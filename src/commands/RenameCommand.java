package commands;

import gui.Frame1;
import gui.JScrollPane1;
import model.workspace.Node1;

public class RenameCommand implements AbstractCommand
{

	private Node1 real;
	private String use;
	private String swap;

	public RenameCommand(Node1 node, String u, String s)
	{
		real = node;
		use = u;
		swap = s;
	}

	@Override
	public void doCommand()
	{
		real.setName(swap);
		String tmp = use;
		use = swap;
		swap = tmp;
		for (int i = 0; i < Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount(); i++)
			Frame1.getInstance().getPanelRT().getTabbedPane().setTitleAt(i,
					((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getComponentAt(i))
							.getNode().getName());
		if (((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedComponent())
				.getNode() != null)
		{
			((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedComponent()).getNode()
					.notify(((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane()
							.getSelectedComponent()).getNode());
		}
		else // pitanje dal ovo uopste moze da se desi
			Frame1.getInstance().getPanelRB().getTextArea().setText("");
	}

	@Override
	public void undoCommand()
	{
		this.doCommand();
	}

}
