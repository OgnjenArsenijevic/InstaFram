package model.workspace;

import java.io.File;

public class LicenceOfAgreementP extends Parametar
{
	private String filePath;
	
	public LicenceOfAgreementP(String name, Node1 parent)
	{
		super(name, parent);
	}
	
	public LicenceOfAgreementP(String filePath)
	{
		this.filePath=filePath;
	}
	
	public LicenceOfAgreementP(LicenceOfAgreementP licence)
	{
		this.setName(licence.getName());
		this.setText(licence.getText());
		this.setListeners(licence.getListeners());
		this.filePath=licence.getFilePath();
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}
