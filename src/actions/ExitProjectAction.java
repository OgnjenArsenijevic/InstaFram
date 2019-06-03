package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import app.Main;
import controller.MessageController;
import gui.Frame1;

// ova klasa sluzi za zatvaranje programa
public class ExitProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public ExitProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/exit.png"));
		putValue(NAME, "mExit");
		putValue(SHORT_DESCRIPTION, "mExit");
	}

	// ova funkcija zatvara program
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (Main.saved == false) // proveravamo da li su promene koje je korisnik napravio sacuvane
		{
			// ako nisu sacuvane onda...
			// pitamo korisnika da li zeli da sacuva promene koje je napravio
			int chosen = MessageController.confrimMessageWithCancel(
					"You have unsaved changes, do you want to save your current work?", "Save?");
			// ako kaze da zeli onda sacuvamo promene pozivajuci f-ju
			// actionPredormed(ActionEvent e) iz klase SaveProjectAction
			// ta funkcija cuva promene (pogledati njenu implementaciju za vise detalja)
			if (chosen == 0)
				Frame1.getInstance().getActionManager().getSaveProjectAction().actionPerformed(null);
			// ako je korisnik kliknuo na yes ili no kada smo ga pitali da li zeli da sacuva
			// izmene onda treba zatvoriti program, a ako je kliknuo na cancel ili na x u
			// gornjem desnom uglu iskacuceg prozorcica onda vracamo korisnika na radnu
			// povrsinu i ne zatvaramo program
			if (chosen == 0 || chosen == 1)
				Frame1.getInstance().dispose();
		}
		else // ako su sve promene koje je korisnik napravio sacuvane onda...
			Frame1.getInstance().dispose(); // zatvaramo program
	}

}