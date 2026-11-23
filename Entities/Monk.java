package Entities;

public class Monk extends Personagem {
    public enum Level {
        NIVEL_1,
        NIVEL_2,
        NIVEL_3
    }

    private Level prayer;
    private Level revenge;

    // O monge não possui dano de ataque base.
    public Monk(String name, int defense, Level prayer, Level revenge, int lifePoints) {
        super(name, lifePoints, 0, defense);
        this.prayer = prayer;
        this.revenge = revenge;
    }

    public Level getPrayer() {
        return prayer;
    }

    public void setPrayer(Level prayer) {
        this.prayer = prayer;
    }

    public Level getRevenge() {
        return revenge;
    }

    public void setRevenge(Level revenge) {
        this.revenge = revenge;
    }

    // Pega 20% do dano recebido pelo monge e retorna como dano ao inimigo, conforme o nível de Revenge.
    public int avenged(int damageTaken) {
        switch (revenge) {
            case NIVEL_1:
                return ((int) (damageTaken * 0.20)) * 1;
            case NIVEL_2:
                return ((int) (damageTaken * 0.20)) * 2;
            case NIVEL_3:
                return ((int) (damageTaken * 0.20)) * 3;
            default:
                return 0;
        }
    }

    // Pega 20% do dano recebido pelo aliado e retorna como cura, conforme o nível de Prayer.
    public int toPray(int damageTakenByAlly) {
        switch (prayer) {
            case NIVEL_1:
                return ((int) (damageTakenByAlly * 0.20)) * 1;
            case NIVEL_2:
                return ((int) (damageTakenByAlly * 0.20)) * 2;
            case NIVEL_3:
                return ((int) (damageTakenByAlly * 0.20)) * 3;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Life cured:" + prayer + ", damage revenged" + revenge;
    }
}
