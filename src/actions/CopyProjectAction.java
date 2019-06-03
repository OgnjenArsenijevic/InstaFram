package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import controller.MessageController;
import gui.Frame1;
import model.workspace.Parametar;
import model.workspace.ParametarElementsSelection;
import model.workspace.Proizvod;

public class CopyProjectAction extends AbstractActionI
{

	public CopyProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/copy.png"));
		putValue(NAME, "mCopy");
		putValue(SHORT_DESCRIPTION, "mCopy");
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
				MessageController.errorMessage("Only parameters can be copied");
				return;
			}
		}
		ArrayList<Parametar> elements = new ArrayList<>();
		for (int i = 0; i < nodeArray.length; i++)
			elements.add(Parametar.makeConstructors(nodeArray[i].getLastPathComponent()));
		ParametarElementsSelection contents = new ParametarElementsSelection(elements);
		Frame1.getInstance().getClipboard().setContents(contents, Frame1.getInstance());
	}

}
