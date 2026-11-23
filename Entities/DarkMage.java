package Entities;

public class DarkMage extends Enemies {
    private static final int MANA_COST = 200;
    private int manaPoints;

    public DarkMage(String name) {
        super(name, 1500, 1800, 300, 1500);
        setManaPoints(1000);
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        if (manaPoints < 0) {
            throw new IllegalArgumentException("Mana cannot be negative.");
        }
        this.manaPoints = manaPoints;
    }

    public int arcaneBlast() {
        if (manaPoints < MANA_COST) {
            throw new IllegalStateException(
                "Cost of the mana is " + MANA_COST + " and the quantity of the mana is " + manaPoints + " that's it caused an error"
            );
        }

        int totalDamage = getAttack() + (int) (MANA_COST * 1.5);
        setManaPoints(manaPoints - MANA_COST);

        return totalDamage;
    }

    public boolean canUseArcaneBlast() {
        return manaPoints >= MANA_COST;
    }

    public int regenerateMana() {
        int amount = 100;
        setManaPoints(manaPoints + amount);
        return manaPoints;
    }

    @Override
    public String toString() {
        return super.toString() + "Mana points: " + manaPoints;
    }
}
