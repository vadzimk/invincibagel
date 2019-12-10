package invincibagel;

import invincibagel.Actor;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy extends Actor {
    protected static final Random randomNum = new Random(); //used for attacks
    int attackCounter = 0;
    int attackFrequency = 250;
    int attackBoundry = 300;
    boolean takeSides = false;
    boolean onScreen = false;
    boolean callAttack = false; // flag
    boolean shootBullet = false;
    boolean bulletType = false; // false  - bullet, true - cheese
    int spriteMoveR, spriteMoveL, // holds the current Enemy right and left side xLocation
            destination; //holds destination to move
    int randomLocation, //  for the enemy and projectile
            randomOffset, // vertical Y offset of projectile
            bulletRange,
            bulletOffset; // for X positioning
    int pauseCounter = 0; // for pausing enemy on the screen
    boolean launchIt = false;

    InvinciBagel invinciBagel;

    public Enemy(InvinciBagel iBagel, String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        invinciBagel = iBagel;
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isAlive = true;
        isBonus = true;
        hasValu = true;
    }

    @Override
    protected void update() {
        //throttles the pulse engine to initiate attack every attackFrequency's pulse
        if (!callAttack) {
            if (attackCounter >= attackFrequency) {
                attackCounter = 0;
                spriteMoveR = 700; // places enemy off the visible screen if on the right
                spriteMoveL = -70; // places enemy off the visible screen if on the left
                randomLocation = randomNum.nextInt(attackBoundry);
                spriteFrame.setTranslateY(randomLocation);
                randomOffset = randomLocation + 5;
                bulletType = randomNum.nextBoolean();
                takeSides = randomNum.nextBoolean();
                callAttack = true;
            } else {
                ++attackCounter;
            }
        }

        if (callAttack) {
            initiateAttack();
        }

        if (shootBullet) {
            shootProjectile();
            if(pauseCounter>=60){
                launchIt =true;
                pauseCounter =0;
            } else {
                pauseCounter++;
            }
        }
    }

    private void shootProjectile() {

        // projectile - bullet
        if (!bulletType && !takeSides) { // enemy on the left side of the screen
            invinciBagel.iBullet.spriteFrame.setTranslateY(randomOffset);
            invinciBagel.iBullet.spriteFrame.setScaleX(-0.5);
            invinciBagel.iBullet.spriteFrame.setScaleY(0.5);
            bulletRange = -50; // bullet x destination
            if (bulletOffset >= bulletRange) { //move bullet towards its x destination
                bulletOffset -= 4;
                invinciBagel.iBullet.spriteFrame.setTranslateX(bulletOffset);
            } else {
                shootBullet = false; // only one shot is fired
            }
        }
        if (!bulletType && takeSides) { // enemy on the right side of the screen
            invinciBagel.iBullet.spriteFrame.setTranslateY(randomOffset);
            invinciBagel.iBullet.spriteFrame.setScaleX(0.5);
            invinciBagel.iBullet.spriteFrame.setScaleY(0.5);
            bulletRange = 624; // bullet x destination
            if (bulletOffset < bulletRange) { // move bullet towards its x destination
                bulletOffset += 4;
                invinciBagel.iBullet.spriteFrame.setTranslateX(bulletOffset);
            } else {
                shootBullet = false;
            }
        }

        // projectile - cheese

        if (bulletType && !takeSides) { // enemy on the left side of the screen
            invinciBagel.iCheese.spriteFrame.setTranslateY(randomOffset);
            invinciBagel.iCheese.spriteFrame.setScaleX(-0.5);
            invinciBagel.iCheese.spriteFrame.setScaleY(0.5);
            bulletRange = -50; // cheese x destination
            if (bulletOffset >= bulletRange) { //move cheese towards its x destination
                bulletOffset -= 4;
                invinciBagel.iCheese.spriteFrame.setTranslateX(bulletOffset);
            } else {
                shootBullet = false; // only one shot is fired
            }
        }
        if (bulletType && takeSides) { // enemy on the right side of the screen
            invinciBagel.iCheese.spriteFrame.setTranslateY(randomOffset);
            invinciBagel.iCheese.spriteFrame.setScaleX(0.5);
            invinciBagel.iCheese.spriteFrame.setScaleY(0.5);
            bulletRange = 624; // cheese x destination
            if (bulletOffset < bulletRange) { // move cheese towards its x destination
                bulletOffset += 4;
                invinciBagel.iCheese.spriteFrame.setTranslateX(bulletOffset);
            } else {
                shootBullet = false;
            }
        }
    }

    private void initiateAttack() {
        if (!takeSides) {
            spriteFrame.setScaleX(1);
            this.setFlipH(false);
            if (!onScreen) {
                destination = 500;
                if (spriteMoveR >= destination) {
                    spriteMoveR -= 2;
                    spriteFrame.setTranslateX(spriteMoveR);
                } else {
                    // shoot projectile
                    bulletOffset = 480;
                    shootBullet = true;
                    onScreen = true;
                }
            }
            if (onScreen && launchIt) {
                destination = 700;
                if (spriteMoveR <= destination) {
                    spriteMoveR += 1;
                    spriteFrame.setTranslateX(spriteMoveR);
                } else {
                    onScreen = false;

                    callAttack = false;
                    launchIt =false;
                    loadBullet();
                    loadCheese();
                    loadEnemy();
                }
            }

            if (takeSides) {
                spriteFrame.setScaleX(-1);
                setFlipH(true);
                if (!onScreen) {
                    destination = 100;
                    if (spriteMoveL < destination) {
                        spriteMoveL += 2;
                        spriteFrame.setTranslateX(spriteMoveL);
                    } else {
                        //shoot projectile
                        bulletOffset = 120;
                        shootBullet = true;
                        onScreen = true;
                    }
                    if (onScreen && launchIt) {
                        destination = -70;
                        if (spriteMoveL >= destination) {
                            spriteMoveL -= 1;
                            spriteFrame.setTranslateX(spriteMoveL);
                        } else {
                            onScreen = false;

                            callAttack = false;
                            launchIt = false;
                            loadBullet();
                            loadCheese();
                            loadEnemy();
                        }
                    }
                }
            }
        }
    }

    private void loadBullet(){
        //if there is a bullet in the current cast - nothing to load
        for(int i=0; i<invinciBagel.castingDirector.getCurrentCast().size(); ++i){
            Actor object = invinciBagel.castingDirector.getCurrentCast().get(i);
            if(object.equals(invinciBagel.iBullet)){
                return;
            }
        }
        //load a bullet
        invinciBagel.castingDirector.addCurrentCast(invinciBagel.iBullet);
        invinciBagel.root.getChildren().add(invinciBagel.iBullet.spriteFrame);
    }

    private void loadCheese(){
        //if there is cheese in the current cast -  nothing to load
        for(int i=0; i<invinciBagel.castingDirector.getCurrentCast().size(); ++i){
            Actor object = invinciBagel.castingDirector.getCurrentCast().get(i);
            if (object.equals(invinciBagel.iCheese)){
                return;
            }
        }

        // load cheese
        invinciBagel.castingDirector.addCurrentCast(invinciBagel.iCheese);
        invinciBagel.root.getChildren().add(invinciBagel.iCheese.spriteFrame);
    }

    private void loadEnemy(){
        //if there is enemy in the current cat - nothing to load
        for (int i=0; i<invinciBagel.castingDirector.getCurrentCast().size(); ++i){
            Actor object = invinciBagel.castingDirector.getCurrentCast().get(i);
            if(object.equals(invinciBagel.iEnemy)){
                return;
            }
        }

        // load enemy
        invinciBagel.castingDirector.addCurrentCast(invinciBagel.iEnemy);
        invinciBagel.root.getChildren().add(invinciBagel.iEnemy.spriteFrame);
    }

}