package controller;

import javax.swing.JOptionPane;

public class MessageController
{
	public static void errorMessage(String text)
	{
		JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmMessage(String text, String title)
	{
		return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	}

	public static int confrimMessageWithCancel(String text, String title)
	{
		return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	}

	public static void infoMessage(String text)
	{
		JOptionPane.showMessageDialog(null, text, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
