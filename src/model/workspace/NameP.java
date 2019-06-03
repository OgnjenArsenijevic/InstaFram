package model.workspace;

public class NameP extends Parametar
{
	private String nameP;

	public NameP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public NameP(String nameP)
	{
		super();
		this.nameP = nameP;
	}

	public NameP(NameP n)
	{
		super();
		this.setName(n.getName());
		this.setText(n.getText());
		this.setListeners(n.getListeners());
		this.nameP = n.nameP;
	}
	
	public String getNameP()
	{
		return nameP;
	}
	
	public void setNameP(String nameP)
	{
		this.nameP = nameP;
	}
}
