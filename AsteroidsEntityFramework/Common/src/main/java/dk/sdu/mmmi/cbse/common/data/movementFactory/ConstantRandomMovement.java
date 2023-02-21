package dk.sdu.mmmi.cbse.common.data.movementFactory;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

public class ConstantRandomMovement implements MovementFactory{
    MovingPart movingPart;
    Movement movement;
    public ConstantRandomMovement() {
        System.out.print("new movement: ");
        double random = Math.random();
        this.movement = new Movement();
        if(random<0.33){
            System.out.print("Right \n");
            movement.setRight(true);
        } else if (random<0.66) {
            System.out.print("Left \n");
            movement.setLeft(true);
        }
    }

    @Override
    public void getNewMovement(MovingPart movingPart) {
        this.movingPart = movingPart;
        this.movingPart.setUp(true);
        this.movingPart.setLeft(this.movement.left);
        this.movingPart.setRight(this.movement.right);
    }
}
