package invincibagel;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;


public class GamePlayLoop extends AnimationTimer {
    protected InvinciBagel invinciBagel;
    Pos location;

    public GamePlayLoop(InvinciBagel iBagel) {
        this.invinciBagel = iBagel;
    }

    @Override
    public void handle(long now) {
        //Everything inside the handle method will be executed 60times per second (60FPS)
        invinciBagel.iBagel.update();
        invinciBagel.iEnemy.update();
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
