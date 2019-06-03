package model.workspace;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

public class ParametarElementsSelection implements Transferable,ClipboardOwner
{

	public static DataFlavor elementFlavor;
	private DataFlavor[] supportedFlavors= {elementFlavor};
	private ArrayList<Parametar> selectedElements=new ArrayList<>();
	
	public ParametarElementsSelection(ArrayList<Parametar> elements)
	{
		selectedElements=elements;
		try
		{
			elementFlavor=new DataFlavor(Class.forName ("java.util.ArrayList"), "Elements");
		}
		catch (Exception e)
		{
			
		}
	}
	
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents)
	{
		
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
	{
		if(flavor.equals(elementFlavor))
			return (selectedElements);
		else
			throw new UnsupportedFlavorException(elementFlavor);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return (supportedFlavors);
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return (flavor.equals(elementFlavor));
	}

	public ArrayList<Parametar> getSelectedElements()
	{
		return selectedElements;
	}
	
}
