package commands;

import java.util.ArrayList;

import gui.Frame1;

public class CommandManager
{
	private ArrayList<AbstractCommand> commands = new ArrayList<>();
	private int currCommand = 0;

	public void addCommand(AbstractCommand command)
	{
		while (currCommand < commands.size())
			commands.remove(currCommand);
		commands.add(command);
		currCommand++;
		if (currCommand >= commands.size())
			Frame1.getInstance().getActionManager().getRedoProjectAction().setEnabled(false);
		if (currCommand > 0)
			Frame1.getInstance().getActionManager().getUndoProjectAction().setEnabled(true);

	}

	public void doCommand()
	{
		if (currCommand < commands.size())
		{
			commands.get(currCommand++).doCommand();
			Frame1.getInstance().getActionManager().getUndoProjectAction().setEnabled(true);
		}
		if (currCommand == commands.size())
			Frame1.getInstance().getActionManager().getRedoProjectAction().setEnabled(false);
	}

	public void undoCommand()
	{
		if (currCommand > 0)
		{
			Frame1.getInstance().getActionManager().getRedoProjectAction().setEnabled(true);
			commands.get(--currCommand).undoCommand();
		}
		if (currCommand == 0)
			Frame1.getInstance().getActionManager().getUndoProjectAction().setEnabled(false);
		System.out.println(currCommand + " " + commands.size());
	}
}
