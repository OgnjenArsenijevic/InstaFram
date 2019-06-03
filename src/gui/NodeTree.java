package gui;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import view.NodeTreeCellRendered;
import view.NodeTreeEditor;

public class NodeTree extends JTree
{

	public NodeTree()
	{
		// setujemo cellEditor i prosledjujemo mu nas treeEditor
		// pogledati klasu NodeTreeEditor za vise detalja
		setCellEditor(new NodeTreeEditor(this, new DefaultTreeCellRenderer()));
		// setujemo cellRenderer, prosledjujemo mu nasu klasu za cellRenderer
		// pogledati implementaciju klase NodeTreeCellRendered
		setCellRenderer(new NodeTreeCellRendered());
		// postavljamo da je moguce praviti promene na cvoru
		setEditable(true);
	}

	// funkcija za dodavanje deteta
	/*
	 * public void addChild(Node1 node) { ((Node1) getModel()).addChild(node);
	 * SwingUtilities.updateComponentTreeUI(this); }
	 */
}
