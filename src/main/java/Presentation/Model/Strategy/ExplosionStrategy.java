package Presentation.Model.Strategy;

import Presentation.Controller.Floor;
import Presentation.Model.Bomb;

public interface ExplosionStrategy {
     void explode(Bomb bomb, Floor floor);
}
