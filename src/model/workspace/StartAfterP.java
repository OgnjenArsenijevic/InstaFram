package model.workspace;

public class StartAfterP extends Parametar
{
	
	boolean allowStartAfter;
	
	public StartAfterP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public StartAfterP(StartAfterP startAfrer)
	{
		this.setName(startAfrer.getName());
		this.setText(startAfrer.getText());
		this.setListeners(startAfrer.getListeners());
		this.allowStartAfter=startAfrer.allowStartAfter;
	}
	
	public boolean isAllowStartAfter()
	{
		return allowStartAfter;
	}
	
	public void setAllowStartAfter(boolean allowStartAfter)
	{
		this.allowStartAfter = allowStartAfter;
	}
}
