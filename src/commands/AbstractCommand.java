package commands;

public interface AbstractCommand
{
	public void doCommand();

	public void undoCommand();
}
