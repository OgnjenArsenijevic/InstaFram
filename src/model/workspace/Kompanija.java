package model.workspace;

import javax.swing.SwingUtilities;

import gui.Frame1;

public class Kompanija extends Node1
{
	public Kompanija(String name)
	{
		super(name);
	}
	
	public Kompanija(Kompanija kompanija)
	{
		for(int i=0;i<kompanija.getChildren().size();i++)
		{
			Proizvod p=new Proizvod((Proizvod) kompanija.getChildAt(i));
			p.setParent(this);
			this.addChild(p);
		}
		this.setName(kompanija.getName());
		this.setParent(null);
	}
	
	public int getProizvodCount()
	{
		return this.getChildCount();
	}
	
	public int getModulCount()
	{
		int cnt=0;
		for(Node1 n : this.getChildren())
			cnt+=((Proizvod)n).getModulCount();
		return cnt;
	}
	
	public int getParametarCount()
	{
		int cnt=0;
		for(Node1 n : this.getChildren())
			cnt+=((Proizvod)n).getParametarCount();
		return cnt;
	}
	
}
