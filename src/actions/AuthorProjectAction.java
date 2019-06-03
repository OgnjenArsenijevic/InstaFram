package actions;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

//ova klasa nam sluzi za otvaranje prozora koji sadrzi podatke o autoru InstaFram-a
public class AuthorProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public AuthorProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/info.png"));
		putValue(NAME, "mOpen");
		putValue(SHORT_DESCRIPTION, "mOpen");
	}

	// ova funkcija otvara prozor u kome se nalaze podaci o autoru InstaFram-a
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JFrame frame = new JFrame("Author info"); // postavljamo naslov prozora
		frame.setSize(400, 340); // postavljamo velicinu prozora
		frame.setLocationRelativeTo(null); // stavljamo da se prozor pojavi na centru ekrana
		frame.setLayout(new BorderLayout()); // postavljamo Layout
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // podesavanje za zatvaranje prozora

		ImageIcon imgIcon = new ImageIcon("src/images/ognjen.png"); // ucitavamo slicicu
		frame.add(new JLabel(imgIcon), BorderLayout.CENTER); // postavljamo slicicu na centar

		// pravimo labelu sa imenom autora, centriramo je, boldiramo tekst u njoj,
		// podesavamo velicinu fonta i postavljamo je na vrh prozora
		JLabel lblIme = new JLabel("Ognjen Arsenijević");
		lblIme.setHorizontalAlignment(SwingConstants.CENTER);
		lblIme.setFont(new Font("", 1, 35));
		frame.add(lblIme, BorderLayout.NORTH);

		// pravimo labelu sa indeksom autora, centriramo je, boldiramo tekst u njoj,
		// podesavamo velicinu fonta i postavljamo je na vrh prozora
		JLabel lblIndex = new JLabel("RN10/17");
		lblIndex.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndex.setFont(new Font("", 1, 35));
		frame.add(lblIndex, BorderLayout.SOUTH);

		frame.setVisible(true);// prikazujemo prozor
	}

}
