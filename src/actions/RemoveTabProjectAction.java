package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.Node1;

//ova klasa sluzi za brisanje selektovanog taba iz tabbedPane-a
public class RemoveTabProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public RemoveTabProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/close.png"));
		putValue(NAME, "mRemoveTab");
		putValue(SHORT_DESCRIPTION, "mRemoveTab");
	}

	// ova funkcija brise selektovani tab iz tabbedPane-a
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// uzimamo indeks selektovanog taba
		int idx = Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedIndex();
		if (idx != -1) // ako je neki tab selektovan onda...
		{
			try
			{
				// brisemo taj selektovani tab iz tabbedPane-a
				Frame1.getInstance().getPanelRT().getTabbedPane().remove(idx);
				// postavljamo da nam nulti tab bude selektovan
				Frame1.getInstance().getPanelRT().getTabbedPane().setSelectedIndex(0);
				// uzimamo podatke o cvoru koji odgovara selektovanom tabu
				Node1 node = ((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane()
						.getComponentAt(0)).getNode();
				// update-ujemo donji desni panel(PanelRB) i u njega stavljamo podatke o cvoru
				// iz selektovanog taba
				// to radimo uz pomoc funkcije doWork
				node.notify(node);
			}
			catch (IndexOutOfBoundsException e)
			{
				// ako se desilo da nakon brisanja taba tabbedPane bude prazan desice se ovaj
				// exception i onda treba da obrisemo sav tekst iz donjeg desnog panela(PanelRB)
				Frame1.getInstance().getPanelRB().getTextArea().setText("");
			}
		}
	}

}
