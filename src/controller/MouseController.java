package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import gui.Frame1;
import gui.JScrollPane1;
import gui.NodeTree;
import javafx.scene.control.ComboBox;
import model.workspace.*;

public class MouseController implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getClickCount() == 1) // ako je mis kliknut tacno jednom onda...
		{
			// ako je neki cvor selektovan dodajemo ga na tabbedPane i u update-ujemo donji
			// desni panel da prikazuje sadrzaj tog selektovanog cvora pomocu f-je doWork
			Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
			if (curr != null && ((Node1) curr).getParent() != null && (e.getSource() instanceof NodeTree))
			{
				Frame1.getInstance().getPanelRT().newTab((Node1) curr);
				((Node1) curr).notify(curr);
			}
			// ako je pritistnut neki drugi tab u tabbedPanu onda...
			if (e.getSource() instanceof JTabbedPane)
			{
				try
				{
					// uzimamo cvor koji odgovara tom pritisnutnom tabu i update-ujemo donji desni
					// panel da prikazuje sadrzaj tog cvora, pomocu funkcije doWork
					curr = ((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane()
							.getSelectedComponent()).getNode();
					((Node1) curr).notify(curr);
				}
				catch (Exception e2)
				{
					// samo je bilo bitno da uhvatimo exception
					// ne treba nista da se radi ovde
				}
			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// ne implementiramo ovu metodu
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// ne implementiramo ovu metodu
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getSource() instanceof NodeTree)
		{
			if (Frame1.getInstance().getNodeTree().getPathForLocation(e.getX(), e.getY()) != null)
			{
				if (Frame1.getInstance().getNodeTree().getPathForLocation(e.getX(), e.getY())
						.getLastPathComponent() instanceof Parametar)
				{
					Frame1.getInstance().getActionManager().getNewProjectAction().setEnabled(false);
				}
				else
				{
					Frame1.getInstance().getActionManager().getNewProjectAction().setEnabled(true);
				}
			}
			else if (Frame1.getInstance().getNodeModel().getRoot() != null)
			{
				Frame1.getInstance().getActionManager().getNewProjectAction().setEnabled(false);
				Frame1.getInstance().getNodeTree().clearSelection();
			}
		}
		else
		{
			Frame1.getInstance().getActionManager().getNewProjectAction().setEnabled(false);
			Frame1.getInstance().getNodeTree().clearSelection();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// ne implementiramo ovu metodu
	}

}
