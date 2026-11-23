package Entities;


public class Tank extends Personagem {

    public enum Level {
        NIVEL_1(1),
        NIVEL_2(2),
        NIVEL_3(3);

        private final int value;

        Level(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    private Level shieldPower;
    private Level armorRating;

    public Tank(String name, Level shieldPower, Level armorRating) {
        super(name, 5000, 30, 1000);
        this.shieldPower = shieldPower;
        this.armorRating = armorRating;
    }

    public Level getShieldPower() {
        return shieldPower;
    }

    public void setShieldPower(Level shieldPower) {
        this.shieldPower = shieldPower;
    }

    public Level getArmorRating() {
        return armorRating;
    }

    public void setArmorRating(Level armorRating) {
        this.armorRating = armorRating;
    }

    // Ataque com escudo baseado na defesa e no nível de ShieldPower.
    public int shieldBash() {
        switch (shieldPower) {
            case NIVEL_1:
                return (getDefense() * 3) + (shieldPower.getValue() * 1);
            case NIVEL_2:
                return (getDefense() * 3) + (shieldPower.getValue() * 2);
            case NIVEL_3:
                return (getDefense() * 3) + (shieldPower.getValue() * 3);
            default:
                return 0;
        }
    }

    // Protege parte do dano recebido pelo aliado de acordo com o ArmorRating.
    public int protectAlly(int damageTakenByAlly) {
        switch (armorRating) {
            case NIVEL_1:
                // Se o resultado for menor ou igual a zero, a proteção absorveu tudo (retorna 0);
                // caso contrário, retorna o dano restante.
                int result = (damageTakenByAlly - 300 <= 0) ? 0 : (damageTakenByAlly - 300);
                return result;
            case NIVEL_2:
                int result0 = (damageTakenByAlly - 400 <= 0) ? 0 : (damageTakenByAlly - 400);
                return result0;
            case NIVEL_3:
                int result1 = (damageTakenByAlly - 500 <= 0) ? 0 : (damageTakenByAlly - 500);
                return result1;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "level of shield:" + shieldPower + ", level of armor: " + armorRating;
    }
}
