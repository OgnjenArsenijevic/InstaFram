package gui;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class Mail
{
	public static void sendMail(String userMail)
	{
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		final String username = "instaframapplication@gmail.com";//
		final String password = "(instafram123)";
		try
		{
			Session session = Session.getInstance(props, new Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(username, password);
				}
			});

			// -- Create a new message --
			Message msg = new MimeMessage(session);

			// -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress("instaframapplication@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userMail, false));
			msg.setSubject("Confirm e-mail address");
			msg.setText("Hello,\nthank you for using InstaFram application, please send reply to this email "
					+ "to confirm your account.");
			msg.setSentDate(new Date());
			Transport.send(msg);
			JOptionPane.showMessageDialog(null, "Verification mail has been sent to your e-mail account", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			// System.out.println("Message sent.");
		}
		catch (MessagingException e)
		{
			// System.out.println("Erreur d'envoi, cause: " + e);
		}
	}

	public static Set<String> readAndDelete() throws Exception
	{
		File file = new File("verifiedMails.txt");
		HashMap<String, Integer> hashMap = new HashMap<>();
		Scanner sc = new Scanner(file);
		while (sc.hasNextLine())
		{
			String s = sc.nextLine();
			if (!hashMap.containsKey(s))
				hashMap.put(s, 1);
		}

		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props = new Properties();
		/*
		 * try { props.load(new FileInputStream(new File("settings.properties"))); }
		 * catch (FileNotFoundException e1) { e1.printStackTrace(); } catch (IOException
		 * e1) { e1.printStackTrace(); }
		 */
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		Session session = Session.getInstance(props, null);

		Store store = session.getStore("imaps");
		store.connect("smtp.gmail.com", "instaframapplication@gmail.com", "(instafram123)");
		// System.out.println(store);

		Folder inbox = store.getFolder("inbox");
		inbox.open(Folder.READ_WRITE); // Folder.READ_ONLY
		// int messageCount = inbox.getMessageCount();
		// System.out.println("Total Messages" + messageCount);
		// int startMessage = messageCount - 5;
		// int endMessage = messageCount;

		/*
		 * if (messageCount < 5) { startMessage = 0; }
		 */

		// Message[] messages = inbox.getMessages(startMessage, endMessage);
		Message[] messages = inbox.getMessages();

		for (Message message : messages)
		{

			// boolean isMessageRead = true;

			/*
			 * for (Flags.Flag flag : message.getFlags().getSystemFlags()) { if (flag ==
			 * Flags.Flag.SEEN) { isMessageRead = true; break; } }
			 */

			//message.setFlag(Flags.Flag.SEEN, true);
			String split[] = (message.getFrom()[0]).toString().split("<");
			String split1[] = split[1].split(">");
			if(!hashMap.containsKey(split1[0]))
				hashMap.put(split1[0], 1);
			//System.out.println(split1[0]);
			// System.out.println(message.getFrom()[0]);
			// System.out.println(message.getSubject() + " "
			// + (isMessageRead ? " [READ]" : " [UNREAD]"));
			message.setFlag(Flags.Flag.DELETED, true);
		}

		inbox.close(true);
		System.out.println("Done....");
		store.close();
		try (FileWriter fw = new FileWriter(file, false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
		{
			for(String key : hashMap.keySet())
			{
				out.println(key);
			}
		}
		catch (IOException ee)
		{
			System.out.println("Greska pri radu sa fajlom");
		}
		return hashMap.keySet();
		
	}
}
