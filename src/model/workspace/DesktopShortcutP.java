package model.workspace;

public class DesktopShortcutP extends Parametar
{
	boolean allowShortcut;
	
	public DesktopShortcutP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public DesktopShortcutP(DesktopShortcutP desktopSP)
	{
		this.setName(desktopSP.getName());
		this.setText(desktopSP.getText());
		this.setListeners(desktopSP.getListeners());
		this.allowShortcut=desktopSP.allowShortcut;
	}
	
	public boolean isAllowShortcut()
	{
		return allowShortcut;
	}
	
	public void setAllowShortcut(boolean allowShortcut)
	{
		this.allowShortcut = allowShortcut;
	}
}
