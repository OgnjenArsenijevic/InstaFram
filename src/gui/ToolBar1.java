package gui;

import javax.swing.JToolBar;
import javax.swing.SwingConstants;

// ovde samo dodajemo komponente u toolbar
public class ToolBar1 extends JToolBar
{

	public ToolBar1(boolean wantSwitch) // prosledjujemo wantSwitch jer imamo 2 toolbara koji se razlikuju
	{

		super(SwingConstants.HORIZONTAL);
		
		this.add(Frame1.getInstance().getActionManager().getNewProjectAction());
		this.add(Frame1.getInstance().getActionManager().getOpenProjectAction());
		this.add(Frame1.getInstance().getActionManager().getLoadToTreeNodeAction());
		this.add(Frame1.getInstance().getActionManager().getExportProductProjectAction());
		this.add(Frame1.getInstance().getActionManager().getBuildInstalationProjectAction());
		if(!wantSwitch)
		{
			this.add(Frame1.getInstance().getActionManager().getDeleteProjectAction());
			this.add(Frame1.getInstance().getActionManager().getUndoProjectAction());
			this.add(Frame1.getInstance().getActionManager().getRedoProjectAction());
		}
		else
		{
			this.add(Frame1.getInstance().getActionManager().getRemoveTabProjectAction());
			this.add(Frame1.getInstance().getActionManager().getSwitchProjectAction());
		}
		this.add(Frame1.getInstance().getActionManager().getSaveProjectAction());
		this.add(Frame1.getInstance().getActionManager().getSaveAsProjectAction());
		this.add(Frame1.getInstance().getActionManager().getExitProjectAction());
		
		// stavljamo da toolbar moze da se pomera
		this.setFloatable(true);

	}

}
