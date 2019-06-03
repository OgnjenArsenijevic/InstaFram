package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class ImportPDFProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public ImportPDFProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/pdf.png"));
		putValue(NAME, "mImpToPDF");
		putValue(SHORT_DESCRIPTION, "mImpToPDF");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// ova funkcija ne treba da bude implementirana
	}

}