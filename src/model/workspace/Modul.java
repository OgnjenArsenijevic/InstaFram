package model.workspace;

public class Modul extends Node1
{
	
	private boolean selected;
	
	public Modul(String name, Node1 parent)
	{
		super(name, parent);
	}

	public Modul(Modul modul)
	{
		this.setName(modul.getName());
		for (int i = 0; i < modul.getChildren().size(); i++)
		{
			Parametar p = Parametar.makeConstructors(modul.getChildAt(i));
			p.setParent(this);
			this.addChild(p);
		}
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
}
