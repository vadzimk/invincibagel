package invincibagel;

import javafx.scene.image.Image;

public class Prop extends Actor {


    public Prop(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        //position the fixed sprite object - prop - in the scene
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    // this method will change the imageView of the sprite object
    protected void update() {


    }
}
