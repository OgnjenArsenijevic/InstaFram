package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import app.Main;
import commands.DeleteCommand;
import controller.MessageController;
import gui.Frame1;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.ParametarElementsSelection;

public class CutProjectAction extends AbstractActionI
{
	public CutProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/cut.png"));
		putValue(NAME, "mCut");
		putValue(SHORT_DESCRIPTION, "mCut");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		TreePath[] nodeArray = Frame1.getInstance().getNodeTree().getSelectionPaths();
		if (nodeArray == null)
		{
			MessageController.errorMessage("You have to select some parameters");
			return;
		}
		for (int i = 0; i < nodeArray.length; i++)
		{
			if (nodeArray[i].getLastPathComponent() instanceof Parametar == false)
			{
				MessageController.errorMessage("Only parameters can be cut");
				return;
			}
		}
		ArrayList<Node1> list = new ArrayList<>();
		ArrayList<Parametar> elements = new ArrayList<>();
		for (int i = 0; i < nodeArray.length; i++)
			elements.add(Parametar.makeConstructors(nodeArray[i].getLastPathComponent()));
		ParametarElementsSelection contents = new ParametarElementsSelection(elements);
		Frame1.getInstance().getClipboard().setContents(contents, Frame1.getInstance());
		for (int i = 0; i < nodeArray.length; i++)
		{
			Parametar par = (Parametar) nodeArray[i].getLastPathComponent();
			Node1 parent = (Node1) par.getParent();
			parent.delete(par);
			list.add(par);
			Frame1.getInstance().getPanelRT().removeTabs(par);
		}
		Frame1.getInstance().getCommandManager().addCommand(new DeleteCommand(list));
		Main.saved = false;
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
	}
}
