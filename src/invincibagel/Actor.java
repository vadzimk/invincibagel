package invincibagel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Actor {
    protected SVGPath spriteBound;
    protected ImageView spriteFrame;
    protected List<Image> imageStates = new ArrayList<>();
    protected double iX, iY;  // initial location
    protected double pX, pY; // pivot point location

    protected boolean
            isAlive,
            isFixed,
            isBonus,
            hasValu,
            isFlipV,
            isFlipH;



    public Actor(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        spriteBound = new SVGPath();
        spriteBound.setContent(SVGdata);
        spriteFrame = new ImageView(spriteCels[0]);
        imageStates.addAll(Arrays.asList(spriteCels));
        iX = xLocation;
        iY = yLocation;

        pX=0;
        pY=0;
        isAlive = false;
        isFixed = true;
        isBonus = false;
        hasValu = false;
        isFlipH = false;
        isFlipV = false;
    }

    public Actor(String SVGdata, double xLocation, double yLocation, double xPivot, double yPivot, Image... spriteCels) {
        this(SVGdata, xLocation, yLocation, spriteCels);
        pX = xPivot;
        pY = yPivot;
    }

    /**
     * tells the Actor what to do on every pulse of the gamePlayLoop
     */
    protected abstract void update();

    //------------ Getter -----------------

    public SVGPath getSpriteBound() {
        return spriteBound;
    }

    public ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public List<Image> getImageStates() {
        return imageStates;
    }

    public double getiX() {
        return iX;
    }

    public double getiY() {
        return iY;
    }

    public double getpX() {
        return pX;
    }

    public double getpY() {
        return pY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public boolean isBonus() {
        return isBonus;
    }

    public boolean hasValu() {
        return hasValu;
    }

    public boolean isFlipV() {
        return isFlipV;
    }

    public boolean isFlipH() {
        return isFlipH;
    }

    // ----------- Setter ----------

    public void setSpriteBound(SVGPath spriteBound) {
        this.spriteBound = spriteBound;
    }

    public void setSpriteFrame(ImageView spriteFrame) {
        this.spriteFrame = spriteFrame;
    }

    public void setImageStates(List<Image> imageStates) {
        this.imageStates = imageStates;
    }

    public void setiX(double iX) {
        this.iX = iX;
    }

    public void setiY(double iY) {
        this.iY = iY;
    }

    public void setpX(double pX) {
        this.pX = pX;
    }

    public void setpY(double pY) {
        this.pY = pY;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public void setBonus(boolean bonus) {
        isBonus = bonus;
    }

    public void setHasValu(boolean hasValu) {
        this.hasValu = hasValu;
    }

    public void setFlipV(boolean flipV) {
        isFlipV = flipV;
    }

    public void setFlipH(boolean flipH) {
        isFlipH = flipH;
    }
}
