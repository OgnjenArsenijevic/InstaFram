package events;

//interfejs za obzerver
public interface Observable
{
	void addListener(Observer listener);
	void removeListener(Observer listener);
	void notify(Object o); 
}
