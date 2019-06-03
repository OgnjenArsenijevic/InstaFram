package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import app.Main;
import controller.MessageController;
import gui.Frame1;
import model.workspace.Kompanija;
import model.workspace.Node1;

//ova klasa sluzi za ucitavanje stabla iz izabranog fajla

public class OpenProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public OpenProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/open1.jpg"));
		putValue(NAME, "mOpen");
		putValue(SHORT_DESCRIPTION, "mOpen");
	}

	// ova funkcija ucitava stablo iz izabranog fajla
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// kreiramo fileChooser
		JFileChooser fileChooser = new JFileChooser();
		// postavljamo direktorijum fileChooser-a na poslednji direktorijum koji je
		// korisnik koristio ili na home ako nije koristio ni jedan
		// pogledati default vrednost za Main.directory u klasi Main
		fileChooser.setCurrentDirectory(Main.directory);
		// nudimo korisniku da izabere fajl iz koga zeli da ucita stablo
		int chosen = fileChooser.showOpenDialog(null);
		// ako je korisnik kliknuo da ucita neki fajl onda...
		if (chosen != 1)
		{
			try
			{
				// proveravamo da li fajl koji je korsnik izabrao postoji u tom direktorijumu
				while (!fileChooser.getSelectedFile().exists()) // ako ne postoji onda...
				{
					// ispisujemo korisniku poruku o gresci i ponovo otvaramo prozor za biranje
					// fajlova
					MessageController.errorMessage("File not found");
					fileChooser.setSelectedFile(null);
					chosen = fileChooser.showOpenDialog(null);
				}
				// ako je korsnik kliknuo na cancel ili x u fileChooseru, onda stavljamo da ni
				// jedan fajl nije selektovan
				if (chosen == 1)
					fileChooser.setSelectedFile(null);
				// prosledjujemo selektovani fajl scanneru
				// ovde moze da se desi NullPointerException ako ni jedan fajl nije selektovan i
				// onda se izlazi iz try-a
				Scanner sc = new Scanner(fileChooser.getSelectedFile());
				if (Main.saved == false) // proveravamo da li su promene koje je korisnik napravio sacuvane
				{
					// ako nisu sacuvane onda...
					// pitamo korisnika da li zeli da sacuva promene koje je napravio
					chosen = MessageController.confrimMessageWithCancel(
							"You have unsaved changes, do you want to save your current work?", "Save?");
					// ako kaze da zeli onda sacuvamo promene pozivajuci f-ju
					// actionPredormed(ActionEvent e) iz klase SaveProjectAction
					// ta funkcija cuva promene (pogledati njenu implementaciju za vise detalja)
					if (chosen == 0)
						Frame1.getInstance().getActionManager().getSaveProjectAction().actionPerformed(null);
					if (chosen == -1 || chosen == 2)
					{
						// ako je korisnik kliknui na cancel ili x u iskacucem prozorcicu onda bacamo
						// Exception da bi smo prekinuli try
						throw new NullPointerException();
					}
				}
				// setujemo directory, currFile i stavljamo da su sve promene sacuvane
				Main.directory = fileChooser.getCurrentDirectory();
				Main.currFile = fileChooser.getSelectedFile();
				FileInputStream fileInputStream = null;
				ObjectInputStream in = null;
				Main.saved = true;
				try
				{
					fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
					in = new ObjectInputStream(fileInputStream);

					Object curr = in.readObject();
					if (curr instanceof Kompanija == false)
					{
						Frame1.getInstance().getNodeModel().setRoot(new Kompanija("Kompanija"));
						((Kompanija) Frame1.getInstance().getNodeModel().getRoot()).addChild((Node1) curr);
						((Node1) curr).setParent((Kompanija) Frame1.getInstance().getNodeModel().getRoot());
					}
					else
						Frame1.getInstance().getNodeModel().setRoot((Kompanija) curr);
					while (true)
					{
						curr = in.readObject();
						((Kompanija) Frame1.getInstance().getNodeModel().getRoot()).addChild((Node1) curr);
						((Node1) curr).setParent((Kompanija) Frame1.getInstance().getNodeModel().getRoot());
					}
				}
				catch (EOFException e)
				{
					try
					{
						in.close();
						fileInputStream.close();
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayDeque<Node1> deque = new ArrayDeque<>();
					deque.push((Node1) Frame1.getInstance().getNodeModel().getRoot());
					while (deque.isEmpty() == false)
					{
						Node1 curr = deque.pop();
						if (curr.getListenersCount() == 0)
							curr.addListener(Frame1.getInstance().getPanelRB());
						for (int i = 0; i < curr.getChildCount(); i++)
							deque.push(curr.getChildren().get(i));
					}
					// brisemo sve otvorene tabove iz tabbedPane-a
					Frame1.getInstance().getPanelRT().getTabbedPane().removeAll();
					// brisemo sav tekst iz donjeg desnog panela(PanelRB)
					Frame1.getInstance().getPanelRB().getTextArea().setText("");
				}
				catch (ClassNotFoundException e)
				{
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				catch (ClassCastException e)
				{

				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}

				// pravimo hashMapu u kojoj cemo cuvati podatke o svim cvorovima koje kod cvora
				// i sve cvorove koje uzmemo iz fajla + selektovani cvor u koji ucitavamo stablo
				/*
				 * HashMap<String, Node1> hashMap = new HashMap<>(); // ako fajl nije prazan...
				 * if (sc.hasNextLine()) { // ubacujemo podatke o rootu iz fajla u hashMapu
				 * String ss = ""; do { ss += sc.nextLine(); ss += "\n"; } while
				 * (!ss.contains("]]]")); ss = ss.substring(0, ss.length() - 1); String[] split
				 * = ss.split("__"); Node1 root = new Node1("");
				 * Frame1.getInstanceOfFrame1().getNodeModel().setRoot(root);
				 * root.setCodeName(split[0]); root.setName(split[1]);
				 * root.setText(split[2].substring(0, split[2].length() - 3));
				 * hashMap.put(root.getCodeName(), root); } while (sc.hasNextLine()) // sve dok
				 * ima jos neucitanih stvari iz fajla... { String ss = ""; // u string ss
				 * ucitavamo podatke o trenutnom cvoru iz fajla do { ss += sc.nextLine(); ss +=
				 * "\n"; } while (!ss.contains("]]]")); // sklanjamo karakter za novi red ss =
				 * ss.substring(0, ss.length() - 1); String[] split = ss.split("__"); // u
				 * split[0] se nalazi kod cvora // u split[1] se nalazi ime cvora // u split[2]
				 * se nalazi tekst cvora // za taj cvor koji treba da dodamo // parentCode
				 * dobijamo kada sklonimo 0 sa pocetka koda koji se nalazi u split[0] // i
				 * poslednju . i sve sto se nalzi posle nje isto iz split[0] // i onda kad mu na
				 * pocetak dodamo kod selektovanog cvora // to realizujemo preko substring
				 * metode i String-a tmp malo nize u kodu int cutEnd = 0; for (int i =
				 * split[0].length() - 1; i >= 0; i--) { cutEnd++; if (split[0].charAt(i) ==
				 * '.') break; } String parentCode = split[0].substring(0, split[0].length() -
				 * cutEnd); // kreiramo novi cvor i u konstuktoru mu kao roditelja prosledjujemo
				 * njegovog // roditelja kojeg smo // pronasli u hash mapi uz pomoc njegovog
				 * kodnog imena Node1 node = new Node1("", hashMap.get(parentCode));
				 * node.addListener(Frame1.getInstanceOfFrame1().getPanelRB()); // u hash mapi
				 * pronalazimo roditelja kreiranog cvora i dodajemo taj cvor na tog // roditelja
				 * uz pomoc funkcije addChild cija se implementacija nalazi u klasi // Node1
				 * hashMap.get(parentCode).addChild(node); // setujemo podatke za taj kreirani
				 * cvor node.setCodeName(split[0]); node.setName(split[1]);
				 * node.setText(split[2].substring(0, split[2].length() - 3)); // ubacujemo
				 * kreirani cvor u hashMapu da bi i na njega kasnije mogla da se // dodaju //
				 * nejgova deca ako postoje hashMap.put(node.getCodeName(), node); } sc.close();
				 * // brisemo sve otvorene tabove iz tabbedPane-a
				 * Frame1.getInstanceOfFrame1().getPanelRT().getTabbedPane().removeAll(); //
				 * brisemo sav tekst iz donjeg desnog panela(PanelRB)
				 * Frame1.getInstanceOfFrame1().getPanelRB().getTextArea().setText("");
				 */
			}
			catch (FileNotFoundException e)
			{
				// samo je bilo bitno da uhvatimo ovaj exception
				// ne treba nista da se radi ovde
			}
			catch (NullPointerException e)
			{
				// samo je bilo bitno da uhvatimo ovaj exception
				// ne treba nista da se radi ovde
			}
		}
		// radimo update da bi nam se na ekranu prikazale promene koje smo napravili
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
	}
}