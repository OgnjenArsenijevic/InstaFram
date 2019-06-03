package model.workspace;

public class Parametar extends Node1
{
	public Parametar()
	{
	}

	public Parametar(String name, Node1 parent)
	{
		super(name, parent);
	}

	public Parametar(Parametar parametar)
	{
		this.setName(parametar.getName());
	}

	@Override
	public boolean getAllowsChildren()
	{
		return false;
	}

	@Override
	public void addChild(Node1 node)
	{
		return;
	}
	
	public static Parametar makeConstructors(Object o)
	{
		if(o instanceof NameP)
			return new NameP((NameP)o);
		if(o instanceof AuthorP)
			return new AuthorP((AuthorP)o);
		if(o instanceof LogoP)
			return new LogoP((LogoP)o);
		if(o instanceof LicenceOfAgreementP)
			return new LicenceOfAgreementP((LicenceOfAgreementP)o);
		if(o instanceof LookAndFeelP)
			return new LookAndFeelP((LookAndFeelP)o);
		if(o instanceof DesktopShortcutP)
			return new DesktopShortcutP((DesktopShortcutP)o);
		if(o instanceof StartAfterP)
			return new StartAfterP((StartAfterP)o);
		return new CustomP((CustomP)o);
	}
}
