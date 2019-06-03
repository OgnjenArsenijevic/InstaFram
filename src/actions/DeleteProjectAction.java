package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import app.Main;
import commands.AddCommand;
import commands.DeleteCommand;
import controller.MessageController;
import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.Kompanija;
import model.workspace.Modul;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;

//ova klasa sluzi za brisanje selektovanog cvora i svih njegovih podcvorova iz stabla
public class DeleteProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public DeleteProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/close.png"));
		putValue(NAME, "mDelete");
		putValue(SHORT_DESCRIPTION, "mDelete");
	}

	// ova funkcija brise selektovani cvor i sve njegove podcvorove iz stabla
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// uzimamo cvor koji je trenutno selektovan
		Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
		ArrayList<Node1> list = new ArrayList<>();
		try
		{
			ArrayDeque<Node1> deque = new ArrayDeque<>(); // ovaj deque koristimo vise puta u nastavku programa

			// ubacujemo selektovani node u deque i radimo BFS na svim njegovim podcvorovima
			// da bi smo prebrojali koliko ima listova medju njima, krajnji rezultat ce se
			// nalaziti u promenljivoj leaves
			// ukoliko nijedan cvor nije bio selektovan desice se NullPointerException koji
			// cemo catchovati a ostatak try-a se nece izvrsiti
			int leaves = 0;
			deque.add((Node1) curr);
			while (deque.isEmpty() == false)
			{
				Node1 curr1 = deque.poll();
				if (curr1.isLeaf())
					leaves++;
				for (int i = 0; i < curr1.getChildCount(); i++)
					deque.add((Node1) curr1.getChildAt(i));
			}
			// ako je i selektovani cvor bio list treba da oduzmemo 1 od krajnjeg
			// rezultata(jer ne treba da racunamo selektovani cvor)
			if (((Node1) curr).isLeaf())
				leaves--;

			String message = "";
			if (curr instanceof Parametar)
				message = "Are you sure that you want to delete " + ((Parametar) curr).getName() + " ?";
			if (curr instanceof Modul)
				message = "Are you sure? " + ((Modul) curr).getName() + " has " + ((Modul) curr).getChildCount()
						+ " paramreters which will also be deleted";
			if (curr instanceof Proizvod)
				message = "Are you sure? " + ((Proizvod) curr).getName() + " has " + ((Proizvod) curr).getModulCount()
						+ " moduls and " + ((Proizvod) curr).getParametarCount()
						+ " parametars which will also be deleted";
			if (curr instanceof Kompanija)
				message = "Are you sure? " + ((Kompanija) curr).getName() + " has "
						+ ((Kompanija) curr).getProizvodCount() + " products, " + ((Kompanija) curr).getModulCount()
						+ " moduls and " + ((Kompanija) curr).getParametarCount()
						+ " parameters which will also be deleted";

			int chosen = MessageController.confirmMessage(message, "Delete selected node?");

			// ako korisnik ne zeli da obrise cvor namerno bacamo NullPointerException da bi
			// prekinuli try
			if (chosen != 0)
				throw new NullPointerException();

			// ako selektovani cvor nema roditelja znaci da je root i da treba da obrisemo
			// sve cvorove
			if (((Node1) curr).getParent() == null)
			{
				Frame1.getInstance().getNodeModel().setRoot(null); // brisemo root
				// brisemo sve cvorove iz tabbedPane koji se nalazi u gornjem desnom panelu
				// (PanelRT)
				Frame1.getInstance().getPanelRT().getTabbedPane().removeAll();
				// brisemo sav tekst iz donjeg desnog panela (PanelRB), jer nema vise cvorova u
				// tabbedPane-u za koje bi mogao da pokazuje podatke
				Frame1.getInstance().getPanelRB().getTextArea().setText("");
				list.add((Node1) curr);
				Frame1.getInstance().getCommandManager().addCommand(new DeleteCommand(list));
				Main.saved = false; // oznacavamo da korsnik trenutno nije sacuvao napravljene promene
			}
			else // ako selektovani cvor ima roditelja onda...
			{
				// pronalazimo njegovog roditelja
				Node1 parent = ((Node1) ((Node1) curr).getParent());
				// iz njegovog roditelja brisemo selektovani cvor (sada je on automatski obrisan
				// i iz stabla)
				parent.delete((Node1) curr);
				// brisemo tekst iz donjeg desnog panela (PanelRB) jer cvor ciji su podaci bili
				// ispisani u njemu vise ne postoji
				Frame1.getInstance().getPanelRB().getTextArea().setText("");
				// reloadujemo NodeModel jer su brisanjem cvora napravljene izmene u stablu
				// Frame1.getInstanceOfFrame1().getNodeModel().reload();
				Frame1.getInstance().getNodeTree().clearSelection();
				Main.saved = false; // oznacavamo da korsnik trenutno nije sacuvao napravljene promene

				// sklanjamo tabove obrisanih cvorova iz tabbedPane-a
				// pogledati implementaciju funkcije removeTabs(Node1 node) u klasi PanelRT za
				// vise detalja
				Frame1.getInstance().getPanelRT().removeTabs((Node1) curr);
				list.add((Node1) curr);
				Frame1.getInstance().getCommandManager().addCommand(new DeleteCommand(list));

			}
			// radimo update da bi nam se na ekranu prikazale promene koje smo napravili
			SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
		}
		catch (NullPointerException e)
		{
			// ovaj exception se desava ako nijedan cvor nije selektovan a pokusamo da
			// deletujemo ili ako smo ga namerno throw-ovali da izadjemo iz try-a
			// e.printStackTrace();
		}
	}

}
