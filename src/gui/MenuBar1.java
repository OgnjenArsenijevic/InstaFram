package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

//ovde samo dodajemo komponente u menubar
public class MenuBar1 extends JMenuBar
{
	public MenuBar1()
	{
		JMenu jmFile = new JMenu("File");
		JMenu jmEdit = new JMenu("Edit");
		JMenu jmTools = new JMenu("Tools");
		JMenu jmParametrizacija = new JMenu("Parametrizacija");
		JMenu jmPomoc = new JMenu("Pomoc");
		JMenu jmAbout = new JMenu("About");

		addMenuItems(jmFile);
		
		jmEdit.add(Frame1.getInstance().getActionManager().getCutProjectAction());
		jmEdit.add(Frame1.getInstance().getActionManager().getCopyProjectAction());
		jmEdit.add(Frame1.getInstance().getActionManager().getPasteProjectAction());
		
		addMenuItems(jmTools);
		addMenuItems(jmParametrizacija);
		addMenuItems(jmPomoc);
		jmAbout.add(Frame1.getInstance().getActionManager().getAuthorProjectAction());

		this.add(jmFile);
		this.add(jmEdit);
		this.add(jmTools);
		add(Box.createGlue());
		this.add(jmParametrizacija);
		this.add(jmPomoc);
		this.add(jmAbout);
	}

	private void addMenuItems(JMenu jmn) // posto se ponavlja, imamo funkciju da ne bi kopirali kod 5 puta
	{
		JMenu jmExport = new JMenu("mExport");
		JMenu jmImport = new JMenu("mImport");

		jmImport.add(Frame1.getInstance().getActionManager().getImportPDFProjectAction());
		jmImport.add(Frame1.getInstance().getActionManager().getImportExcelProjectAction());
		jmImport.add(Frame1.getInstance().getActionManager().getImportWordProjectAction());
		jmExport.add(Frame1.getInstance().getActionManager().getExportPDFProjectAction());
		jmExport.add(Frame1.getInstance().getActionManager().getExportExcelProjectAction());
		jmExport.add(Frame1.getInstance().getActionManager().getExportWordProjectAction());

		jmn.add(Frame1.getInstance().getActionManager().getNewProjectAction());
		jmn.addSeparator();
		jmn.add(Frame1.getInstance().getActionManager().getOpenProjectAction());
		jmn.add(Frame1.getInstance().getActionManager().getLoadToTreeNodeAction());
		jmn.add(Frame1.getInstance().getActionManager().getExportProductProjectAction());
		jmn.add(Frame1.getInstance().getActionManager().getBuildInstalationProjectAction());
		jmn.add(Frame1.getInstance().getActionManager().getDeleteProjectAction());
		jmn.add(Frame1.getInstance().getActionManager().getSwitchProjectAction());
		jmn.addSeparator();
		jmn.add(Frame1.getInstance().getActionManager().getSaveProjectAction());
		jmn.add(Frame1.getInstance().getActionManager().getSaveAsProjectAction());
		jmn.addSeparator();
		jmn.add(jmExport);
		jmn.add(jmImport);
		jmn.addSeparator();
		jmn.add(Frame1.getInstance().getActionManager().getExitProjectAction());
		
		if(Frame1.getInstance().getUser().equals("User"))
		{
			Frame1.getInstance().getActionManager().getNewProjectAction().setEnabled(false);
			Frame1.getInstance().getActionManager().getLoadToTreeNodeAction().setEnabled(false);
			Frame1.getInstance().getActionManager().getDeleteProjectAction().setEnabled(false);
		}
	}

}