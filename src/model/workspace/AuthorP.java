package model.workspace;

public class AuthorP extends Parametar
{

	private String authorName;
	private String authorSurname;

	public AuthorP(String name, Node1 parent)
	{
		super(name, parent);
	}

	public AuthorP(String authorName, String authorSurname)
	{
		super();
		this.authorName = authorName;
		this.authorSurname = authorSurname;
	}

	public AuthorP(AuthorP author)
	{
		super();
		this.setName(author.getName());
		this.setText(author.getText());
		this.setListeners(author.getListeners());
		this.authorName = author.authorName;
		this.authorSurname = author.authorSurname;
	}

	public String getAuthorName()
	{
		return authorName;
	}

	public String getAuthorSurname()
	{
		return authorSurname;
	}

	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	public void setAuthorSurname(String authorSurname)
	{
		this.authorSurname = authorSurname;
	}

}
