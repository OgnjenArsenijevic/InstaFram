package controller;

import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import app.Main;
import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.AuthorP;
import model.workspace.CustomP;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.Modul;
import model.workspace.NameP;
import model.workspace.Node1;
import model.workspace.Proizvod;

public class KeyController implements KeyListener
{

	@Override
	public void keyPressed(KeyEvent e)
	{
		// ne implementiramo ovu metodu
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// svaki put kada u gornjem desnom panelu izmenimo sadrzaj textArea-e koji
		// odgovara trenutnom panelu
		// update-ujemo donji desni panel i sadrzaj cvora koji odgovara cvoru(koji
		// odgovara trenutnom panelu)
		Node1 curr = ((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedComponent())
				.getNode();
		JScrollPane1 sp = (JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane()
				.getSelectedComponent();
		String s = "";
		if (e.getSource() instanceof JTextArea)
			s = ((JTextArea) e.getSource()).getText();
		if (e.getSource() instanceof JTextField)
			s = ((JTextField) e.getSource()).getText();
		// curr.setText(((JTextArea) e.getSource()).getText());
		// stavljamo da trenutno nisu sacuvane izmene koje je napravio korisnik

		Main.saved = false;
		if (curr instanceof NameP)
		{
			((NameP) curr).setNameP(s);
			return;
		}
		if (curr instanceof AuthorP)
		{
			((AuthorP) curr).setAuthorName(sp.getView().getTfName().getText());
			((AuthorP) curr).setAuthorSurname(sp.getView().getTfSurname().getText());
			return;
		}
		if (curr instanceof LogoP)
		{
			((LogoP) curr).setImageFile(s);
			return;
		}
		if (curr instanceof LicenceOfAgreementP)
		{
			((LicenceOfAgreementP) curr).setFilePath(s);
			return;
		}
		if (curr instanceof CustomP)
			((CustomP) curr).setTxt(s);
		if (curr instanceof Proizvod)
		{
			((Proizvod) curr).setFilePath(s);
			return;
		}
		else
			curr.setText(((JTextArea) e.getSource()).getText());

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// ne implementiramo ovu metodu
	}

}
