package actions;

import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public abstract class AbstractActionI extends AbstractAction
{
	public Icon loadIcon(String fileName)
	{
		ImageIcon imageIcon = null;
		File file = new File(fileName);
		if (file.exists())
			imageIcon = new ImageIcon(fileName);
		else
			System.err.println("Resource not found: " + fileName);
		return imageIcon;
	}

}