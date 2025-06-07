package Presentation.Model.Observer;

public interface Observable {
    void addObserver(Observador o);
    void removeObserver(Observador o);
    void notifyObservers(String s);
}
