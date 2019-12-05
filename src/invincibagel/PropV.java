package invincibagel;

import javafx.scene.image.Image;

public class PropV extends Actor {
    public PropV(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        this.setFlipV(true);
        spriteFrame.setScaleY(-1);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
    }

    @Override
    protected void update() {

    }
}
