package invincibagel;

import javafx.scene.image.Image;

import static invincibagel.InvinciBagel.HEIGHT;
import static invincibagel.InvinciBagel.WIDTH;

public class Bagel extends Hero {
    protected InvinciBagel invinciBagel; //holds a reference to the current state of the InvinciBagel game. Protected access allows any subclass of Bagel to access this field
    protected static final double SPRITE_PIXELS_X = 81;
    protected static final double SPRITE_PIXELS_Y = 81;
    protected static final double rightBoundary = WIDTH / 2 - SPRITE_PIXELS_X / 2;
    protected static final double leftBoundary = -(WIDTH / 2 - SPRITE_PIXELS_X / 2);
    protected static final double bottomBoundary = HEIGHT / 2 - SPRITE_PIXELS_Y / 2;
    protected static final double topBoundary = -(HEIGHT / 2 - SPRITE_PIXELS_Y / 2);
    private boolean animator = false; // a flag used to alternate between imageView(1) - if false and imageView(2) - if true.

    public Bagel(InvinciBagel iBagel, String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        invinciBagel = iBagel;
    }

    @Override
    protected void update() {
        //will be called on the object in the handle method of the GamePlayloop of AnimationTimer Class
        setXYLocation();
        setBoundaries();
        setImageState();
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

    /**
     * Choose the image for the ImageView of the sprite according to the keybuttons pressed
     */
    private void setImageState() {
        // Wait state:
        if (
                !invinciBagel.isRight() &&
                        !invinciBagel.isLeft() &&
                        !invinciBagel.isDown() &&
                        !invinciBagel.isUp()) {
            spriteFrame.setImage(imageStates.get(0));
            animator = false;
        }

        // Run state:
        if (invinciBagel.isRight()) {

            spriteFrame.setScaleX(1); // no mirroring the imageView
            if (!animator) {
                spriteFrame.setImage(imageStates.get(1));
                animator = true;
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                animator = false;
            }
        }
        if (invinciBagel.isLeft()) {

            spriteFrame.setScaleX(-1); // mirror the imageView around Y axis
            if (!animator) {
                spriteFrame.setImage(imageStates.get(1));
                animator = true;
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                animator = false;
            }
        }

        // Jump state:
        if (invinciBagel.isDown()) {
            spriteFrame.setImage(imageStates.get(3));
        }
        if (invinciBagel.isUp()) {
            spriteFrame.setImage(imageStates.get(4));
        }

    }

    /**
     * relocate the ImageView object of the sprite
     */
    private void moveInvinciBagel(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }


}
