package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import app.Main;
import controller.KeyController;
import gui.Frame1;
import model.workspace.Modul;
import model.workspace.Proizvod;

public class ProizvodView extends JPanel
{
	public ProizvodView(Proizvod p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		//JPanel panel1 = new JPanel();
		JLabel lblPath = new JLabel("Proizvod: ");
		JTextField jtf = new JTextField();
		jtf.setText(((Proizvod) p).getFilePath());
		jtf.setPreferredSize(new Dimension(230, 20));
		jtf.addKeyListener(new KeyController());
		JButton btnBrowse = new JButton("Browse");
	//	JButton btnPreview = new JButton("Preview");
		btnBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final JFileChooser fc = new JFileChooser();
				//FileFilter filter = new FileNameExtensionFilter("text", "txt");
				//fc.setFileFilter(filter);
				if (fc.showOpenDialog(Frame1.getInstance()) != 0)
					return;
				File file = fc.getSelectedFile();
				if (file == null)
					return;
				String s = file.getPath();
				jtf.setText(s);
				((Proizvod)p).setFilePath(s);
				Main.saved=false;
			}
		});

		panel.add(lblPath);
		panel.add(jtf);
		panel.add(btnBrowse);
	//	panel1.add(btnPreview);
		this.setBorder(new EmptyBorder(50, 10, 50, 10));
		this.add(panel, BorderLayout.CENTER);
	//	this.add(panel1, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}
