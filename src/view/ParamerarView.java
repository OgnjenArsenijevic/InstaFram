package view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.Main;
import controller.KeyController;
import controller.MessageController;
import controller.MouseController;
import gui.Frame1;
import model.workspace.AuthorP;
import model.workspace.CustomP;
import model.workspace.DesktopShortcutP;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.LookAndFeelP;
import model.workspace.NameP;
import model.workspace.StartAfterP;
import model.workspace.Parametar;

public class ParamerarView extends JPanel
{

	private JTextField tfNameP;
	private JTextField tfName;
	private JTextField tfSurname;

	public ParamerarView(Parametar p)
	{
		//System.out.println("aa");
		if (p instanceof NameP)
			NamePView(p);
		if (p instanceof AuthorP)
			AuthorPView(p);
		if (p instanceof LogoP)
			LogoPView(p);
		if (p instanceof LicenceOfAgreementP)
			LicenceOfAgreementPView(p);
		if (p instanceof LookAndFeelP)
			LookAndFeelPView(p);
		if (p instanceof DesktopShortcutP)
			DesktopShortcutPView(p);
		if (p instanceof StartAfterP)
			StartAfterPView(p);
		if (p instanceof CustomP)
			CustomPView(p);
	}

	private void NamePView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JLabel lbl = new JLabel("Product name: ");
		tfNameP = new JTextField();
		tfNameP.setText(((NameP) p).getNameP());
		tfNameP.addKeyListener(new KeyController());
		tfNameP.setPreferredSize(new Dimension(120, 20));
		panel.add(lbl);
		panel.add(tfNameP);
		this.setBorder(new EmptyBorder(50, 10, 10, 10));
		this.add(panel, BorderLayout.CENTER);
	}

	private void AuthorPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		// panel.setLayout(new GridLayout(2, 2));
		JLabel lblName = new JLabel("   Name:   ");
		JLabel lblSurname = new JLabel("Surname: ");
		tfName = new JTextField();
		tfSurname = new JTextField();
		tfName.setText(((AuthorP) p).getAuthorName());
		tfSurname.setText(((AuthorP) p).getAuthorSurname());
		tfName.setPreferredSize(new Dimension(120, 20));
		tfSurname.setPreferredSize(new Dimension(120, 20));
		tfName.addKeyListener(new KeyController());
		tfSurname.addKeyListener(new KeyController());
		panel.add(lblName);
		panel.add(tfName);
		panel1.add(lblSurname);
		panel1.add(tfSurname);
		this.setBorder(new EmptyBorder(50, 10, 50, 10));
		this.add(panel, BorderLayout.CENTER);
		this.add(panel1, BorderLayout.SOUTH);
	}

	private void LogoPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JLabel lblLogo = new JLabel("Logo: ");
		JTextField jtf = new JTextField();
		jtf.setText(((LogoP) p).getImageFile());
		jtf.setPreferredSize(new Dimension(270, 20));
		jtf.addKeyListener(new KeyController());
		JButton btnBrowse = new JButton("Browse");
		JButton btnPreview = new JButton("Preview");
		btnBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("image", "jpg", "png", "jpeg", "gif");
				fc.setFileFilter(filter);
				if (fc.showOpenDialog(Frame1.getInstance()) != 0)
					return;
				File file = fc.getSelectedFile();
				if (file == null)
					return;
				String s = file.getPath();
				jtf.setText(s);
				((LogoP) p).setImageFile(s);
				Main.saved=false;
			}
		});
		btnPreview.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File file = new File(jtf.getText());
				if (file.exists())
				{
					if (validExtension(jtf.getText(), (LogoP) p))
					{
						ImageIcon imgIcon = new ImageIcon(jtf.getText());
						int height = imgIcon.getIconHeight();
						int width = imgIcon.getIconWidth();
						while (height > 400)
							height /= 2;
						while (width > 400)
							width /= 2;
						Image image = getScaledImage(imgIcon.getImage(), width, height);
						imgIcon.setImage(image);
						JDialog frame = new JDialog();
						frame.setModalityType(ModalityType.APPLICATION_MODAL);
						JLabel lbl = new JLabel(imgIcon);
						frame.setTitle("Logo preview");
						frame.add(new JScrollPane(lbl), BorderLayout.CENTER);
						frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						frame.setSize(430, 430);
						frame.pack();
						frame.setLocationRelativeTo(Frame1.getInstance());
						frame.setVisible(true);
					}
					else
						MessageController.errorMessage("Please select valid image");
				}
				else
					MessageController.errorMessage("File not found");
			}
		});

		panel.add(lblLogo);
		panel.add(jtf);
		panel.add(btnBrowse);
		panel1.add(btnPreview);
		this.setBorder(new EmptyBorder(50, 10, 50, 10));
		this.add(panel, BorderLayout.CENTER);
		this.add(panel1, BorderLayout.SOUTH);
	}

	private void LicenceOfAgreementPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JLabel lblLicence = new JLabel("Licence of Agreement: ");
		JTextField jtf = new JTextField();
		jtf.setText(((LicenceOfAgreementP) p).getFilePath());
		jtf.setPreferredSize(new Dimension(230, 20));
		jtf.addKeyListener(new KeyController());
		JButton btnBrowse = new JButton("Browse");
		JButton btnPreview = new JButton("Preview");
		btnBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("text", "txt");
				fc.setFileFilter(filter);
				if (fc.showOpenDialog(Frame1.getInstance()) != 0)
					return;
				File file = fc.getSelectedFile();
				if (file == null)
					return;
				String s = file.getPath();
				jtf.setText(s);
				((LicenceOfAgreementP) p).setFilePath(s);
				Main.saved=false;
			}
		});

		btnPreview.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File file = new File(jtf.getText());
				if (file.exists())
				{
					if (validExtension(jtf.getText(), (LicenceOfAgreementP) p))
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
						frame.setTitle("Preview");
						JPanel panel = new JPanel();
						panel.add(new JScrollPane(lbl));
						frame.add(panel, BorderLayout.CENTER);
						frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						frame.setSize(700, 700);
						frame.pack();
						frame.setLocationRelativeTo(Frame1.getInstance());
						frame.setVisible(true);
					}
					else
						MessageController.errorMessage("Please select valid textual file");
				}
				else
					MessageController.errorMessage("File not found");
			}
		});

		panel.add(lblLicence);
		panel.add(jtf);
		panel.add(btnBrowse);
		panel1.add(btnPreview);
		this.setBorder(new EmptyBorder(50, 10, 50, 10));
		this.add(panel, BorderLayout.CENTER);
		this.add(panel1, BorderLayout.SOUTH);
	}

	private void LookAndFeelPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lblLicence = new JLabel("Choose Look And Feel: ");
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		panel.add(lblLicence);
		// String items[]= new String[looks.length];
		for (UIManager.LookAndFeelInfo look : looks)
		{
			JRadioButton jrb = new JRadioButton(look.getName());
			if (((LookAndFeelP) p).getLookAndFeels().contains(jrb.getText()))
				jrb.setSelected(true);
			jrb.addItemListener(new ItemListener()
			{
				@Override
				public void itemStateChanged(ItemEvent e)
				{
					Main.saved=false;
					Object item = e.getItem();
					String txt = ((JRadioButton) item).getText();
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if (((LookAndFeelP) p).getLookAndFeels() == null
								|| !((LookAndFeelP) p).getLookAndFeels().contains(txt))
							((LookAndFeelP) p).getLookAndFeels().add(txt);
					}
					else
					{
						if (((LookAndFeelP) p).getLookAndFeels() != null
								&& ((LookAndFeelP) p).getLookAndFeels().contains(txt))
							((LookAndFeelP) p).getLookAndFeels().remove(txt);
					}
				}
			});
			panel1.add(jrb);
		}

		JButton btnPreview = new JButton("Preview");

		btnPreview.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (UIManager.LookAndFeelInfo look : looks)
				{
					try
					{
						UIManager.setLookAndFeel(look.getClassName());
						String s = "This is "+look.getName()+" Look and Feel";
						JOptionPane.showMessageDialog(null, s, "Look and Feel Preview",
								JOptionPane.INFORMATION_MESSAGE);
					}
					catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1)
					{
						
					}
				}
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1)
				{
				}

			}
		});
		panel2.add(btnPreview);
		this.setBorder(new EmptyBorder(50, 10, 50, 10));
		this.add(panel, BorderLayout.NORTH);
		this.add(panel1, BorderLayout.CENTER);
		this.add(panel2, BorderLayout.SOUTH);
	}
	
	private void DesktopShortcutPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JCheckBox jcb=new JCheckBox("Allow user to create desktop shortcut?");
		if(((DesktopShortcutP)p).isAllowShortcut())
			jcb.setSelected(true);
		jcb.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				Main.saved=false;
				if (e.getStateChange() == ItemEvent.SELECTED)
					((DesktopShortcutP)p).setAllowShortcut(true);
				else
					((DesktopShortcutP)p).setAllowShortcut(false);
			}
		});
		panel.add(jcb);
		this.setBorder(new EmptyBorder(50, 10, 10, 10));
		this.add(panel,BorderLayout.CENTER);
	}
	
	private void StartAfterPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		JCheckBox jcb=new JCheckBox("Allow user to start app after installation?");
		if(((StartAfterP)p).isAllowStartAfter())
			jcb.setSelected(true);
		jcb.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				Main.saved=false;
				if (e.getStateChange() == ItemEvent.SELECTED)
					((StartAfterP)p).setAllowStartAfter(true);
				else
					((StartAfterP)p).setAllowStartAfter(false);
			}
		});
		panel.add(jcb);
		this.setBorder(new EmptyBorder(50, 10, 10, 10));
		this.add(panel,BorderLayout.CENTER);
	}
	
	private void CustomPView(Parametar p)
	{
		this.setLayout(new BorderLayout());
		JPanel pan=new JPanel();
		JPanel pan1=new JPanel();
		if(((CustomP)p).getOption()==0)
		{
			pan.add(new JLabel(((CustomP)p).getLabel()));
			JTextField jtf=new JTextField();
			jtf.setText(((CustomP)p).getTxt());
			jtf.addKeyListener(new KeyController());
			jtf.setPreferredSize(new Dimension(150, 20));
			pan.add(jtf);
			this.setBorder(new EmptyBorder(50, 10, 10, 10));
			this.add(pan,BorderLayout.CENTER);
		}
		if(((CustomP)p).getOption()==1)
		{
			JCheckBox checkBox=new JCheckBox(((CustomP)p).getLabel());
			pan.add(checkBox);
			if(((CustomP)p).isSelected())
				checkBox.setSelected(true);
			checkBox.addItemListener(new ItemListener()
			{	
				@Override
				public void itemStateChanged(ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
						((CustomP)p).setSelected(true);
					else
						((CustomP)p).setSelected(false);
				}
			});
			this.setBorder(new EmptyBorder(50, 10, 10, 10));
			this.add(pan,BorderLayout.CENTER);
		}
		if(((CustomP)p).getOption()==2)
		{
			pan.add(new JLabel(((CustomP)p).getLabel()));
			JRadioButton jrb1=new JRadioButton(((CustomP)p).getlRB1());
			JRadioButton jrb2=new JRadioButton(((CustomP)p).getlRB2());
			if(((CustomP)p).isSelectedRB1())
				jrb1.setSelected(true);
			if(((CustomP)p).isSelectedRB2())
				jrb2.setSelected(true);
			jrb1.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(jrb1.isSelected())
					{
						jrb2.setSelected(false);
						((CustomP)p).setSelectedRB1(true);
						((CustomP)p).setSelectedRB2(false);
					}
					else
						((CustomP)p).setSelectedRB1(false);
				}
			});
			jrb2.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(jrb2.isSelected())
					{
						jrb1.setSelected(false);
						((CustomP)p).setSelectedRB1(false);
						((CustomP)p).setSelectedRB2(true);
					}
					else
						((CustomP)p).setSelectedRB2(false);
				}
			});
			pan1.add(jrb1);
			pan1.add(jrb2);
			this.setBorder(new EmptyBorder(50, 10, 50, 10));
			this.add(pan, BorderLayout.CENTER);
			this.add(pan1, BorderLayout.SOUTH);
		}
	}

	public JTextField getTfNameP()
	{
		return tfNameP;
	}

	public JTextField getTfName()
	{
		return tfName;
	}

	public JTextField getTfSurname()
	{
		return tfSurname;
	}

	private boolean validExtension(String nameWithExtension, Parametar p)
	{
		String s = "";
		int i = nameWithExtension.length() - 1;
		int cnt = 0;
		while (nameWithExtension.charAt(i) != '.' && cnt < 5)
		{
			s += nameWithExtension.charAt(i);
			i--;
			cnt++;
		}
		s = new StringBuilder(s).reverse().toString();
		if (p instanceof LogoP)
			return (s.equals("png") || s.equals("jpg") || s.equals("jpeg"));
		return (s.equals("txt"));
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

	private String convertToMultiline(String original)
	{
		return "<html>" + original.replaceAll("\n", "<br>");
	}
	
}
