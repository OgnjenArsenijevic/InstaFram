package view;

import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.workspace.Node1;

// u ovoj klasi dodajemo slicice cvorovima u stablu
// u zavisnosti od toga da li je list ili nije cvor u stablu dobije jednu od dve moguce slicice
// ako se slucajno desi da neka slicica nije pronadjena vratice se greska i cvor ce biti bez slicice
public class NodeTreeCellRendered extends DefaultTreeCellRenderer
{

	public NodeTreeCellRendered()
	{
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (value instanceof Node1)
		{
			if (((Node1) value).isLeaf())
			{
				ImageIcon imageIcon = null;
				File file = new File("src/images/open4.png");
				if (file.exists())
				{
					imageIcon = new ImageIcon("src/images/open4.png");
					setIcon(imageIcon);
				}
				else
					System.err.println("Resource not found: " + "src/images/pdf.png");
			}
			else
			{
				ImageIcon imageIcon = null;
				File file = new File("src/images/open3.png");
				if (file.exists())
				{
					imageIcon = new ImageIcon("src/images/open3.png");
					setIcon(imageIcon);
				}
				else
					System.err.println("Resource not found: " + "src/images/pdf.png");
			}

		}

		return this;
	}

}
