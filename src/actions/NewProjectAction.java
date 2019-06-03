package actions;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import app.Main;
import commands.AddCommand;
import gui.Frame1;
import model.workspace.AuthorP;
import model.workspace.CustomP;
import model.workspace.DesktopShortcutP;
import model.workspace.Kompanija;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.LookAndFeelP;
import model.workspace.Modul;
import model.workspace.NameP;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;
import model.workspace.StartAfterP;

//ova klasa sluzi za dodavanje novog cvora
public class NewProjectAction extends AbstractActionI
{

	// u konstruktoru postavljamo accelerator
	// ucitavamo slicicu pozivajuci funkciju(kojoj prosledjujemo String sa putanjom
	// do fajla sa slicicom) iz klase AbstractActionI
	// setujemo ime i opis ikonice
	public NewProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/new1.jpg"));
		putValue(NAME, "mNew");
		putValue(SHORT_DESCRIPTION, "mNew");
	}

	// ova funkcija dodaje novi cvor u selektovani cvor
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		ArrayList<Node1> list = new ArrayList<>();
		// ako ne postoji ni jedan cvor dodajemo root
		if (Frame1.getInstance().getNodeModel().getRoot() == null)
		{
			Kompanija kompanija = new Kompanija("Kompanija");
			Frame1.getInstance().getNodeModel().setRoot(kompanija);
			list.add(kompanija);
			Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
		}
		else // inace...
		{
			// uzimamo poslednji selektovani cvor
			Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
			// Node1 node1 = new Node1(" ", (Node1) curr);
			try
			{
				if (curr instanceof Kompanija)
				{
					Proizvod proizvod = new Proizvod("Proizvod", (Kompanija) curr);
					((Kompanija) curr).addChild(proizvod);
					proizvod.addListener(Frame1.getInstance().getPanelRB());
					list.add(proizvod);
					Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
					Main.saved = false;
				}
				if (curr instanceof Proizvod)
				{
					ImageIcon imageIcon = (ImageIcon) loadIcon("src/images/question.png");

					JDialog jDialog = new JDialog();
					jDialog.setSize(new Dimension(400, 110));
					jDialog.setTitle("Question");
					jDialog.setLayout(new BorderLayout());
					jDialog.setLocationRelativeTo(Frame1.getInstance());
					jDialog.setModalityType(ModalityType.APPLICATION_MODAL);
					jDialog.setResizable(false);
					JLabel lbl = new JLabel(imageIcon);
					lbl.setFont(new Font("", 1, 12));
					lbl.setText("What component do you want to add?");
					JPanel panel1 = new JPanel(new FlowLayout());
					panel1.add(lbl);
					jDialog.add(panel1, BorderLayout.NORTH);
					JButton btnModul = new JButton("Modul");
					JButton btnParametar = new JButton("Parametar");
					JPanel panel2 = new JPanel(new FlowLayout());
					panel2.add(btnModul);
					panel2.add(btnParametar);
					jDialog.add(panel2, BorderLayout.CENTER);

					btnModul.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							jDialog.dispose();
							Modul modul = new Modul("Modul", (Proizvod) curr);
							((Proizvod) curr).addChild(modul);
							modul.addListener(Frame1.getInstance().getPanelRB());
							list.add(modul);
							Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
							Main.saved = false;
						}
					});

					btnParametar.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							jDialog.dispose();
							parametarWork(curr, 1);
						}
					});

					jDialog.setVisible(true);

				}
				if (curr instanceof Modul)
				{

					parametarWork(curr, 2);
				}
			}
			catch (NullPointerException e)
			{
				// samo je bilo bitno da uhvatimo ovaj exception
				// ne treba nista da se radi ovde
			}
		}
		// radimo update da bi nam se na ekranu prikazale promene koje smo napravili
		SwingUtilities.updateComponentTreeUI(Frame1.getInstance().getNodeTree());
	}

	private void parametarWork(Object curr, int num)
	{
		ImageIcon imageIcon = (ImageIcon) loadIcon("src/images/question.png");

		JDialog jDialog = new JDialog();
		jDialog.setSize(new Dimension(400, 110));
		jDialog.setTitle("Question");
		jDialog.setLayout(new BorderLayout());
		jDialog.setLocationRelativeTo(Frame1.getInstance());
		jDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		jDialog.setResizable(false);
		JLabel lbl = new JLabel(imageIcon);
		lbl.setFont(new Font("", 1, 12));
		lbl.setText("Choose parametar type");
		JPanel panel1 = new JPanel(new FlowLayout());
		String items[] = { "NameP", "AuthorP", "LogoP", "LicenceOfAgreementP", "LookAndFeelP", "DesktopSP",
				"StartAfterP", "CUSTOM" };
		JComboBox<String> jcb = new JComboBox<>(items);
		panel1.add(lbl);
		panel1.add(jcb);
		jDialog.add(panel1, BorderLayout.NORTH);
		JButton btnChoose = new JButton("Choose");
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(btnChoose);
		jDialog.add(panel2, BorderLayout.CENTER);

		btnChoose.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Parametar parametar = null;
				String s = jcb.getSelectedItem().toString();
				if (s.equals("NameP"))
				{
					if (num == 1)
					{
						NameP par = new NameP("NameP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						NameP par = new NameP("NameP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("AuthorP"))
				{
					if (num == 1)
					{
						AuthorP par = new AuthorP("AuthorP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						AuthorP par = new AuthorP("AuthorP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("LogoP"))
				{
					if (num == 1)
					{
						LogoP par = new LogoP("LogoP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						LogoP par = new LogoP("LogoP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("LicenceOfAgreementP"))
				{
					if (num == 1)
					{
						LicenceOfAgreementP par = new LicenceOfAgreementP("LicenceOfAgreementP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						LicenceOfAgreementP par = new LicenceOfAgreementP("LicenceOfAgreementP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("LookAndFeelP"))
				{
					if (num == 1)
					{
						LookAndFeelP par = new LookAndFeelP("LookAndFeelP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						LookAndFeelP par = new LookAndFeelP("LookAndFeelP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("DesktopSP"))
				{
					if (num == 1)
					{
						DesktopShortcutP par = new DesktopShortcutP("DesktopSP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						DesktopShortcutP par = new DesktopShortcutP("DesktopSP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("StartAfterP"))
				{
					if (num == 1)
					{
						StartAfterP par = new StartAfterP("StartAfterP", (Proizvod) curr);
						((Proizvod) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
					else
					{
						StartAfterP par = new StartAfterP("StartAfterP", (Modul) curr);
						((Modul) curr).addChild(par);
						par.addListener(Frame1.getInstance().getPanelRB());
						jDialog.dispose();
						parametar = par;
					}
				}
				if (s.equals("CUSTOM"))
				{
					jDialog.dispose();
					String options[] = { "TextField", "CheckBox", "2RadioButtons" };
					int chosen = JOptionPane.showOptionDialog(null,
							"Which component do you want to add to your custom parameter?", "Custom parameter",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (chosen == -1)
						return;
					JDialog jd = new JDialog();
					jd.setTitle("Label text");
					jd.setLocationRelativeTo(null);
					jd.setSize(new Dimension(230, 160));
					jd.setModalityType(ModalityType.APPLICATION_MODAL);
					jd.setLayout(new BorderLayout());
					JPanel pan = new JPanel();
					JPanel pan1 = new JPanel();
					JTextField jtf = new JTextField();
					jtf.setPreferredSize(new Dimension(140, 20));
					pan.add(new JLabel("Text for label: "));
					pan.add(jtf);
					JButton jbtn = new JButton("Confirm");
					pan1.add(jbtn);
					jd.add(pan, BorderLayout.CENTER);
					jd.add(pan1, BorderLayout.SOUTH);
					CustomP par;
					if (num == 1)
						par = new CustomP("CUSTOM", (Proizvod) curr);
					else
						par = new CustomP("CUSTOM", (Modul) curr);
					jbtn.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							if (num == 1)
							{
								// par = new CustomP("CUSTOM", (Proizvod) curr);
								par.setLabel(jtf.getText());
								par.setOption(chosen);
								((Proizvod) curr).addChild(par);
								par.addListener(Frame1.getInstance().getPanelRB());
								// jDialog.dispose();
								// parametar = par;
							}
							else
							{
								// par = new CustomP("CUSTOM", (Modul) curr);
								par.setLabel(jtf.getText());
								par.setOption(chosen);
								((Modul) curr).addChild(par);
								par.addListener(Frame1.getInstance().getPanelRB());
								// jDialog.dispose();
								// parametar = par;
							}
							if (chosen == 2)
							{
								JDialog jd = new JDialog();

								jd.setTitle("Labels for Radion Buttons");
								jd.setLocationRelativeTo(null);
								jd.setSize(new Dimension(320, 160));
								jd.setModalityType(ModalityType.APPLICATION_MODAL);
								jd.setLayout(new BorderLayout());
								JPanel pan = new JPanel();
								pan.add(new JLabel("RadioButton1 text: "));
								JTextField jtf = new JTextField();
								jtf.setPreferredSize(new Dimension(150, 20));
								pan.add(jtf);
								JPanel pan1 = new JPanel();
								pan1.add(new JLabel("RadioButton2 text: "));
								JTextField jtf1 = new JTextField();
								jtf1.setPreferredSize(new Dimension(150, 20));
								pan1.add(jtf1);
								JButton btnConfirm = new JButton("Confirm");
								JPanel pan2 = new JPanel();
								pan2.add(btnConfirm);
								jd.add(pan, BorderLayout.NORTH);
								jd.add(pan1, BorderLayout.CENTER);
								jd.add(pan2, BorderLayout.SOUTH);
								btnConfirm.addActionListener(new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent e)
									{
										par.setlRB1(jtf.getText());
										par.setlRB2(jtf1.getText());
										jd.dispose();

									}
								});
								jd.setVisible(true);
							}
							jd.dispose();
						}
					});
					parametar = par;
					jd.setVisible(true);
					/*
					 * if (num == 1) { CustomP par = new CustomP("CUSTOM", (Proizvod) curr);
					 * ((Proizvod) curr).addChild(par);
					 * par.addListener(Frame1.getInstanceOfFrame1().getPanelRB());
					 * jDialog.dispose(); parametar = par; } else { CustomP par = new
					 * CustomP("CUSTOM", (Modul) curr); ((Modul) curr).addChild(par);
					 * par.addListener(Frame1.getInstanceOfFrame1().getPanelRB());
					 * jDialog.dispose(); parametar = par; }
					 */
				}
				ArrayList<Node1> list = new ArrayList<>();
				list.add(parametar);
				Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
			}
		});

		/*
		 * if(num==1) { Parametar parametar=new Parametar("Parametar", (Proizvod)curr);
		 * ((Proizvod)curr).addChild(parametar);
		 * parametar.addListener(Frame1.getInstanceOfFrame1().getPanelRB()); } else {
		 * Parametar parametar=new Parametar("Parametar", (Modul)curr);
		 * ((Modul)curr).addChild(parametar);
		 * parametar.addListener(Frame1.getInstanceOfFrame1().getPanelRB()); }
		 */
		Main.saved = false;

		jDialog.setVisible(true);
	}

}