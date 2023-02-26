package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

public interface IRunTimeInstantiator {
    Entity createEntity(PositionPart positionPart);
}
