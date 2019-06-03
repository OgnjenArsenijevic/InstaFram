package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.DatatypeConverter;

import com.sun.javafx.scene.control.SelectedCellsMap;

import controller.MessageController;

public class SignIn extends JFrame
{

	private JTextField username;
	private JPasswordField password;
	private JButton btnSignIn;
	private JButton btnSignUp;
	private SignIn self;

	public SignIn()
	{
		// setLayout(new BorderLayout());
		setLayout(new GridLayout(3, 1));
		setSize(350, 150);
		setTitle("Sign in");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		this.getInsets().set(10, 5, 5, 5);

		JLabel lblUsername = new JLabel("Username:");
		JLabel lblPassword = new JLabel("Password:");
		username = new JTextField();
		password = new JPasswordField();
		username.setPreferredSize(new Dimension(150, 20));
		password.setPreferredSize(new Dimension(150, 20));
		btnSignIn = new JButton("Sign In");
		btnSignUp = new JButton("Sign up");
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout());
		panelTop.add(lblUsername);
		panelTop.add(username);
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new FlowLayout());
		panelCenter.add(lblPassword);
		panelCenter.add(password);
		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new FlowLayout());
		panelBottom.add(btnSignIn);
		panelBottom.add(btnSignUp);

		add(panelTop);
		add(panelCenter);
		add(panelBottom);

		addActions();

		setVisible(true);
	}

	void addActions()
	{
		self = this;
		btnSignIn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if ((username == null || username.getText().equals("")) && password.getPassword().length == 0)
					{
						MessageController.errorMessage("You must enter username and password");
						return;
					}
					if ((username == null || username.getText().equals("")))
					{
						MessageController.errorMessage("You must enter username");
						password.setText("");
						return;
					}
					if (password.getPassword().length == 0)
					{
						MessageController.errorMessage("You must enter password");
						username.setText("");
						return;
					}
					if (username.getText().contains(" "))
					{
						MessageController.errorMessage("Username cannot contain spaces");
						username.setText("");
						password.setText("");
					}
					File file = new File("data.txt");
					Scanner sc = new Scanner(file);
					boolean check = false;
					while (sc.hasNextLine())
					{
						String s = sc.nextLine();
						String split[] = s.split(" ");
						// System.out.println(split[0]);
						// System.out.println(split[1]);
						// System.out.println(split[2]);
						// System.out.println(hashPassword(String.valueOf(password.getPassword())));
						if (split[1].equals(username.getText())
								&& split[2].equals(hashPassword(String.valueOf(password.getPassword()))))
						{
							self.dispose();
							// nudimo korisniku opciju da ucita stablo iz fajla pri pokretanju programa
							int chosen = MessageController.confirmMessage("Do you want to load workspace?", "Load?");
							Frame1 frame1 = Frame1.getInstance();
							frame1.setUser(split[3]);
							frame1.doWork();
							if (chosen == 0) // ako korisnik hoce da ucita iz fajla onda...
							{
								// pozivamo akciju iz klase OpenProjecAction
								Frame1.getInstance().getActionManager().getOpenProjectAction()
										.actionPerformed(null);
							}
							if (chosen == -1)
								return;
							frame1.setVisible(true);
							frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							frame1.addWindowListener(new java.awt.event.WindowAdapter()
							{
								@Override
								public void windowClosing(java.awt.event.WindowEvent windowEvent)
								{
									Frame1.getInstance().getActionManager().getExitProjectAction()
											.actionPerformed(null);
								}
							});
							check = true;
							break;
						}
					}
					if (!check)
						MessageController.errorMessage("Username or password is wrong");
					else
					{/*
						Frame1.getInstanceOfFrame1().getPanelS().getTfKorisnik()
								.setText("Korisnik: < " + username.getText() + " >");
						boolean verified = false;
						Set<String> set;
						try
						{
							set= Mail.readAndDelete();
							File f = new File("data.txt");
							Scanner scan = new Scanner(f);
							while (scan.hasNextLine())
							{
								String ss = scan.nextLine();
								String split[] = ss.split(" ");
								if (split[1].equals(username.getText()))
								{
									if(set.contains(split[0]))
									{
										verified = true;
										break;
									}
								}
							}
						}
						catch (Exception e2)
						{
							//e2.printStackTrace();
						}
						if (verified)
							Frame1.getInstanceOfFrame1().getPanelS().getTfStatus().setText("Status: < Verified >");
						else
							Frame1.getInstanceOfFrame1().getPanelS().getTfStatus().setText("Status: < Unverified >");
					*/}
				}
				catch (Exception e2)
				{
					// TODO: handle exception
				}

			}
		});

		btnSignUp.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFrame frame = new JFrame("Sign Up");
				frame.setLayout(new BorderLayout());
				frame.setSize(400, 260);
				frame.setLocationRelativeTo(self);
				frame.setResizable(false);
				JLabel lblMail = new JLabel("Mail:");
				JLabel lblUsername = new JLabel("Username:");
				JLabel lblPassword = new JLabel("Password:");
				JLabel lblPasswordConfirm = new JLabel("Confirm Password:");
				JLabel lblRole = new JLabel("Role:");
				JTextField mailC = new JTextField();
				JTextField usernameC = new JTextField();
				JPasswordField passwordC = new JPasswordField();
				JPasswordField passwordConfirm = new JPasswordField();
				String positionType[] = { "Admin", "User" };
				JComboBox<String> comboBox = new JComboBox<>(positionType);
				comboBox.setEditable(false);
				JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
				panel.setBorder(new EmptyBorder(10, 30, 10, 30));
				panel.add(lblMail);
				panel.add(mailC);
				panel.add(lblUsername);
				panel.add(usernameC);
				panel.add(lblPassword);
				panel.add(passwordC);
				panel.add(lblPasswordConfirm);
				panel.add(passwordConfirm);
				panel.add(lblRole);
				panel.add(comboBox);
				frame.add(panel, BorderLayout.NORTH);

				JButton btnCreateAcount = new JButton("Sign Up");
				JPanel panel1 = new JPanel(new FlowLayout());
				panel1.add(btnCreateAcount);
				btnSignUp.setHorizontalAlignment(SwingConstants.CENTER);
				frame.add(panel1, BorderLayout.CENTER);

				frame.setVisible(true);

				btnCreateAcount.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							if ((mailC == null || mailC.getText().equals(""))
									|| (usernameC == null || usernameC.getText().equals(""))
									|| passwordC.getPassword().length == 0 || passwordConfirm.getPassword().length == 0)
							{
								MessageController.errorMessage("You have empty fields");
								return;
							}
							String hashedPassword = hashPassword(String.valueOf(passwordC.getPassword()));
							String hashedPasswordConfirm = hashPassword(String.valueOf(passwordConfirm.getPassword()));
							if (!hashedPassword.equals(hashedPasswordConfirm))
							{
								MessageController.errorMessage("Passwords do not match");
								passwordC.setText("");
								passwordConfirm.setText("");
								return;
							}
							if (!mailC.getText().contains("@") || mailC.getText().contains(" "))
							{
								MessageController.errorMessage("Please, enter valid mail adress");
								mailC.setText("");
								return;
							}
							if (usernameC.getText().contains(" "))
							{
								MessageController.errorMessage("Username cannot contain space characters");
								usernameC.setText("");
								return;
							}
							if (String.valueOf(passwordC.getPassword()).contains(" "))
							{
								MessageController.errorMessage("Password cannot contain space characters");
								passwordC.setText("");
								passwordConfirm.setText("");
								return;
							}

							File file = new File("data.txt");
							Scanner sc = new Scanner(file);
							while (sc.hasNextLine())
							{
								String s = sc.nextLine();
								String split[] = s.split(" ");
								if (split[0].equals(mailC.getText()) && split[1].equals(usernameC.getText()))
								{
									MessageController.errorMessage("User with this mail and username already exists");
									return;
								}
								if (split[0].equals(mailC.getText()))
								{
									MessageController.errorMessage("User with this mail already exists");
									return;
								}
								if (split[1].equals(usernameC.getText()))
								{
									MessageController.errorMessage("This username is already taken, please choose different username");
									return;
								}
							}
							try (FileWriter fw = new FileWriter(file, true);
									BufferedWriter bw = new BufferedWriter(fw);
									PrintWriter out = new PrintWriter(bw))
							{
								// dodajemo podatke o kreiranom korisniku u fajl
								out.print(mailC.getText() + " " + usernameC.getText() + " " + hashedPassword + " "
										+ comboBox.getSelectedItem() + "\n");
								Mail.sendMail(mailC.getText());
								JOptionPane.showMessageDialog(null, "Account successfully created");
								frame.dispose();
							}
							catch (IOException ee)
							{
								System.out.println("Greska pri radu sa fajlom");
							}
						}
						catch (Exception e2)
						{
							// TODO: handle exception
						}

					}
				});
			}
		});
	}

	private String hashPassword(String password)
	{
		MessageDigest messageDigest;
		try
		{
			messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(password.getBytes());
			return DatatypeConverter.printHexBinary(messageDigest.digest());
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return password;
	}
}
