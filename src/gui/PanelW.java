package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

// setujemo izgled panela sa stablom
public class PanelW extends JPanel
{

	public PanelW()
	{
		this.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(Frame1.getInstance().getNodeTree());
		this.add(scrollPane, BorderLayout.CENTER);
	}
	

}


