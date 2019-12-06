package invincibagel;

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

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
    int frameCounter = 0;
    int runningSpeed = 6; // holds the number of animator cycles to skip before changing the ImageView


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
//        playAudioClip();
        checkCollision();
    }


    @Override
    public boolean collide(Actor object) {
        boolean collisionDetect = false; // flag true when collision has been detected
        if (invinciBagel.iBagel.spriteFrame.getBoundsInParent().intersects(object.getSpriteFrame().getBoundsInParent()
        )) { //first level of collision detection - the spriteFrame's node's rectangular bounds
            //second level of collision detection - the SVGPath's shape's intersect: shape method
            Shape intersection = SVGPath.intersect(invinciBagel.iBagel.getSpriteBound(), object.getSpriteBound());
            if (intersection.getBoundsInLocal().getWidth() != -1) collisionDetect = true;

        }

        if(collisionDetect){
            invinciBagel.playiSound0();
            return true;
        }

        return false;
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
            frameCounter = 0; //reset the frameCounter any time arrow keys are not in use
        }

        // Run state:
        if (invinciBagel.isRight()) {
            spriteFrame.setScaleX(1); // no mirroring the imageView
            this.setFlipH(false);

            if (!animator && (!invinciBagel.isDown() && !invinciBagel.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (frameCounter >= runningSpeed) {
                    animator = true;
                    frameCounter = 0;
                } else {
                    ++frameCounter;
                }
            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (frameCounter >= runningSpeed) {
                    animator = false;
                    frameCounter = 0;
                } else {
                    ++frameCounter;
                }

            }
        }
        if (invinciBagel.isLeft()) {
            spriteFrame.setScaleX(-1); // mirror the imageView around Y axis
            this.setFlipH(true);

            if (!animator && (!invinciBagel.isDown() && !invinciBagel.isUp())) {
                spriteFrame.setImage(imageStates.get(1));
                if (frameCounter >= runningSpeed) {
                    animator = true;
                    frameCounter = 0;
                } else {
                    ++frameCounter;
                }

            } else if (animator) {
                spriteFrame.setImage(imageStates.get(2));
                if (frameCounter >= runningSpeed) {
                    animator = false;
                    frameCounter = 0;
                } else {
                    ++frameCounter;
                }
            }
        }

        // landing state
        if (invinciBagel.isDown()) {
            spriteFrame.setImage(imageStates.get(6));
        }
        //leap up state
        if (invinciBagel.isUp()) {
            spriteFrame.setImage(imageStates.get(4));
        }

        // jump over state
        if (invinciBagel.iswKey()) {
            spriteFrame.setImage(imageStates.get(5));
        }

        //evade state
        if (invinciBagel.issKey()) {
            spriteFrame.setImage(imageStates.get(8));
        }

    }


    /**
     * relocate the ImageView object of the sprite
     */
    private void moveInvinciBagel(double x, double y) {
        spriteFrame.setTranslateX(x);
        spriteFrame.setTranslateY(y);
    }

    private void playAudioClip() {
        if (invinciBagel.isLeft()) invinciBagel.playiSound0();
        if (invinciBagel.isRight()) invinciBagel.playiSound1();
        if (invinciBagel.isUp()) invinciBagel.playiSound2();
        if (invinciBagel.isDown()) invinciBagel.playiSound3();
        if (invinciBagel.iswKey()) invinciBagel.playiSound4();
        if (invinciBagel.issKey()) invinciBagel.playiSound5();

    }

    private void checkCollision() {
        for (int i = 0; i < invinciBagel.castingDirector.getCurrentCast().size(); i++) {
            Actor object = invinciBagel.castingDirector.getCurrentCast().get(i);
            collide(object);
        }
    }

}
