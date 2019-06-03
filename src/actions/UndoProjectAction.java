package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gui.Frame1;

public class UndoProjectAction extends AbstractActionI
{
	public UndoProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/undo.png"));
		putValue(NAME, "mUndo");
		putValue(SHORT_DESCRIPTION, "mUndo");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Frame1.getInstance().getCommandManager().undoCommand();
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
	}
}
