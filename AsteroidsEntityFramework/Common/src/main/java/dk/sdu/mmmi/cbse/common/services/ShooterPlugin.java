package dk.sdu.mmmi.cbse.common.services;

public abstract class ShooterPlugin {
    private IRunTimeInstantiator runTimeInstantiator;
    protected final float maxProjectileCooldown;
    protected float projectileCooldown;
    public ShooterPlugin(IRunTimeInstantiator projectTile, float projectileCooldown){
        this.runTimeInstantiator = projectTile;
        this.maxProjectileCooldown = projectileCooldown;
    }

    public void resetCooldown(){
        this.projectileCooldown = maxProjectileCooldown;
    }

    public void decrementCooldown(float dt){
        projectileCooldown -= dt;
    }

    public boolean isOffCooldown(){
        return projectileCooldown <= 0;
    }

    public IRunTimeInstantiator getRunTimeInstantiator() {
        return runTimeInstantiator;
    }
}
