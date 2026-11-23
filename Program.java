import Entities.DarkMage;
import Entities.Enemies;
import Entities.ForgeExecutioner;
import Entities.Monk;
import Entities.Personagem;
import Entities.Samurai;
import Entities.Tank;
import Services.BattleService;

public class Program {
    public static void main(String[] args) {
        BattleService battleService = new BattleService();

        // Instanciando os Heróis
        Tank tank = new Tank("Aegis", Tank.Level.NIVEL_2, Tank.Level.NIVEL_2);
        Samurai samurai = new Samurai("Kenshin", 3000, 2000, 1000, 200);
        Monk monk = new Monk("Tenzin", 1000, Monk.Level.NIVEL_1, Monk.Level.NIVEL_3, 2000);

        battleService.heroes.add(tank);
        battleService.heroes.add(samurai);
        battleService.heroes.add(monk);

        // Instanciando os Inimigos
        DarkMage darkMage = new DarkMage("Malakor");
        ForgeExecutioner executioner = new ForgeExecutioner("Brutus");

        battleService.enemies.add(darkMage);
        battleService.enemies.add(executioner);

        // Exibindo os dados iniciais dos Heróis
        System.out.println("=== HEROES ===");
        for (Personagem hero : battleService.heroes) {
            System.out.println(hero);
        }

        // Exibindo os dados iniciais dos Inimigos
        System.out.println("\n=== ENEMIES ===");
        for (Enemies enemy : battleService.enemies) {
            System.out.println(enemy);
        }

        System.out.println("\n=== STARTING BATTLE ===");
        battleService.startBattle();
    }
}