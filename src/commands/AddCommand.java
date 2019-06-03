package commands;

import java.util.ArrayList;
import java.util.Collections;

import gui.Frame1;
import model.workspace.Node1;

public class AddCommand implements AbstractCommand
{

	private ArrayList<Node1> real = new ArrayList<>();

	public AddCommand(ArrayList<Node1> list)
	{
		real = list;
	}

	@Override
	public void doCommand()
	{
		Collections.reverse(real);
		for (int i = 0; i < real.size(); i++)
		{
			String name = real.get(i).getName();
			if ((Node1) real.get(i).getParent() != null)
				((Node1) real.get(i).getParent()).addChild(real.get(i));
			else
				Frame1.getInstance().getNodeModel().setRoot(real.get(i));
			real.get(i).setName(name);
		}
		Collections.reverse(real);
	}

	@Override
	public void undoCommand()
	{
		Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
		for (Node1 n : real)
		{
			if (curr != null && curr instanceof Node1 && curr.equals(n))
				Frame1.getInstance().getNodeTree().clearSelection();
			Frame1.getInstance().getPanelRT().removeTabs(n);
			if (Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount() == 0)
				Frame1.getInstance().getPanelRB().getTextArea().setText("");
			if ((Node1) n.getParent() != null)
				((Node1) n.getParent()).delete(n);
			else
				Frame1.getInstance().getNodeModel().setRoot(null);
		}
	}

}
