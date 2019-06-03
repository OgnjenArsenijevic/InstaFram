package model.workspace;

import javax.swing.tree.DefaultTreeModel;

// nas treeModel, koristi se u klasi Frame1
public class NodeModel extends DefaultTreeModel
{

	public NodeModel() // konstruktor
	{
		super(new Kompanija("Kompanija"));

	}

	/*public void addChild(Node1 node) // dodavanje cvora na root
	{
		((Node1) getRoot()).addChild(node);
	}*/

}
