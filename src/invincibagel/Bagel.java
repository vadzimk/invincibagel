package invincibagel;

import javafx.scene.image.Image;

public class Bagel extends Hero {

    public Bagel(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
    }

    @Override
    protected void update() {

    }

    @Override
    public boolean collide(Actor object) {
        return super.collide(object);
    }
}
