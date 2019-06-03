package actions;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.KeyStroke;

import commands.AddCommand;
import commands.DeleteCommand;
import gui.Frame1;
import model.workspace.AuthorP;
import model.workspace.CustomP;
import model.workspace.DesktopShortcutP;
import model.workspace.LicenceOfAgreementP;
import model.workspace.LogoP;
import model.workspace.LookAndFeelP;
import model.workspace.Modul;
import model.workspace.NameP;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.ParametarElementsSelection;
import model.workspace.Proizvod;
import model.workspace.StartAfterP;

public class PasteProjectAction extends AbstractActionI
{
	public PasteProjectAction()
	{

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/paste.png"));
		putValue(NAME, "mPaste");
		putValue(SHORT_DESCRIPTION, "mPaste");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Object curr = Frame1.getInstance().getNodeTree().getLastSelectedPathComponent();
		if (curr instanceof Proizvod || curr instanceof Modul)
		{
			Transferable clipboardContent = Frame1.getInstance().getClipboard()
					.getContents(Frame1.getInstance());
			if ((clipboardContent != null)
					&& (clipboardContent.isDataFlavorSupported(ParametarElementsSelection.elementFlavor)))
			{
				try
				{
					ArrayList<Parametar> tempElements = (ArrayList<Parametar>) clipboardContent
							.getTransferData(ParametarElementsSelection.elementFlavor);
					ArrayList<Node1> list = new ArrayList<>();
					for (int i = 0; i < tempElements.size(); i++)
					{
						Parametar par = Parametar.makeConstructors(tempElements.get(i));
						// par.setIndexInChildrenArray(-1);
						if (curr instanceof Proizvod)
						{
							par.setParent((Proizvod) curr);
							((Proizvod) curr).addChild(par);
						}
						else
						{
							par.setParent((Modul) curr);
							((Modul) curr).addChild(par);
						}
						list.add(par);
					}
					Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));

				}
				catch (UnsupportedFlavorException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
