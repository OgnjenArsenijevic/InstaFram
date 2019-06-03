package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import app.Main;
import controller.MessageController;
import gui.Frame1;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.Modul;
import model.workspace.Parametar;
import model.workspace.Proizvod;

public class ExportProductProjectAction extends AbstractActionI
{
	public ExportProductProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/export.png"));
		putValue(NAME, "mExportForInstallation");
		putValue(SHORT_DESCRIPTION, "mExportForInstalation");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
		if (curr == null || curr instanceof Proizvod == false)
			return;
		String s = "src/Proizvodi za Instalaciju/" + ((Proizvod) curr).getName();
		File file = new File(s);
		file.mkdirs();
		s += "/resursi";
		File resursi = new File(s);
		resursi.mkdirs();
		String destination = s + "/";
		if (((Proizvod) curr).getFilePath() == null)
		{
			MessageController.errorMessage(
					"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
			return;
		}
		File source = new File(((Proizvod) curr).getFilePath());
		s += "/" + source.getName();
		File dest = new File(s);
		try
		{
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		s = "resursi/" + source.getName();
		((Proizvod) curr).setFilePath(s);
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
		// System.out.println(list);
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) instanceof LogoP)
			{
				try
				{
					String path = ((LogoP) list.get(i)).getImageFile();
					source = new File(path);
					s = "resursi/" + source.getName();
					((LogoP) list.get(i)).setImageFile(s);
					String ss = destination + source.getName();
					dest = new File(ss);
					// System.out.println( ((LogoP) list.get(i)).getImageFile());
					try
					{
						Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
					catch (IOException e)
					{
						MessageController.errorMessage(
								"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
						return;
					}
				}
				catch (Exception e)
				{
					MessageController.errorMessage(
							"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
					return;
				}
			}
			if (list.get(i) instanceof LicenceOfAgreementP)
			{
				try
				{
					String path = ((LicenceOfAgreementP) list.get(i)).getFilePath();
					source = new File(path);
					s = "resursi/" + source.getName();
					((LicenceOfAgreementP) list.get(i)).setFilePath(s);
					String ss = destination + source.getName();
					// System.out.println(ss);
					dest = new File(ss);
					// System.out.println(((LicenceOfAgreementP) list.get(i)).getFilePath());
					try
					{
						Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
					catch (IOException e)
					{
						MessageController.errorMessage(
								"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
						return;
					}
				}
				catch (Exception e)
				{
					MessageController.errorMessage(
							"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
					return;
				}
			}
		}
		File fileProduct = new File(file.getAbsolutePath() + "/app");
		if (fileProduct.exists())
		{
			fileProduct.delete();
		}
		FileOutputStream fileOutputStream;
		try
		{
			fileOutputStream = new FileOutputStream(fileProduct);
			ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
			out.writeObject(curr);
			out.close();
			fileOutputStream.close();
		}
		catch (FileNotFoundException e)
		{
			MessageController.errorMessage(
					"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
			return;
		}
		catch (IOException ror)
		{
			MessageController.errorMessage(
					"Error ocurred, some Logo parameters or Licence of Agreement Parameters or Product path contains wrong path ");
			return;
		}
		MessageController.infoMessage("Product successfully exported");
	}

}
