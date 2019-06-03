package model.workspace;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import gui.Frame1;

public class Proizvod extends Node1
{
	private String filePath;
	private ArrayList<Parametar> installationParameters;
	private int currParameterIndex;
	private boolean desktopShortcut;
	private boolean startAfter;
	private String destination;
	
	public Proizvod(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public Proizvod(Proizvod proizvod)
	{
		this.setName(proizvod.getName());
		for(int i=0;i<proizvod.getChildren().size();i++)
		{
			if(proizvod.getChildAt(i) instanceof Modul)
			{
				Modul m=new Modul((Modul)proizvod.getChildAt(i));
				m.setParent(this);
				this.addChild(m);
			}
			else
			{
				Parametar p = Parametar.makeConstructors(proizvod.getChildAt(i));
				p.setParent(this);
				this.addChild(p);
			}
		}
		this.setParent((Node1) Frame1.getInstance().getNodeModel().getRoot());
		this.filePath=proizvod.filePath;
	}
	
	@Override
	public void addChild(Node1 node) // funkcija za dodavanje dece cvoru
	{
		ArrayList<Node1> moduli=new ArrayList<>();
		ArrayList<Node1> parametri=new ArrayList<>();
		
		super.addChild(node);
		
		for(Node1 n:this.getChildren())
		{
			if(n instanceof Modul)
				moduli.add(n);
			if(n instanceof Parametar)
				parametri.add(n);
		}
		this.getChildren().clear();
		for(Node1 n : moduli)
			this.getChildren().add(n);
		for(Node1 n : parametri)
			this.getChildren().add(n);
		// radimo update da bi nam se na ekranu prikazale promene koje smo napravili
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
		notify(this);
	}
	
	public int getModulCount()
	{
		int cnt=0;
		for(Node1 n : this.getChildren())
		{
			if(n instanceof Modul)
				cnt++;
		}
		return cnt;
	}
	
	public int getParametarCount()
	{
		int cnt=this.getChildCount()-this.getModulCount();
		for(Node1 n : this.getChildren())
		{
			if(n instanceof Modul)
				cnt+=((Modul)n).getChildCount();
		}
		return cnt;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public ArrayList<Parametar> getInstallationParameters()
	{
		return installationParameters;
	}

	public void setInstallationParameters(ArrayList<Parametar> installationParameters)
	{
		this.installationParameters = installationParameters;
	}

	public int getCurrParameterIndex()
	{
		return currParameterIndex;
	}

	public void setCurrParameterIndex(int currParameterIndex)
	{
		this.currParameterIndex = currParameterIndex;
	}

	public boolean isDesktopShortcut()
	{
		return desktopShortcut;
	}

	public void setDesktopShortcut(boolean desktopShortcut)
	{
		this.desktopShortcut = desktopShortcut;
	}

	public boolean isStartAfter()
	{
		return startAfter;
	}

	public void setStartAfter(boolean startAfter)
	{
		this.startAfter = startAfter;
	}

	public String getDestination()
	{
		return destination;
	}
	
	public void setDestination(String destination)
	{
		this.destination = destination;
	}
	
}
