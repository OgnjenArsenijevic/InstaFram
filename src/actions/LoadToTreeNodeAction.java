package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import app.Main;
import commands.AddCommand;
import controller.MessageController;
import gui.Frame1;
import model.workspace.Kompanija;
import model.workspace.Node1;
import model.workspace.Parametar;
import model.workspace.Proizvod;

//ova klasa sluzi za ucitavanje stabla iz fajla u selektovani cvor
public class LoadToTreeNodeAction extends AbstractActionI
{

	public LoadToTreeNodeAction()
	{
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("src/images/import.png"));
		putValue(NAME, "mImportProduct");
		putValue(SHORT_DESCRIPTION, "mImportProduct");
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
						/*if (Main.saved == false)
						{
							chosen = JOptionPane.showConfirmDialog(null,
									"You have unsaved changes, do you want to save your current work?", "",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (chosen == 0)
								Frame1.getInstanceOfFrame1().getActionManager().getSaveProjectAction().actionPerformed(null);
							if (chosen == -1 || chosen == 2)
							{
								throw new NullPointerException();
							}
						}*/
						Main.directory = fileChooser.getCurrentDirectory();
						Main.currFile = fileChooser.getSelectedFile();
						FileInputStream fileInputStream = null;
						ObjectInputStream in=null;
						Main.saved = false;
						try
						{
							fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
							in=new ObjectInputStream(fileInputStream);
							
							Object curr=in.readObject();
							
							if(!(curr instanceof Proizvod)){
								in.close();
								fileInputStream.close();
								return;
							}
							ArrayList<Node1> list=new ArrayList<>();
							if(curr instanceof Proizvod)
							{
								((Node1)curr).setIndexInChildrenArray(-1);
								((Kompanija)Frame1.getInstance().getNodeModel().getRoot()).addChild((Node1)curr);
								((Node1)curr).setParent((Kompanija)Frame1.getInstance().getNodeModel().getRoot());
								list.add((Node1)curr);
								Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
							}
							
							while(true)
							{
								curr=in.readObject();
								((Node1)curr).setIndexInChildrenArray(-1);
								((Kompanija)Frame1.getInstance().getNodeModel().getRoot()).addChild((Node1)curr);
								((Node1)curr).setParent((Kompanija)Frame1.getInstance().getNodeModel().getRoot());
								list.add((Node1)curr);
								Frame1.getInstance().getCommandManager().addCommand(new AddCommand(list));
							}
						}
						catch(EOFException e)
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
							ArrayDeque<Node1> deque = new ArrayDeque<>();
							deque.push((Node1)Frame1.getInstance().getNodeModel().getRoot());
							while(deque.isEmpty()==false)
							{
								Node1 curr=deque.pop();
								if(curr.getListenersCount()==0)
									curr.addListener(Frame1.getInstance().getPanelRB());
								for(int i=0;i<curr.getChildCount();i++)
									deque.push(curr.getChildren().get(i));
							}
							Frame1.getInstance().getPanelRT().getTabbedPane().removeAll();
							Frame1.getInstance().getPanelRB().getTextArea().setText("");
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
						catch(ClassCastException e)
						{
							
						}
						catch (IOException e)
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
}
