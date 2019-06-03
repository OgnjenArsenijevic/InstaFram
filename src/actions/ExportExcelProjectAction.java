package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class ExportExcelProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public ExportExcelProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/excel.png"));
		putValue(NAME, "mExpToMsExcel");
		putValue(SHORT_DESCRIPTION, "mExpToMsExcel");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// ova funkcija ne treba da bude implementirana
	}

}