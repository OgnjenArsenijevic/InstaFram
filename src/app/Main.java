package app;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.SignIn;
import view.InstallationView;

public class Main
{
	public static File directory;
	public static File currFile;
	public static boolean saved;
	
	public static void main(String[] args)
	{
		// stavljamo look and feel ako moze
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.out.println(UIManager.getSystemLookAndFeelClassName());
			UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		    for (UIManager.LookAndFeelInfo look : looks) {
		      System.out.println(look.getName());
		    }
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SignIn signIn=new SignIn();
		
		// uzimamo userov default directory
		Main.directory = new File(System.getProperty("user.home"));
		Main.currFile = null;
		Main.saved = true;
	}

}
