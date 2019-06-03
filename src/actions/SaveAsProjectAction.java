package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import app.Main;
import controller.MessageController;
import gui.Frame1;
import model.workspace.Node1;
import model.workspace.Proizvod;

// ova klasa sluzi za serijalizaciju stabla i cuvanje podataka o stablu u odabrani fajl
public class SaveAsProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public SaveAsProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/save_as.png"));
		putValue(NAME, "mSaveAs");
		putValue(SHORT_DESCRIPTION, "mSaveAs");
	}

	// ova funkcija serijalizuje stablo i cuva podatke o tom stablu u odabrani fajl
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		/*
		 * File chooser
		 * 
		 */
		/*
		 * FileOutputStream fileOutputStream = new FileOutputStream("test.ser");
		 * ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
		 * out.writeObject(root);
		 * 
		 * out.close(); fileOutputStream.close();
		 */

		try
		{
			ImageIcon imageIcon = (ImageIcon) loadIcon("src/images/question.png");
			String[] options = new String[] { "Whole wokspace", "Just selected products" };
			int response = JOptionPane.showOptionDialog(null, "What do you want to save?", "Question",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, imageIcon, options, options[0]);
			if (response == -1)
				return;
			// kreiramo fileChooser
			JFileChooser fileChooser = new JFileChooser();
			boolean changeSaved = true;
			// postavljamo direktorijum fileChooser-a na poslednji direktorijum koji je
			// korisnik koristio ili na home ako nije koristio ni jedan
			// pogledati default vrednost za Main.directory u klasi Main
			fileChooser.setCurrentDirectory(Main.directory);
			// nudimo korisniku da izabere u koji ce fajl sacuvati stablo
			int chosen0 = fileChooser.showSaveDialog(null);
			while (fileChooser.getSelectedFile().exists()) // ako izabrani fajl vec postoji
			{
				if (chosen0 != 0) // ako je korsnik kliknuo na cancel ili x brejkujemo
				{
					fileChooser.setSelectedFile(null);
					changeSaved = false;
					break;
				}
				// pitamo korisnika da li zeli da overriduje taj fajl
				int chosen = MessageController.confirmMessage("This file already exists, do you want to override it?",
						"Override?");
				if (chosen != 0) // ako ne zeli da overriduje onda...
				{
					// postavljamo da nijedan fajl nije selektovan
					fileChooser.setSelectedFile(null);
					// ponovo nudimo korisniku da odabere fajl u koji ce sacuvati stablo
					int chosen1 = fileChooser.showSaveDialog(null);
					if (chosen1 == 1) // ako korisnik klikne na cancel ili x brejkujemo
					{
						fileChooser.setSelectedFile(null);
						changeSaved = false;
						break;
					}
				}
				else // ako korisnik zeli da overriduje fajl brejkujemo
					break;
			}
			if (changeSaved) // ako ce korisnikovo stablo biti savucano u neki fajl onda...
			{
				// update-ujemo podatke o trenutnom direktorijumu, fajlu i postavljamo da je
				// korisnikov rad sacuvan
				Main.directory = fileChooser.getCurrentDirectory();
				Main.currFile = fileChooser.getSelectedFile();
				Main.saved = true;
			}
			int cnt = 0;
			if (response == 1)
			{
				TreePath[] nodeArray = Frame1.getInstance().getNodeTree().getSelectionPaths();
				for (int i = 0; i < nodeArray.length; i++)
				{
					if (nodeArray[i].getLastPathComponent() instanceof Proizvod)
						cnt++;
				}
				if (cnt == 0)
					return;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(fileChooser.getSelectedFile());
			ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
			if (response == 0)
				out.writeObject(Frame1.getInstance().getNodeModel().getRoot());
			if (response == 1)
			{
				TreePath[] nodeArray = Frame1.getInstance().getNodeTree().getSelectionPaths();
				for (int i = 0; i < nodeArray.length; i++)
				{
					if (nodeArray[i].getLastPathComponent() instanceof Proizvod)
						out.writeObject(nodeArray[i].getLastPathComponent());
				}
			}
			out.close();
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		/*
		 * String s = ""; ArrayDeque<Node1> deque = new ArrayDeque<>(); try { // radimo
		 * BFS i redom upisujemo u fajl sve cvorove stabla // na koje nailazimo // za
		 * svaki cvor izmedju njegovog codeName-a ,Name-a i Text-a stavljamo //
		 * separatore prilikom upisa u fajl // sto ce nam kasnije olaksati posao kod
		 * OpenProjectAction // takodje na kraj teksta stavljamo dodatne znakove da bi
		 * posle kad radimo // OpenProjectAction // mogli da proverimo slucaj kad tekst
		 * ima vise linija deque.add((Node1)
		 * Frame1.getInstanceOfFrame1().getNodeModel().getRoot()); while
		 * (deque.isEmpty() == false) { Node1 curr = deque.poll(); s +=
		 * curr.getCodeName() + "__" + curr.getName() + "__" + curr.getText() + "]]]" +
		 * "\n"; for (int i = 0; i < curr.getChildCount(); i++) { deque.add((Node1)
		 * curr.getChildAt(i)); } } // kreiramo fileChooser JFileChooser fileChooser =
		 * new JFileChooser(); boolean changeSaved = true; // postavljamo direktorijum
		 * fileChooser-a na poslednji direktorijum koji je // korisnik koristio ili na
		 * home ako nije koristio ni jedan // pogledati default vrednost za
		 * Main.directory u klasi Main fileChooser.setCurrentDirectory(Main.directory);
		 * // nudimo korisniku da izabere u koji ce fajl sacuvati stablo int chosen0 =
		 * fileChooser.showSaveDialog(null); while
		 * (fileChooser.getSelectedFile().exists()) // ako izabrani fajl vec postoji {
		 * if (chosen0 != 0) // ako je korsnik kliknuo na cancel ili x brejkujemo {
		 * fileChooser.setSelectedFile(null); changeSaved = false; break; } // pitamo
		 * korisnika da li zeli da overriduje taj fajl int chosen =
		 * JOptionPane.showConfirmDialog(null,
		 * "This file already exists, do you want to override it?", "",
		 * JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); if (chosen != 0) //
		 * ako ne zeli da overriduje onda... { // postavljamo da nijedan fajl nije
		 * selektovan fileChooser.setSelectedFile(null); // ponovo nudimo korisniku da
		 * odabere fajl u koji ce sacuvati stablo int chosen1 =
		 * fileChooser.showSaveDialog(null); if (chosen1 == 1) // ako korisnik klikne na
		 * cancel ili x brejkujemo { fileChooser.setSelectedFile(null); changeSaved =
		 * false; break; } } else // ako korisnik zeli da overriduje fajl brejkujemo
		 * break; } if (changeSaved) // ako ce korisnikovo stablo biti savucano u neki
		 * fajl onda... { // update-ujemo podatke o trenutnom direktorijumu, fajlu i
		 * postavljamo da je // korisnikov rad sacuvan Main.directory =
		 * fileChooser.getCurrentDirectory(); Main.currFile =
		 * fileChooser.getSelectedFile(); Main.saved = true; } try (FileWriter fw = new
		 * FileWriter(fileChooser.getSelectedFile(), false); BufferedWriter bw = new
		 * BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) { // ispisujemo
		 * podatke u stablu u fajl out.print(s); } catch (IOException e) {
		 * System.out.println("Greska pri radu sa fajlom"); } } catch (Exception e) { //
		 * samo je bilo bitno da uhvatimo exception // ne treba nista da se radi ovde }
		 */
	}

}