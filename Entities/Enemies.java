package Entities;

public class Enemies {
    private String name;
    private int lifePoints;
    private int attack;
    private int defense;
    private int magicDefense;

    public Enemies(String name, int lifePoints, int attack, int defense, int magicDefense) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.defense = defense;
        this.magicDefense = magicDefense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getMagicDefense() {
        return magicDefense;
    }

    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }

    @Override
    public String toString() {
        return "name:" + name + ", points of life:" + lifePoints + ", attack:" + attack + ", defense:" + defense + ", magic defense:" + magicDefense;
    }

    public boolean hasAliveEnemies() {
        if (lifePoints <= 0) {
            System.out.println(name + " don't has enough life.");
            return false;
        }

        boolean hasEnoughLife = lifePoints > 0;
        return hasEnoughLife;
    }
}
