package Entities;

public class ForgeExecutioner extends Enemies {
    private static final int MIN_RAGE_FOR_CRUSHING_BLOW = 200;
    private int ragePoints;

    public ForgeExecutioner(String name) {
        super(name, 3000, 2000, 1000, 20);
        setRagePoints(0);
    }

    public int getRagePoints() {
        return ragePoints;
    }

    public void setRagePoints(int ragePoints) {
        if (ragePoints < 0) {
            throw new IllegalArgumentException("Rage points do not have negatives values.");
        }
        this.ragePoints = ragePoints;
    }

    public int incrementRagePoints(int damageTaken) {
        setRagePoints(damageTaken + ragePoints);
        return ragePoints;
    }

    public int crushingBlow() {
        if (ragePoints < MIN_RAGE_FOR_CRUSHING_BLOW) {
            throw new IllegalStateException(
                "Minimum rage required is " + MIN_RAGE_FOR_CRUSHING_BLOW + ", but current rage is " + ragePoints
            );
        }

        int totalDamage = ragePoints - (int) (ragePoints * 0.40) + getAttack();
        setRagePoints(0);
        return totalDamage;
    }

    public boolean canUseCrushingBlow() {
        return ragePoints >= MIN_RAGE_FOR_CRUSHING_BLOW;
    }

    @Override
    public String toString() {
        return super.toString() + "rage point value:" + ragePoints;
    }
}