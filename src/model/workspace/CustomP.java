package model.workspace;

public class CustomP extends Parametar
{
	
	private String label;
	private int option;
	private String txt;
	private boolean selected;
	private String lRB1;
	private String lRB2;
	private boolean selectedRB1;
	private boolean selectedRB2;
	
	public CustomP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public CustomP(CustomP custom)
	{
		this.setName(custom.getName());
		this.setText(custom.getText());
		this.setListeners(custom.getListeners());
		this.label=custom.label;
		this.option=custom.option;
		this.txt=custom.txt;
		this.selected=custom.selected;
	}
	
	public int getOption()
	{
		return option;
	}
	
	public void setOption(int option)
	{
		this.option = option;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public String getTxt()
	{
		return txt;
	}
	
	public void setTxt(String txt)
	{
		this.txt = txt;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public String getlRB1()
	{
		return lRB1;
	}

	public void setlRB1(String lRB1)
	{
		this.lRB1 = lRB1;
	}

	public String getlRB2()
	{
		return lRB2;
	}

	public void setlRB2(String lRB2)
	{
		this.lRB2 = lRB2;
	}

	public boolean isSelectedRB1()
	{
		return selectedRB1;
	}

	public void setSelectedRB1(boolean selectedRB1)
	{
		this.selectedRB1 = selectedRB1;
	}

	public boolean isSelectedRB2()
	{
		return selectedRB2;
	}

	public void setSelectedRB2(boolean selectedRB2)
	{
		this.selectedRB2 = selectedRB2;
	}
	
	
}
