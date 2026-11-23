package Entities;

public class Samurai extends Personagem {
    private int dexterity;
    private int bowDamage;

    public Samurai(String name, int lifePoints, int attack, int defense, int dexterity) {
        super(name, lifePoints, attack, defense);
        this.dexterity = dexterity;
        this.bowDamage = 20;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getBowDamage() {
        return bowDamage;
    }

    public void setBowDamage(int bowDamage) {
        this.bowDamage = bowDamage;
    }

    // Quanto maior a Destreza (Dex), maior o dano, multiplicado pelo dano do arco.
    public int rangedAttack() {
        if (dexterity <= 25) {
            return bowDamage * dexterity;
        } else {
            // Quando a Destreza passa de 25, o multiplicador de dano é limitado a 40.
            return bowDamage * 40;
        }
    }

    // O ataque corpo a corpo é o ataque base mais 50% da Destreza (DEX).
    public int meleeAttack() {
        return getAttack() + (int) (dexterity * 0.50);
    }

    // Sobrescreve toString() para incluir os atributos específicos da classe.
    @Override
    public String toString() {
        return super.toString() + "dexterity:" + dexterity + ", bow damage:" + bowDamage;
    }
}