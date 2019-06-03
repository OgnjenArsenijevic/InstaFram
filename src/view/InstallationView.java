package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.xml.transform.Source;

import controller.KeyController;
import controller.MessageController;
import gui.Frame1;
import model.workspace.NameP;
import model.workspace.AuthorP;
import model.workspace.CustomP;
import model.workspace.DesktopShortcutP;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.LookAndFeelP;
import model.workspace.Parametar;
import model.workspace.Proizvod;
import model.workspace.StartAfterP;

public class InstallationView
{

	private JButton btnNext = new JButton("Next");
	private JButton btnPrev = new JButton("Previous");
	private JButton btnCancel = new JButton("Cancel");
	private JDialog dialog;

	public InstallationView(Proizvod p)
	{
		dialog = new JDialog();
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				p.setCurrParameterIndex(-1);
				super.windowClosing(e);
			}
		});
		dialog.setTitle("Installation");
		dialog.setLayout(new BorderLayout());
		dialog.setLocationRelativeTo(Frame1.getInstance());
		dialog.setSize(new Dimension(400, 400));
		if (p.getCurrParameterIndex() == p.getInstallationParameters().size())
		{
			EndView(p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof NameP)
		{
			NamePView((NameP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof AuthorP)
		{
			AuthorPView((AuthorP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof LogoP)
		{
			LogoPView((LogoP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof LicenceOfAgreementP)
		{
			LicenceOfAgreementPView((LicenceOfAgreementP) p.getInstallationParameters().get(p.getCurrParameterIndex()),
					p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof LookAndFeelP)
		{
			LookAndFeelPView((LookAndFeelP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof DesktopShortcutP)
		{
			DesktopShortcutPView((DesktopShortcutP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof StartAfterP)
		{
			StartAfterPView((StartAfterP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
		if (p.getInstallationParameters().get(p.getCurrParameterIndex()) instanceof CustomP)
		{
			CustomPView((CustomP) p.getInstallationParameters().get(p.getCurrParameterIndex()), p);
			return;
		}
	}

	private void NamePView(NameP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lblNameP = new JLabel("Product name: " + pp.getNameP());
		JPanel pan = new JPanel();
		pan.add(lblNameP);
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void AuthorPView(AuthorP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel(convertToMultiline(
				"Author Name: " + pp.getAuthorName() + "\n\n" + "Author Surname: " + pp.getAuthorSurname()));
		JPanel pan = new JPanel();
		pan.add(lbl);
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void LogoPView(LogoP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel("Logo :");
		JPanel pan = new JPanel();
		pan.add(lbl);
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		JPanel pan2 = new JPanel();
		ImageIcon imgIcon = new ImageIcon(pp.getImageFile());
		int height = imgIcon.getIconHeight();
		int width = imgIcon.getIconWidth();
		while (height > 150)
			height /= 2;
		while (width > 150)
			width /= 2;
		Image image = getScaledImage(imgIcon.getImage(), width, height);
		imgIcon.setImage(image);
		JLabel lbl1 = new JLabel(imgIcon);
		pan2.add(lbl1);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan2.setBorder(new EmptyBorder(10, 0, 50, 0));
		pan1.setBorder(new EmptyBorder(10, 0, 50, 0));
		dialog.add(pan, BorderLayout.NORTH);
		dialog.add(pan2, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void LicenceOfAgreementPView(LicenceOfAgreementP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel("Licence of Agreement");
		JButton btnRead = new JButton("Read");
		btnRead.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File file = new File(pp.getFilePath());
				if (file.exists())
				{
					String s = "Licence of Agreement\n";
					Scanner sc;
					try
					{
						sc = new Scanner(file);
						while (sc.hasNextLine())
							s += sc.nextLine() + "\n";
					}
					catch (FileNotFoundException e1)
					{
					}
					JDialog frame = new JDialog();
					frame.setModalityType(ModalityType.APPLICATION_MODAL);
					frame.setLayout(new BorderLayout());
					JLabel lbl = new JLabel(convertToMultiline(s));
					frame.setTitle("Licence of Agreement");
					JPanel panel = new JPanel();
					panel.add(new JScrollPane(lbl));
					frame.add(panel, BorderLayout.CENTER);
					frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame.setSize(700, 700);
					frame.pack();
					frame.setLocationRelativeTo(Frame1.getInstance());
					frame.setVisible(true);
				}
			}
		});
		JPanel pan = new JPanel();
		pan.add(lbl);
		pan.add(btnRead);
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void LookAndFeelPView(LookAndFeelP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel("Licence of Agreement");
		JComboBox<String> cb = new JComboBox<>(pp.getLookAndFeels().toArray(new String[0]));
		JPanel pan = new JPanel();
		pan.add(lbl);
		pan.add(cb);
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		JButton btnNextWithLAF = new JButton("Next");
		btnNextWithLAF.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
				for (UIManager.LookAndFeelInfo look : looks)
				{
					if (look.getName().equals((String) cb.getSelectedItem()))
						try
						{
							UIManager.setLookAndFeel(look.getClassName());
						}
						catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException e1)
						{
							e1.printStackTrace();
						}
				}
				p.setCurrParameterIndex(p.getCurrParameterIndex() + 1);
				dialog.dispose();
			}
		});
		pan1.add(btnNextWithLAF);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void DesktopShortcutPView(DesktopShortcutP pp, Proizvod p)
	{
		p.setDesktopShortcut(false);
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel();
		JPanel pan = new JPanel();
		if (!pp.isAllowShortcut())
		{
			lbl.setText("Desktop shortcut is not allowed");
			p.setDesktopShortcut(false);
			pan.add(lbl);
		}
		else
		{
			JCheckBox cb = new JCheckBox("Create Desktop Shortcut?");
			cb.addItemListener(new ItemListener()
			{
				@Override
				public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
						p.setDesktopShortcut(true);
					else
						p.setDesktopShortcut(false);
				}
			});
			pan.add(cb);
		}
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void StartAfterPView(StartAfterP pp, Proizvod p)
	{
		p.setStartAfter(false);
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JLabel lbl = new JLabel();
		JPanel pan = new JPanel();
		if (!pp.isAllowStartAfter())
		{
			lbl.setText("Start after installation is not allowed");
			p.setStartAfter(false);
			pan.add(lbl);
		}
		else
		{
			JCheckBox cb = new JCheckBox("Start app after installation?");
			cb.addItemListener(new ItemListener()
			{
				@Override
				public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
						p.setStartAfter(true);
					else
						p.setStartAfter(false);
				}
			});
			pan.add(cb);
		}
		JPanel pan1 = new JPanel();
		if (tmp > 0)
			pan1.add(btnPrev);
		pan1.add(btnNext);
		pan1.add(btnCancel);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void CustomPView(CustomP pp, Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		addActions(p);
		JPanel pan = new JPanel();
		JPanel pan1 = new JPanel();
		if (pp.getOption() == 0)
		{
			pan.add(new JLabel(pp.getLabel()));
			JTextField jtf = new JTextField();
			jtf.setText(pp.getTxt());
			jtf.addKeyListener(new KeyController());
			jtf.setPreferredSize(new Dimension(150, 20));
			pan.add(jtf);
			pan.setBorder(new EmptyBorder(50, 10, 10, 10));
			dialog.add(pan, BorderLayout.CENTER);
		}
		if (pp.getOption() == 1)
		{
			JCheckBox checkBox = new JCheckBox(pp.getLabel());
			pan.add(checkBox);
			if (pp.isSelected())
				checkBox.setSelected(true);
			checkBox.addItemListener(new ItemListener()
			{
				@Override
				public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
						pp.setSelected(true);
					else
						pp.setSelected(false);
				}
			});
			pan.setBorder(new EmptyBorder(50, 10, 10, 10));
			dialog.add(pan, BorderLayout.CENTER);
		}
		if (pp.getOption() == 2)
		{
			pan.add(new JLabel(pp.getLabel()));
			JRadioButton jrb1 = new JRadioButton(pp.getlRB1());
			JRadioButton jrb2 = new JRadioButton(pp.getlRB2());
			if (pp.isSelectedRB1())
				jrb1.setSelected(true);
			if (pp.isSelectedRB2())
				jrb2.setSelected(true);
			jrb1.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (jrb1.isSelected())
					{
						jrb2.setSelected(false);
						pp.setSelectedRB1(true);
						pp.setSelectedRB2(false);
					}
					else
						pp.setSelectedRB1(false);
				}
			});
			jrb2.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (jrb2.isSelected())
					{
						jrb1.setSelected(false);
						pp.setSelectedRB1(false);
						pp.setSelectedRB2(true);
					}
					else
						pp.setSelectedRB2(false);
				}
			});
			pan1.add(jrb1);
			pan1.add(jrb2);
			pan.setBorder(new EmptyBorder(50, 10, 50, 10));
			dialog.add(pan, BorderLayout.NORTH);
			dialog.add(pan1, BorderLayout.CENTER);
		}
		JPanel pan2 = new JPanel();
		if (tmp > 0)
			pan2.add(btnPrev);
		pan2.add(btnNext);
		pan2.add(btnCancel);
		dialog.add(pan2, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void EndView(Proizvod p)
	{
		int tmp = p.getCurrParameterIndex();
		JLabel lbl = new JLabel("Thank you for using InstaFram");
		JPanel pan = new JPanel();
		pan.add(lbl);
		JPanel pan1 = new JPanel();
		JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				// System.out.println(desktopPath);
				File ss = new File(p.getFilePath());
				File dd = new File(p.getDestination());
				try
				{
					Files.copy(ss.toPath(), dd.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				catch (IOException e1)
				{
					MessageController.errorMessage("Destination folder does not exist");
					return;
				}
				if (p.isDesktopShortcut())
				{
					FileSystemView filesys = FileSystemView.getFileSystemView();
					File source = new File(p.getFilePath());
					File dest = new File(filesys.getHomeDirectory() + "/" + source.getName());
					try
					{
						Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
					catch (IOException e1)
					{
						// MessageController.errorMessage("Select valid destination folder");
						// return;
					}
				}
				if (p.isStartAfter())
				{
					if (!Desktop.isDesktopSupported())
					{
						MessageController.errorMessage("Desktop is not supported");
						return;
					}
					Desktop desktop = Desktop.getDesktop();
					File source = new File(p.getFilePath());
					if (source.exists())
						try
						{
							desktop.open(source);
						}
						catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				p.setCurrParameterIndex(p.getCurrParameterIndex() + 1);
				dialog.dispose();
			}
		});
		pan1.add(btnFinish);
		pan.setBorder(new EmptyBorder(50, 0, 0, 0));
		pan1.setBorder(new EmptyBorder(0, 0, 50, 0));
		dialog.add(pan, BorderLayout.CENTER);
		dialog.add(pan1, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private String convertToMultiline(String original)
	{
		return "<html>" + original.replaceAll("\n", "<br>");
	}

	private void addActions(Proizvod p)
	{
		btnNext.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				p.setCurrParameterIndex(p.getCurrParameterIndex() + 1);
				dialog.dispose();
			}
		});
		btnPrev.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				p.setCurrParameterIndex(p.getCurrParameterIndex() - 1);
				dialog.dispose();
			}
		});
		btnCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				p.setCurrParameterIndex(-1);
				dialog.dispose();
			}
		});
	}

	private Image getScaledImage(Image srcImg, int w, int h)
	{
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}
}
