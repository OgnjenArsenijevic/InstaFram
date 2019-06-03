package gui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.KeyController;
import model.workspace.Modul;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;
import view.ParamerarView;
import view.ProizvodView;

// ekstendujemo klasu JScroolPane tako da nas ScroolPane moze da ima i textArea-u i cvor
public class JScrollPane1 extends JScrollPane
{
	private Node1 node;
	private JTextArea textArea;
	private String text;
	private ParamerarView view;
	private ProizvodView viewP;

	public JScrollPane1(Node1 node, JTextArea textArea)
	{
		super(textArea);
		this.node = node;
		this.textArea = textArea;
	}
	
	public JScrollPane1(Node1 node)
	{
		if(node instanceof Parametar)
		{
			this.node=node;
			this.text=node.getText();
			Parametar p=(Parametar)node;
			view=new ParamerarView(p);
			setViewportView(view);
		}
		else
		{
			if(node instanceof Proizvod)
			{
				this.node=node;
				this.text=node.getName();
				Proizvod p=(Proizvod)node;
				viewP=new ProizvodView(p);
				p.notify(p);
				setViewportView(viewP);
			}
			else
			{
				textArea=new JTextArea();
				textArea.setText(node.getText());
				textArea.addKeyListener(new KeyController());
				this.node=node;
				this.text=node.getText();
				setViewportView(textArea);
			}
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
	
	public ParamerarView getView()
	{
		return view;
	}

}
