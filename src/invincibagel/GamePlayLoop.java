package invincibagel;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import  static invincibagel.InvinciBagel.iBagel;
public class GamePlayLoop extends AnimationTimer {
    Pos location;

    @Override
    public void handle(long now) {
        //Everything inside the handle method will be executed 60times per second (60FPS)
        iBagel.update();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
