package model.workspace;

import javax.swing.ImageIcon;

public class LogoP extends Parametar
{
	private String imageFile;
	
	public LogoP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public LogoP(String imageFile)
	{
		this.imageFile=imageFile;
	}
	
	public LogoP(LogoP logo)
	{
		this.setName(logo.getName());
		this.setText(logo.getText());
		this.setListeners(logo.getListeners());
		this.imageFile=logo.getImageFile();
	}

	public String getImageFile()
	{
		return imageFile;
	}

	public void setImageFile(String imageFile)
	{
		this.imageFile = imageFile;
	}
	
	
}
