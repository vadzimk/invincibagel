package invincibagel;

import javafx.scene.image.Image;

import static invincibagel.InvinciBagel.HEIGHT;
import static invincibagel.InvinciBagel.WIDTH;

public class Bagel extends Hero {
    protected InvinciBagel invinciBagel; //holds a reference to the current state of the InvinciBagel game. Protected access allows any subclass of Bagel to access this field
    protected static final double SPRITE_PIXELS_X = 81/2;
    protected static final double SPRITE_PIXELS_Y = 81;
    protected static final double rightBoundary = WIDTH/2 - SPRITE_PIXELS_X / 2;
    protected static final double leftBoundary = -(WIDTH/2 - SPRITE_PIXELS_X / 2);
    protected static final double bottomBoundary = HEIGHT/2 - SPRITE_PIXELS_Y / 2;
    protected static final double topBoundary = -(HEIGHT/2 - SPRITE_PIXELS_Y / 2);

    public Bagel(InvinciBagel iBagel, String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        invinciBagel = iBagel;
    }

    @Override
    protected void update() {
        //will be called on the object in the handle method of the GamePlayloop of AnimationTimer Class
        setXYLocation();
        setBoundaries();
        moveInvinciBagel(iX, iY);
    }


    @Override
    public boolean collide(Actor object) {
        return super.collide(object);
    }

    /**
     * Calculates the coordinates of the Bagel object
     */
    private void setXYLocation() {
        if (invinciBagel.isRight()) {
            iX += vX;
        }
        if (invinciBagel.isLeft()) {
            iX -= vX;
        }
        if (invinciBagel.isDown()) {
            iY += vY;
        }
        if (invinciBagel.isUp()) {
            iY -= vY;
        }
    }

    /**
     * Checks boundaries of the screen before passing it's calculated coordinates to the the next method object
     * If they become out of bounds it resets the object's coordinates to the value of the bounds
     */
    private void setBoundaries() {
        if (iX >= rightBoundary) {
            iX = rightBoundary;
        }
        if (iX <= leftBoundary) {
            iX = leftBoundary;
        }
        if (iY >= bottomBoundary) {
            iY = bottomBoundary;
        }
        if (iY <= topBoundary) {
            iY = topBoundary;
        }
    }

    private void moveInvinciBagel(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }


}
