package model.workspace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreeNode;

import events.Observer;
import events.Observable;
import gui.Frame1;

// klasa za cvor u stablu
public class Node1 implements TreeNode, Observable,Serializable
{

	private ArrayList<Node1> children = new ArrayList<Node1>(); // array lista dece datog cvora
	private String name; // ime cvora
	private Node1 parent; // roditelj cvora
	private String text; // sadrzaj(tekst) cvora
	private int numberForName;
	private int indexInChildrenArray=-1;

	private transient ArrayList<Observer> listeners = new ArrayList<>(); // array lista listenera datog cvora

	public Node1() // konstruktor koji koristimo za cvor u klasi PanelRB
	{
	}

	public Node1(String name) // konstruktor za cvor koji je Root stabla
	{
		this.name = name;
		this.parent = null;
		this.numberForName = 1;
		lazyLoad();
	}

	public Node1(String name, Node1 parent) // konstruktor za bilo koji drugi cvor
	{
		this.name = name;
		this.parent = parent;
		this.numberForName = 1;
		lazyLoad();
	}
	
	public void lazyLoad()
	{
		if(children==null)
			children = new ArrayList<Node1>();
		if(listeners==null)
			listeners = new ArrayList<>(); 
	}

	@Override
	public Enumeration children() // vraca svu decu cvora
	{
		lazyLoad();
		return this.children();
	}

	@Override
	public boolean getAllowsChildren() // stavljamo da svaki cvor moze da ima decu
	{
		return true;
	}

	@Override
	public TreeNode getChildAt(int arg0) // vraca dete koje se nalazi na zadatom indeksu
	{
		lazyLoad();
		return this.children.get(arg0);
	}

	@Override
	public int getChildCount() // vraca broj dece cvora
	{
		lazyLoad();
		return this.children.size();
	}

	@Override
	public int getIndex(TreeNode arg0) // vraca indeks prosledjenog cvora u listi dece
	{
		lazyLoad();
		return this.children.indexOf(arg0);
	}

	@Override
	public TreeNode getParent() // vraca roditelja cvora
	{
		return this.parent;
	}

	public void setParent(Node1 parent)
	{
		this.parent = parent;
	}
	
	@Override
	public boolean isLeaf() // vraca da li je cvor list
	{
		lazyLoad();
		if (this.children.size() == 0)
			return true;
		return false;
	}

	public String getName() // geter za ime cvora
	{
		return name;
	}

	public void setName(String name) // seter za ime cvora
	{
		this.name = name;
		notify(this);
	}

	@Override
	public String toString() // toString metoda
	{
		return name;
	}

	public ArrayList<Node1> getChildren()
	{
		lazyLoad();
		return children;
	}

	public void addChild(Node1 node) // funkcija za dodavanje dece cvoru
	{
		lazyLoad();
		if(node.indexInChildrenArray==-1)
			node.indexInChildrenArray=this.children.size();
		//System.out.println(node.indexInChildrenArray+" index");
		// dodajemo cvor u listu dece
		this.children.add(node.indexInChildrenArray,node);
		
		for(int i=0;i<children.size();i++)
			children.get(i).indexInChildrenArray=i;
		
		// setujemo ime i kod tog cvora dodavajuci . i njegov indeks u nizu dece
		// njegovog roditelja na kod njegovog roditelja
		node.setName(node.getName() + " " + this.numberForName);
		this.numberForName++;
		// radimo update da bi nam se na ekranu prikazale promene koje smo napravili
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
		notify(this);
	}
	
	public void deleteMore(ArrayList<Node1> nodes)
	{
		lazyLoad();
		for(int i=0;i<nodes.size();i++)
			children.remove(nodes.get(i));
		for(int i=0;i<children.size();i++)
			children.get(i).indexInChildrenArray=i;
	}

	public void delete(Node1 node) // funkcija za brisanje deteta iz cvora
	{
		lazyLoad();
		children.remove(node);
		for(int i=0;i<children.size();i++)
			children.get(i).indexInChildrenArray=i;
	}

	// geteri i seteri

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
		notify(this);
	}

	@Override
	public void addListener(Observer listener) // dodavanje listenera na cvor
	{
		lazyLoad();
		listeners.add(listener);
	}

	@Override
	public void removeListener(Observer listener) // sklanjanje listenera sa cvora
	{
		lazyLoad();
		listeners.remove(listener);
	}

	@Override
	public void notify(Object o)
	{
		lazyLoad();
		// ova funkcija radi funkciju update na svim listenerima koji se trenutno nalaze
		// u cvoru
		for (int i = 0; i < listeners.size(); i++)
			listeners.get(i).update(o);
	}
	
	public int getListenersCount()
	{
		lazyLoad();
		return listeners.size();
	}

	public ArrayList<Observer> getListeners()
	{
		return listeners;
	}
	
	public void setListeners(ArrayList<Observer> listeners)
	{
		this.listeners = listeners;
	}
	
	public int getIndexInChildrenArray()
	{
		return indexInChildrenArray;
	}
	
	public void setIndexInChildrenArray(int indexInChildrenArray)
	{
		this.indexInChildrenArray = indexInChildrenArray;
	}

	
}
