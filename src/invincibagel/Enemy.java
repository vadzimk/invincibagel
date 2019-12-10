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

    public Enemy(String SVGdata, double xLocation, double yLocation, Image... spriteCels) {
        super(SVGdata, xLocation, yLocation, spriteCels);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        isAlive = true;
        isBonus = true;
        hasValu = true;
    }

    @Override
    protected void update() {
        //throttles the pulse engine to initiate attack every attackFrequency's pulse
        if (attackCounter >= attackFrequency) {
            attackCounter = 0;
            initiateAttack();
        } else {
            ++attackCounter;
        }
    }

    private void initiateAttack() {
        if(takeSides){
            spriteFrame.setScaleX(1);
            this.setFlipH(false);
            spriteFrame.setTranslateX(500);
            spriteFrame.setTranslateY(randomNum.nextInt(attackBoundry));
            takeSides = false;
        } else {
            spriteFrame.setScaleX(-1);
            this.setFlipH(true);
            spriteFrame.setTranslateX(100);
            spriteFrame.setTranslateY(randomNum.nextInt(attackBoundry));
            takeSides = true;
        }
    }
}
