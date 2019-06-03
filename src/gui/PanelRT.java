package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import controller.KeyController;
import controller.MouseController;
import model.workspace.Node1;

public class PanelRT extends JPanel
{
	private JTabbedPane tabbedPane = new JTabbedPane();

	public PanelRT()
	{
		// this.setBackground(Color.RED);
		this.setLayout(new BorderLayout());
		ToolBar1 toolBar1 = new ToolBar1(true);
		this.add(toolBar1, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addMouseListener(new MouseController());
	}

	// brisanje tabova iz tabbedPane-a
	public void removeTabs(Node1 node)
	{
		ArrayDeque<Node1> deque = new ArrayDeque<>();
		// radimo BFS za prosledjeni cvor i sve njegove podcvorove ako neki od njih
		// pronadjemo u tabbedPane-u brisemo ga iz tabbedPane-a
		deque.add(node);
		while (deque.isEmpty() == false)
		{
			int idx = -1;
			Node1 curr1 = deque.poll();
			for (int i = 0; i < tabbedPane.getTabCount(); i++)
			{
				if (((JScrollPane1) tabbedPane.getComponentAt(i)).getNode() == curr1)
				{
					idx = i;
					break;
				}
			}
			if (idx != -1)
				tabbedPane.remove(idx);
			for (int i = 0; i < curr1.getChildCount(); i++)
				deque.add((Node1) curr1.getChildAt(i));
		}
		// proveravamo da li ima otvorenih tabova u tabbedPane-u
		if (tabbedPane.getTabCount() > 0)
		{
			// ako ima otvorenih tabova onda im svima update-ujemo titl (posto su cvorovima
			// koji odgovaraju tim tabovima promenjena imena u prethodnom delu koda)
			for (int i = 0; i < tabbedPane.getTabCount(); i++)
				tabbedPane.setTitleAt(i, ((JScrollPane1) tabbedPane.getComponentAt(i)).getNode().getName());
			// selektujemo tab sa indeksom 0
			tabbedPane.setSelectedIndex(0);
			// uzimamo cvor koji odgovara selektovanom tabu
			Node1 node1 = ((JScrollPane1) tabbedPane.getComponentAt(0)).getNode();
			// update-ujemo sadrzaj donjeg desnog panela(PanelRT) na osnovu podataka iz
			// cvora selektovanog taba, koristeci staticku funkciju doWork
			// pogledati implementaciju staticke funkcije doWork u klasi Work za vise
			// detalja
			node1.notify(node1);
		}
	}

	// dodavanje novog taba na tabbedPane
	public void newTab(Node1 node)
	{
		JTextArea textArea = new JTextArea("");
		if (node.getText() != null && !node.getText().contains("null"))
			textArea.setText(node.getText());
		// stavljamo listener na textArea-u da mi mogli da menjamo dolji desni panel kad
		// promenimo text u textArea-i
		textArea.addKeyListener(new KeyController());
		boolean shouldAdd = true;
		for (int i = 0; i < tabbedPane.getTabCount(); i++)
		{
			if (((JScrollPane1) tabbedPane.getComponentAt(i)).getNode() == node)
			{
				shouldAdd = false;
				break;
			}
		}
		if (shouldAdd) // ako cvor ne postoji u tabbed paneu onda...
		{
			// dodamo taj cvor
			//JScrollPane1 jsp= new JScrollPane1(node, textArea);
			JScrollPane1 jsp= new JScrollPane1(node);
			tabbedPane.addTab(node.getName(), jsp);
			// i selektujemo tab koji odgovara tom cvoru
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		} else // inace postavljamo tab koji ima taj cvor da bude selektovan
		{
			int index = 0;
			for (int i = 0; i < tabbedPane.getTabCount(); i++)
			{
				if (((JScrollPane1) tabbedPane.getComponent(i)).getNode() == node)
				{
					index = i;
					break;
				}
			}
			tabbedPane.setSelectedIndex(index);
		}
	}

	public JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}

}
