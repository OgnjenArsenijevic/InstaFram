package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.Node1;

// ova klasa sluzi za switchovanje tabova u tabbedPane-u
public class SwitchProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public SwitchProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/switch.png"));
		putValue(NAME, "mSwitch");
		putValue(SHORT_DESCRIPTION, "mSwitch");
	}

	// ova funkcija swith-uje tabove u tabbedPane-u
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// ako u tabbedPane-u postoji vise od jednog taba onda...
		if (Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount() > 1)
		{
			// uzimamo indeks trenutno selektovanog taba
			int idx = Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedIndex();
			idx++; // povecavamo indeks za 1
			// ako smo se nalazili na poslednjem tabu stavljamo mu da predje na pocetni tab
			if (idx >= Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount())
				idx = 0;
			Frame1.getInstance().getPanelRT().getTabbedPane().setSelectedIndex(idx);
			// update-ujemo donji desni panel(PanleRB) i u njega upisujemo podatke o cvoru
			// koji odgovara trenutno selektovanom tabu
			Node1 curr = ((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane()
					.getSelectedComponent()).getNode();
			curr.notify(curr);

		}
		// ako je tabbedPane prazan ili ima samo 1 tab nema sta da se switchuje

	}

}