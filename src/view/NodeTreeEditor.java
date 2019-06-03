package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

import app.Main;
import commands.AddCommand;
import commands.RenameCommand;
import controller.MessageController;
import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.Kompanija;
import model.workspace.Node1;
import model.workspace.Proizvod;

public class NodeTreeEditor extends DefaultTreeCellEditor implements ActionListener
{

	private Object stavka = null;
	private JTextField edit = null;

	public NodeTreeEditor(JTree arg0, DefaultTreeCellRenderer arg1)
	{
		super(arg0, arg1);
	}

	public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4,
			int arg5)
	{

		stavka = arg1;

		edit = new JTextField(arg1.toString());
		edit.addActionListener(this);
		return edit;
	}

	// ako je tri puta kliknuto na cvor ova funkcija vraca true
	public boolean isCellEditable(EventObject arg0)
	{
		if (arg0 instanceof MouseEvent)
		{
			if (((MouseEvent) arg0).getClickCount() == 3)
			{
				return true;
			}
		}
		return false;
	}

	// ova funkcija menja ime selektovanog cvora
	public void actionPerformed(ActionEvent e)
	{
		Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
		String ss = e.getActionCommand();
		if (ss.equals(((Node1) curr).getName())) // ako nista nije promenjeno brejkujemo
			return;
		boolean check = true;
		// proveravamo da li vec postoji cvor sa istim imenom na tom nivou stabla
		if (((Node1) curr).getParent() != null)
		{
			Node1 parent = (Node1) ((Node1) curr).getParent();
			for (int i = 0; i < parent.getChildCount(); i++)
			{
				if (((Node1) parent.getChildAt(i)).getName().equals(ss))
				{
					check = false;
					break;
				}
			}
		}
		if (check == false) // ako postoji obavestavamo korisnika da ne moze da promeni ime
		{
			MessageController.errorMessage("This name already exists, please change it");
		}
		else // ako ne postoji
		{
			String forbidden1 = "Kompanija";
			String forbidden2 = "Proizvod";
			String forbidden3 = "Modul";
			String forbidden4 = "Parametar";
			boolean change = true;
			if (ss.contains(forbidden1)) // ne dozvoljavamo da ime sadrzi rezervisanu rec
			{
				MessageController.errorMessage("Name cannot contain reserved word " + forbidden1);
				change = false;
			}
			if (ss.contains(forbidden2)) // ne dozvoljavamo da ime sadrzi rezervisanu rec
			{
				MessageController.errorMessage("Name cannot contain reserved word " + forbidden2);
				change = false;
			}
			if (ss.contains(forbidden3)) // ne dozvoljavamo da ime sadrzi rezervisanu rec
			{
				MessageController.errorMessage("Name cannot contain reserved word " + forbidden3);
				change = false;
			}
			if (ss.contains(forbidden4)) // ne dozvoljavamo da ime sadrzi rezervisanu rec
			{
				MessageController.errorMessage("Name cannot contain reserved word " + forbidden4);
				change = false;
			}
			if (change)
			{
				Frame1.getInstance().getCommandManager().addCommand(new RenameCommand((Node1) stavka,
						ss, ((Node1) stavka).getName()));

				//((Node1) stavka).setPreviousName(((Node1) stavka).getName());
				((Node1) stavka).setName(ss); // menjamo ime cvora

				// update-ujemo njegovo ime u tabbedPane-u i u podacima koji se nalaze u donjem
				// desnom panelu
				for (int i = 0; i < Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount(); i++)
				{
					if (((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getComponentAt(i))
							.getNode() == ((Node1) stavka))
					{
						Frame1.getInstance().getPanelRT().getTabbedPane().setTitleAt(i, ss);
						//((Node1) stavka).notify(stavka);
						Main.saved = false;
						break;
					}
				}
			}
		}

	}

}