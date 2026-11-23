package Entities;

public abstract class Personagem {
    private String name;
    private int lifePoints;
    private int attack;
    private int defense;

    public Personagem(String name, int lifePoints, int attack, int defense) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.attack = attack;
        this.defense = defense;
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

    // Método toString que exibe os dados padrão de todos os personagens.
    @Override
    public String toString() {
        return "Name:" + name + ", Life points:" + lifePoints + ", Attack:" + attack + ", Defense:" + defense + " ";
    }
}