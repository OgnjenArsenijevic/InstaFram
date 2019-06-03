package model.workspace;

import java.util.ArrayList;

public class LookAndFeelP extends Parametar
{
	private ArrayList<String> lookAndFeels=new ArrayList<>();
	
	public LookAndFeelP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public LookAndFeelP(LookAndFeelP laf)
	{
		this.setName(laf.getName());
		this.setText(laf.getText());
		this.setListeners(laf.getListeners());
		this.lookAndFeels=laf.lookAndFeels;
	}
	
	public ArrayList<String> getLookAndFeels()
	{
		return lookAndFeels;
	}
	
	public void setLookAndFeels(ArrayList<String> lookAndFeels)
	{
		this.lookAndFeels = lookAndFeels;
	}
}
