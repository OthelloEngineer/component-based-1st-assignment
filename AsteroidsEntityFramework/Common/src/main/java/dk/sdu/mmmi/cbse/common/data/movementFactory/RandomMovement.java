package dk.sdu.mmmi.cbse.common.data.movementFactory;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

public class RandomMovement implements MovementFactory{

    MovingPart movingPart;
    @Override
    public void getNewMovement(MovingPart movingPart) {
        this.movingPart = movingPart;
        isUp();
        isHorizontalMovement();
    }
    public void isUp(){
        if(Math.random()<0.8){
            this.movingPart.setUp(true);
        } else {
            this.movingPart.setUp(false);
        }
    }

    public void isHorizontalMovement(){
        double random = Math.random();
        if(random<0.33){
            this.movingPart.setRight(false);
            this.movingPart.setLeft(true);
        } else if (random<0.66) {
            this.movingPart.setLeft(false);
            this.movingPart.setRight(true);
        }
    }
}
