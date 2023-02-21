package dk.sdu.mmmi.cbse.common.data.movementFactory;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

public interface MovementFactory {
    public void getNewMovement(MovingPart movingPart);
}
