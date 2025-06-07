package Presentation.Model.Observer;

import Presentation.Model.Player;

public interface Observable {
    void addObserver(Observador o);
    void removeObserver(Observador o);
    void notifyObservers(String s, Player player);
}
