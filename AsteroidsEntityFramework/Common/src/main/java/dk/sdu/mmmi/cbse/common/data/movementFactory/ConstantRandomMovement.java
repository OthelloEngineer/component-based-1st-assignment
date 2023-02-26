package dk.sdu.mmmi.cbse.common.data.movementFactory;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

public class ConstantRandomMovement implements MovementFactory{
    MovingPart movingPart;
    Movement movement;
    public ConstantRandomMovement() {
        System.out.print("new movement: ");
        this.movement = new Movement();
    }

    @Override
    public void getNewMovement(MovingPart movingPart) {
        this.movingPart = movingPart;
        this.movingPart.setUp(true);
        this.movingPart.setLeft(this.movement.left);
        this.movingPart.setRight(this.movement.right);
    }
}
