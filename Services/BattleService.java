package Services;

import Entities.DarkMage;
import Entities.Enemies;
import Entities.ForgeExecutioner;
import Entities.Monk;
import Entities.Personagem;
import Entities.Samurai;
import Entities.Tank;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BattleService {
    public List<Personagem> heroes = new ArrayList<>();
    public List<Enemies> enemies = new ArrayList<>();
    private Personagem protectedAlly;
    private Tank protectingTank;
    private final Scanner scanner = new Scanner(System.in);

    private Personagem chooseTarget(List<Personagem> heroList) {
        List<Personagem> aliveTargets = new ArrayList<>();
        for (Personagem h : heroList) {
            if (h.getLifePoints() > 0) {
                aliveTargets.add(h);
            }
        }

        System.out.println("Choose a hero target:");
        for (int i = 0; i < aliveTargets.size(); i++) {
            System.out.println((i + 1) + ". " + aliveTargets.get(i).getName() + " (HP: " + aliveTargets.get(i).getLifePoints() + ")");
        }

        while (true) {
            System.out.print("Target number: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= aliveTargets.size()) {
                    return aliveTargets.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {
                // Trata entrada não numérica como opção inválida
            }

            System.out.println("Invalid option. Choose a number between 1 and " + aliveTargets.size() + ".");
        }
    }

    private Enemies chooseTargetEnemies(List<Enemies> enemyList) {
        List<Enemies> aliveTargets = new ArrayList<>();
        for (Enemies e : enemyList) {
            if (e.getLifePoints() > 0) {
                aliveTargets.add(e);
            }
        }

        System.out.println("Choose an enemy target:");
        for (int i = 0; i < aliveTargets.size(); i++) {
            System.out.println((i + 1) + ". " + aliveTargets.get(i).getName() + " (HP: " + aliveTargets.get(i).getLifePoints() + ")");
        }

        while (true) {
            System.out.print("Target number: ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= aliveTargets.size()) {
                    return aliveTargets.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {
                // Trata entrada não numérica como opção inválida
            }

            System.out.println("Invalid option. Choose a number between 1 and " + aliveTargets.size() + ".");
        }
    }

    public boolean hasAliveHeroes() {
        for (Personagem hero : heroes) {
            if (hero.getLifePoints() > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAliveEnemies() {
        for (Enemies enemy : enemies) {
            if (enemy.getLifePoints() > 0) {
                return true;
            }
        }
        return false;
    }

    private void applyDamage(Personagem target, int damage) {
        int baseDamage = Math.max(0, damage);
        int finalDamage = Math.max(0, baseDamage - target.getDefense());

        int newLife = target.getLifePoints() - finalDamage;
        if (newLife < 0) {
            newLife = 0;
        }
        target.setLifePoints(newLife);

        System.out.println(target.getName() + " took " + finalDamage + " damage! (Remaining HP: " + target.getLifePoints() + ")");
    }

    private void applyDamage(Enemies target, int damage) {
        applyDamage(target, damage, false);
    }

    private void applyDamage(Enemies target, int damage, boolean isMagicDamage) {
        int baseDamage = Math.max(0, damage);
        int defense = isMagicDamage ? target.getMagicDefense() : target.getDefense();
        int finalDamage = Math.max(0, baseDamage - defense);

        int newLife = target.getLifePoints() - finalDamage;
        if (newLife < 0) {
            newLife = 0;
        }
        target.setLifePoints(newLife);

        if (target instanceof ForgeExecutioner && finalDamage > 0) {
            ForgeExecutioner executioner = (ForgeExecutioner) target;
            executioner.incrementRagePoints(finalDamage);
        }

        System.out.println(target.getName() + " took " + finalDamage + " damage! (Remaining HP: " + target.getLifePoints() + ")");
    }

    private int readChoice(int min, int max) {
        while (true) {
            System.out.print("Choose (" + min + "-" + max + "): ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {
                // Trata entrada não numérica como opção inválida
            }

            System.out.println("Invalid option. Enter a number between " + min + " and " + max + ".");
        }
    }

    private int readNonNegativeInt() {
        while (true) {
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Trata entrada não numérica como número inválido
            }

            System.out.println("Invalid number. Enter 0 or greater.");
        }
    }

    private void executeHeroesTurn() {
        for (Personagem hero : heroes) {
            if (hero.getLifePoints() <= 0) {
                continue;
            }

            if (!hasAliveEnemies()) {
                break;
            }

            System.out.println("\n--- " + hero.getName() + "'s Turn ---");

            if (hero instanceof Tank) {
                Tank tank = (Tank) hero;
                System.out.println("1. Shield Bash");
                System.out.println("2. Protect Ally (mitigate incoming damage for an ally)");
                int tankChoice = readChoice(1, 2);

                if (tankChoice == 1) {
                    Enemies target = chooseTargetEnemies(enemies);
                    applyDamage(target, tank.shieldBash());
                } else {
                    Personagem ally = chooseTarget(heroes);
                    protectedAlly = ally;
                    protectingTank = tank;
                    System.out.println(tank.getName() + " is now protecting " + ally.getName());
                }
            } else if (hero instanceof Samurai) {
                Samurai samurai = (Samurai) hero;
                System.out.println("1. Melee Attack");
                System.out.println("2. Ranged Attack");
                int samuraiChoice = readChoice(1, 2);

                Enemies samuraiTarget = chooseTargetEnemies(enemies);
                int samuraiDamage = samuraiChoice == 1 ? samurai.meleeAttack() : samurai.rangedAttack();
                applyDamage(samuraiTarget, samuraiDamage);
            } else if (hero instanceof Monk) {
                Monk monk = (Monk) hero;
                System.out.println("1. To Pray (heal an ally based on reference damage)");
                System.out.println("2. Avenged (vengeance damage to an enemy based on damage taken)");
                int monkChoice = readChoice(1, 2);

                System.out.print("Reference damage value: ");
                int refDamage = readNonNegativeInt();

                if (monkChoice == 1) {
                    Personagem allyToHeal = chooseTarget(heroes);
                    int healAmount = monk.toPray(refDamage);
                    allyToHeal.setLifePoints(allyToHeal.getLifePoints() + healAmount);
                    System.out.println(monk.getName() + " prayed for " + allyToHeal.getName() + " and restored " + healAmount + " HP! (Current HP: " + allyToHeal.getLifePoints() + ")");
                } else {
                    Enemies enemyToAvenge = chooseTargetEnemies(enemies);
                    int revengeDamage = monk.avenged(refDamage);
                    applyDamage(enemyToAvenge, revengeDamage);
                }
            }
        }
    }

    private void dealDamageToHero(Personagem target, int rawDamage) {
        if (protectedAlly != null && protectingTank != null && target == protectedAlly && protectingTank.getLifePoints() > 0) {
            int damageTaken = protectingTank.protectAlly(rawDamage);
            applyDamage(protectingTank, damageTaken);
            System.out.println(protectingTank.getName() + " protected " + target.getName() + "!");
            protectingTank = null;
            protectedAlly = null;
            return;
        }

        applyDamage(target, rawDamage);
    }

    private void executeEnemiesTurn() {
        for (Enemies enemy : enemies) {
            if (enemy.getLifePoints() <= 0) {
                continue;
            }

            if (!hasAliveHeroes()) {
                break;
            }

            System.out.println("\n--- " + enemy.getName() + "'s Turn ---");

            if (enemy instanceof DarkMage) {
                DarkMage darkMage = (DarkMage) enemy;
                System.out.println("1. Basic Attack");
                System.out.println("2. Arcane Blast (Mana: " + darkMage.getManaPoints() + ")");
                System.out.println("3. Regenerate Mana (Mana: " + darkMage.getManaPoints() + ")");
                int mageChoice = readChoice(1, 3);

                if (mageChoice == 1) {
                    Personagem target = chooseTarget(heroes);
                    dealDamageToHero(target, darkMage.getAttack());
                } else if (mageChoice == 2) {
                    if (!darkMage.canUseArcaneBlast()) {
                        System.out.println("Not enough mana for Arcane Blast! Performing Basic Attack instead.");
                        Personagem target = chooseTarget(heroes);
                        dealDamageToHero(target, darkMage.getAttack());
                        continue;
                    }

                    try {
                        int blastDamage = darkMage.arcaneBlast();
                        System.out.println("Choose a target for Arcane Blast (against enemy magic/physical rules):");
                        Personagem target = chooseTarget(heroes);
                        dealDamageToHero(target, blastDamage);
                    } catch (IllegalStateException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    int newMana = darkMage.regenerateMana();
                    System.out.println(darkMage.getName() + " regenerated mana! Current Mana: " + newMana);
                }
            } else if (enemy instanceof ForgeExecutioner) {
                ForgeExecutioner executioner = (ForgeExecutioner) enemy;
                System.out.println("1. Basic Attack");
                System.out.println("2. Crushing Blow (Rage: " + executioner.getRagePoints() + ")");
                int execChoice = readChoice(1, 2);

                if (execChoice == 1) {
                    Personagem target = chooseTarget(heroes);
                    dealDamageToHero(target, executioner.getAttack());
                } else {
                    if (!executioner.canUseCrushingBlow()) {
                        System.out.println("Not enough rage for Crushing Blow! Performing Basic Attack instead.");
                        Personagem target = chooseTarget(heroes);
                        dealDamageToHero(target, executioner.getAttack());
                        continue;
                    }

                    try {
                        int crushingDamage = executioner.crushingBlow();
                        Personagem target = chooseTarget(heroes);
                        dealDamageToHero(target, crushingDamage);
                    } catch (IllegalStateException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    public void startBattle() {
        int round = 1;

        while (hasAliveHeroes() && hasAliveEnemies()) {
            System.out.println("\n================ ROUND " + round + " ================");
            executeHeroesTurn();

            if (!hasAliveEnemies()) {
                break;
            }

            executeEnemiesTurn();

            // Garante que a proteção do Tank não vaze para a próxima rodada caso ninguém ataque o aliado protegido
            protectedAlly = null;
            protectingTank = null;

            round++;
        }

        System.out.println("\n================ BATTLE OVER ================");
        if (hasAliveHeroes()) {
            System.out.println("Victory! The heroes defeated all enemies!");
        } else {
            System.out.println("Defeat! All heroes have fallen.");
        }
    }
}
