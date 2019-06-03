package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import gui.Frame1;

public class RedoProjectAction extends AbstractActionI
{
	public RedoProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/redo.png"));
		putValue(NAME, "mRedo");
		putValue(SHORT_DESCRIPTION, "mRedo");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Frame1.getInstance().getCommandManager().doCommand();
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());

	}
}
