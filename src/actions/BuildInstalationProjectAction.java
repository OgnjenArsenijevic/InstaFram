package actions;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import app.Main;
import controller.MessageController;
import gui.Frame1;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.Modul;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;
import view.InstallationView;

public class BuildInstalationProjectAction extends AbstractActionI
{
	public BuildInstalationProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/install.png"));
		putValue(NAME, "mBuildInstallation");
		putValue(SHORT_DESCRIPTION, "mBuildInstallation");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(Main.directory);
		int chosen = fileChooser.showOpenDialog(null);
		if (chosen != 1)
		{
			try
			{
				while (!fileChooser.getSelectedFile().exists())
				{
					MessageController.errorMessage("File not found");
					fileChooser.setSelectedFile(null);
					chosen = fileChooser.showOpenDialog(null);
				}
				if (chosen == 1)
					fileChooser.setSelectedFile(null);
				Scanner sc = new Scanner(fileChooser.getSelectedFile());

				Main.directory = fileChooser.getCurrentDirectory();
				Main.currFile = fileChooser.getSelectedFile();
				FileInputStream fileInputStream = null;
				ObjectInputStream in = null;
				Main.saved = false;
				try
				{
					fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
					in = new ObjectInputStream(fileInputStream);

					Object curr = in.readObject();

					if (!(curr instanceof Proizvod))
					{
						in.close();
						fileInputStream.close();
						MessageController.errorMessage("Please select valid file");
						return;
					}
					((Node1) curr).setIndexInChildrenArray(-1);
					// ((Kompanija)
					// Frame1.getInstanceOfFrame1().getNodeModel().getRoot()).addChild((Node1)
					// curr);
					// ((Node1) curr).setParent((Kompanija)
					// Frame1.getInstanceOfFrame1().getNodeModel().getRoot());
					String s = fileChooser.getSelectedFile().getAbsolutePath();
					int cnt = s.length() - 1;
					while (cnt > 0)
					{
						if (s.charAt(cnt) == '\\' || s.charAt(cnt) == '/')
						{
							break;
						}
						cnt--;
					}
					s = s.substring(0, cnt);
					((Proizvod) curr).setFilePath(s + "/" + ((Proizvod) curr).getFilePath());
					ArrayList<Parametar> list = new ArrayList<>();
					for (int i = 0; i < ((Proizvod) curr).getChildren().size(); i++)
					{
						if (((Proizvod) curr).getChildren().get(i) instanceof LogoP
								|| ((Proizvod) curr).getChildren().get(i) instanceof LicenceOfAgreementP)
							list.add((Parametar) ((Proizvod) curr).getChildren().get(i));
						else
						{
							if (((Proizvod) curr).getChildren().get(i) instanceof Modul)
							{
								Modul mm = (Modul) ((Proizvod) curr).getChildren().get(i);
								for (int j = 0; j < mm.getChildren().size(); j++)
								{
									if (mm.getChildren().get(j) instanceof LogoP
											|| mm.getChildren().get(j) instanceof LicenceOfAgreementP)
										list.add((Parametar) mm.getChildren().get(j));
								}
							}
						}
					}
					for (Parametar p : list)
					{
						if (p instanceof LicenceOfAgreementP)
							((LicenceOfAgreementP) p).setFilePath(s + "/" + ((LicenceOfAgreementP) p).getFilePath());
						else
							((LogoP) p).setImageFile(s + "/" + ((LogoP) p).getImageFile());
					}
					startInstallation((Proizvod) curr);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					try
					{
						in.close();
						fileInputStream.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

				}
				catch (EOFException e)
				{
					try
					{
						in.close();
						fileInputStream.close();
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}

				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (ClassCastException e)
				{

				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (UnsupportedLookAndFeelException e)
				{
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e)
			{
			}
			catch (NullPointerException e)
			{
			}
		}
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
	}

	private void startInstallation(Proizvod p)
	{
		JDialog dialog = new JDialog();
		dialog.setLayout(new BorderLayout());
		dialog.setLocationRelativeTo(null);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.setTitle("Installation");
		dialog.setSize(800, 150);
		JLabel lbl = new JLabel("Choose moduls for installation:    ");
		JPanel pan1 = new JPanel();
		JPanel pan2 = new JPanel();
		JPanel pan3 = new JPanel();
		pan1.add(lbl);
		System.out.println(p.getChildren());
		for (int i = 0; i < p.getChildren().size(); i++)
		{
			if (p.getChildren().get(i) instanceof Modul)
			{
				Modul m = (Modul) p.getChildren().get(i);
				m.setSelected(false);
			}
		}
		for (int i = 0; i < p.getChildren().size(); i++)
		{
			if (p.getChildren().get(i) instanceof Modul)
			{
				JRadioButton jrb = new JRadioButton(((Modul) p.getChildren().get(i)).getName());
				Modul m = (Modul) p.getChildren().get(i);
				jrb.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent e)
					{
						if (e.getStateChange() == ItemEvent.SELECTED)
							m.setSelected(true);
						else
							m.setSelected(false);
					}
				});
				pan1.add(jrb);
			}
		}
		System.out.println("exit");
		JLabel lbl1 = new JLabel("Choose destination folder");
		JTextField jtf = new JTextField();
		jtf.setPreferredSize(new Dimension(300, 20));
		jtf.setEditable(false);
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(Main.directory);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.showSaveDialog(null);
					jtf.setText(chooser.getSelectedFile().getAbsolutePath());
				}
				catch (Exception e2)
				{
				}
			}
		});

		pan2.add(lbl1);
		pan2.add(jtf);
		pan2.add(btnBrowse);
		JButton btn = new JButton("Next");

		btn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File source = new File(p.getFilePath());
				p.setDestination(jtf.getText() + "/" + source.getName());
				ArrayList<Parametar> list = new ArrayList<>();
				for (int i = 0; i < p.getChildren().size(); i++)
				{
					if (p.getChildren().get(i) instanceof Modul)
					{
						Modul m = (Modul) p.getChildren().get(i);
						if (m.isSelected())
						{
							for (int j = 0; j < m.getChildren().size(); j++)
								list.add((Parametar) m.getChildren().get(j));
						}
					}
					else
						list.add((Parametar) p.getChildren().get(i));
				}
				System.out.println(list);
				p.setInstallationParameters(list);
				p.setDesktopShortcut(false);
				p.setStartAfter(false);
				p.setCurrParameterIndex(0);
				dialog.dispose();
				while (p.getCurrParameterIndex() <= p.getInstallationParameters().size())
				{
					System.out.println(p.getCurrParameterIndex());
					InstallationView view = new InstallationView(p);
					System.out.println(p.getCurrParameterIndex());
					System.out.println();
					if (p.getCurrParameterIndex() == -1)
						break;
				}
			}
		});

		pan3.add(btn);
		dialog.add(pan1, BorderLayout.NORTH);
		dialog.add(pan2, BorderLayout.CENTER);
		dialog.add(pan3, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}
}
