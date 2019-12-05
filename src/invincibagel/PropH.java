package invincibagel;

import javafx.scene.image.Image;

public class PropH extends Actor {
    public PropH(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        this.setFlipH(true); //sets the bookkeeping flag to true  - this sprite class is for plipped around the Y axis sprites
        spriteFrame.setScaleX(-1); //flips the imageView around the Y axis
        //position the imageView of the sprite in the scene
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    //changes the imageView of the sprite
    protected void update() {

    }
}
