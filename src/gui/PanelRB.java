package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayDeque;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import events.Observer;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;

public class PanelRB extends JPanel implements Observer
{
	private JTextArea textArea;
	private Node1 node = new Node1();

	// kreiramo panel i prosledjujemo listener cvoru
	public PanelRB()
	{
		this.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout());
		textArea = new JTextArea("");
		textArea.setEditable(false);
		node.addListener(this);
		this.add(new JScrollPane(textArea), BorderLayout.CENTER);
	}

	// ovo se desi kad node pozove notify
	@Override
	public void update(Object curr)
	{
		
		// proveravamo da li cvor postoji u tabbedPane-u, ako ne postoji ne treba nista
		// da uradimo i prekidamo rad funkcije
		try
		{
			boolean check = false;
			for (int i = 0; i < Frame1.getInstance().getPanelRT().getTabbedPane().getTabCount(); i++)
			{
				if (((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getComponentAt(i))
						.getNode() == (((Node1) curr)))
				{
					check = true;
					break;
				}
			}
			if (!check)
				return;
			// podesavamo tekst koji trema da se pojavi u donjem desnom panelu
			String s = "Ime �?vora: " + ((Node1) curr).getName() + "\n";
			if(curr instanceof Parametar == false && curr instanceof Proizvod == false)
				s += "Sadržaj �?vora: "
					+ ((JScrollPane1) Frame1.getInstance().getPanelRT().getTabbedPane().getSelectedComponent())
							.getTextArea().getText()
					+ "\n";
			s += "Naziv roditelja: " + ((Node1) ((Node1) curr).getParent()).getName() + "\n";
			s += "Broj direktnih potomaka: " + ((Node1) curr).getChildCount() + "\n";
			int cntLeaves = 0;

			// radimo bfs da prebrojimo koliko selektovani cvor ima listova, koji ne moraju
			// da mu budu direktni potomci
			ArrayDeque<Node1> deque = new ArrayDeque<>();
			deque.add((Node1) curr);
			while (deque.isEmpty() == false)
			{
				Node1 curr1 = deque.poll();
				if (curr1.isLeaf())
					cntLeaves++;
				for (int i = 0; i < curr1.getChildCount(); i++)
					deque.add((Node1) curr1.getChildAt(i));
			}
			if (((Node1) curr).isLeaf())
				cntLeaves = 0;
			s += "Br. listova medju potomcima: " + cntLeaves;
			textArea.setText(s);
		}
		catch (NullPointerException e)
		{
			// TODO: handle exception
		}
	}

	public Node1 getNode()
	{
		return node;
	}

	public JTextArea getTextArea()
	{
		return textArea;
	}

	
}
