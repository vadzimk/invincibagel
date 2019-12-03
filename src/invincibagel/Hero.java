package invincibagel;

import javafx.scene.image.Image;

public abstract class Hero extends Actor {

    //hold data about motion of sprites:
    protected double
            vX,
            vY,
            lifeSpan,
            damage,
            offsetX,
            offsetY,
            boundScale,
            boundRot,
            friction,
            gravity,
            bounce;

    public Hero(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        lifeSpan = 1000;
        vX = vY = damage = offsetX = offsetY = 0;
        boundScale = boundRot = friction = gravity = bounce = 0;
    }

    @Override
    protected abstract void update();

    public boolean collide(Actor object) {
        return false;
    }

    // ----------- Getter ----------------
    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }

    public double getLifeSpan() {
        return lifeSpan;
    }

    public double getDamage() {
        return damage;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getBoundScale() {
        return boundScale;
    }

    public double getBoundRot() {
        return boundRot;
    }

    public double getFriction() {
        return friction;
    }

    public double getGravity() {
        return gravity;
    }

    public double getBounce() {
        return bounce;
    }

    // -------------- Setter --------------


    public void setvX(double vX) {
        this.vX = vX;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public void setLifeSpan(double lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setBoundScale(double boundScale) {
        this.boundScale = boundScale;
    }

    public void setBoundRot(double boundRot) {
        this.boundRot = boundRot;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setBounce(double bounce) {
        this.bounce = bounce;
    }
}
