package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

// setujemo izgled panela u dnu frame-a
public class PanelS extends JPanel
{
	private JTextField tfKorisnik;
	private JTextField tfStatus;
	
	public PanelS()
	{
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		JTextField tfDatumIVreme = new JTextField();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String formatDateTime = now.format(formatter);
		tfDatumIVreme.setText("Datum i vreme: ( " + formatDateTime + " )");
		tfDatumIVreme.setEditable(false);
		tfDatumIVreme.setHorizontalAlignment(JTextField.CENTER);

		new Timer(1000, new ActionListener() // setujemo menjanje vremena u donjem levom uglu prozora
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String formatDateTime = now.format(formatter);
				tfDatumIVreme.setText("Datum i vreme: ( " + formatDateTime + " )");
			}
		}).start();

		try
		{
			tfKorisnik = new JTextField("Korisnik: < " + System.getProperty("user.name") + " >");
		}
		catch (SecurityException e)
		{
			tfKorisnik = new JTextField("Korisnik: < Ko je prijavljen za rad >");
		}
		tfKorisnik.setEditable(false);
		tfKorisnik.setHorizontalAlignment(JTextField.CENTER);
		JTextField tfAkcija = new JTextField("Akcija: < Naziv komandne linije >");
		tfAkcija.setEditable(false);
		tfAkcija.setBackground(Color.ORANGE);
		tfAkcija.setHorizontalAlignment(JTextField.CENTER);
		tfStatus = new JTextField("Status: < Ready >");
		tfStatus.setEditable(false);
		tfStatus.setBackground(Color.YELLOW);
		tfStatus.setHorizontalAlignment(JTextField.CENTER);
		// JTextField tfEmpty=new JTextField(" ");
		tfDatumIVreme.setFont(new Font("", 3, 12));
		tfKorisnik.setFont(new Font("", 1, 12));
		tfAkcija.setFont(new Font("", 1, 12));
		tfStatus.setFont(new Font("", 1, 12));

		JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tfDatumIVreme, tfKorisnik);
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tfAkcija, tfStatus);
		JSplitPane sp3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sp1, sp2);

		sp1.setDividerSize(3);
		sp2.setDividerSize(3);
		sp3.setDividerSize(3);

		this.add(sp3, BorderLayout.CENTER);
	}
	
	public JTextField getTfKorisnik()
	{
		return tfKorisnik;
	}
	
	public JTextField getTfStatus()
	{
		return tfStatus;
	}
}
