package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Hero extends Creature implements Serializable {
    private int row;
    private int col;
    private int level;
    private Backpack backpack = new Backpack("Backpack",0);
    private BagOfGold bagOfGold = new BagOfGold("Bag of gold", 100,0);
    private QuestItemBag questItemBag = new QuestItemBag();
    private int baseHeroDamage = getDamage();
    private int swordDamage = 0;
    private int totalDamage = baseHeroDamage + swordDamage;

    public Hero(String name, int health, int damage, int maxHealth) {
        super(name, health, damage, maxHealth);
        //this.row = 12;
        //this.col = 1;
        this.level = 1;
    }


    public int getTotalGoldInBag() {
        return this.bagOfGold.getAmountOfGold();
    }

    public void addItemToBackpack(Item item) {
        backpack.addItem(item);
    }

    public void addGoldToBagOfGold(int gold) {
        this.bagOfGold.addAmountOfGold(gold);
    }

    public void addQuestItemToQuestBag(QuestItem item){
        this.questItemBag.addItem(item);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public QuestItemBag getQuestItemBag() {
        return questItemBag;
    }

    public void heroFight(Monster monster) throws InterruptedException {

        boolean control = true;
        monster.displayInfo();

        while (control) {

            int fight = attack();
            int fightMonster = attackMonster();


            if (fight >= 50) {
                int changeMonsterHealth = monster.getHealth();
                System.out.println(" ");
                System.out.println("You hit the enemy!");
                int newMonsterHealth = monster.setMonsterHealth(changeMonsterHealth - this.totalDamage);

                if (newMonsterHealth <= 0) {
                    System.out.println("Enemy died.");
                    levelUp();
                    control = false;
                } else {
                    System.out.println("Enemy health: " + monster.setMonsterHealth(changeMonsterHealth - this.totalDamage) + "/" + monster.maxHealth);
                    Thread.sleep(1500);
                }

            } else {
                System.out.println(" ");
                System.out.println("You missed!");
            }

            if (monster.getHealth() > 0) {
                if (fightMonster >= 50) {
                    int changeHeroHealth = super.getHealth();
                    System.out.println(" ");
                    System.out.println("The enemy hit you!");
                    int newHeroHealth = super.setHeroHealth(changeHeroHealth - monster.getDamage());

                    if (newHeroHealth <= 0) {
                        System.out.println("You died.");
                        //System.out.println("Health: 0" + "/" + super.maxHealth);
                        control = false;
                    } else {
                        System.out.println("Health: " + super.setHeroHealth(changeHeroHealth - monster.getDamage()) + "/" + super.maxHealth);
                        Thread.sleep(1500);
                    }

                } else {
                    System.out.println(" ");
                    System.out.println("Enemy missed!");
                }
            }

        }
    }

    private int attack() {
        Random r = new Random();
        int low = 1;
        int high = 100;
        int result = r.nextInt(high - low) + low;
        return result;
    }

    private int attackMonster() {
        Random r = new Random();
        int low = 1;
        int high = 100;
        int result = r.nextInt(high - low) + low;
        return result;
    }

    public void openBackpack() {
        boolean backpackIsOpen = true;
        while (backpackIsOpen) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("............................");
                System.out.println("Inventory: ");
                backpack.showItemsInBackpack();
                System.out.println("'1' To heal yourself");
                System.out.println("'2' To equip a weapon");
                System.out.println("'3' To remove an item from backpack.");
                System.out.println("'4' To close backpack");
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {

                    case 1:
                        if (returnHealthPotionFromBackpack() == null) {
                            System.out.println("You have no health potions in your inventory.");
                        } else {
                            if (getHealth() < getMaxHealth()) {
                                consumeHealthPotion();
                                System.out.println("Your health is now " + getHealth());
                            } else if (getHealth() >= getMaxHealth()) {
                                setHeroHealth(getMaxHealth());
                                System.out.println("Your health is full.");
                            }
                        }
                        break;
                    case 2:
                        equipSword();
                        break;

                    case 3:
                        backpack.removeItem();
                        break;

                    case 4:
                        backpackIsOpen = false;
                        break;

                    default:
                        System.out.println("Incorrect button. To choose between options use '1', '2', '3' or '4'");
                }
            } catch (Exception ex) {
                System.out.println("Enter a number between 1-4.");
                System.out.println("Hit <enter> to try again.");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            }

        }
    }

    private void levelUp() {
        super.setHeroMaxHealth(getMaxHealth() + 10); // This is just a TEST. I was just wondering why we use "super.", discard when TEST is done.
        super.setDamage(this.baseHeroDamage += 10);
        this.baseHeroDamage = getDamage();
        this.totalDamage = this.baseHeroDamage + this.swordDamage;
        this.level += 1;
        System.out.println("===========================");
        System.out.println("\u001B[32mYou Won!\033[0m");
        System.out.println("===== You leveled up! =====");
        System.out.println("Health is: " + getHealth());
        System.out.println("Max health is: " + getMaxHealth());
        System.out.println("Base damage is: " + this.baseHeroDamage);
        System.out.println("===========================");
        System.out.println(" ");

    }

    public void equipSword() {
        ArrayList<Sword> swords = new ArrayList<>();
        if (backpack.getItems() != null) {
            for (Item item : backpack.getItems()) {
                if (item instanceof Sword) {
                    swords.add((Sword) item);
                }
            }
            if (!swords.isEmpty()) {
                Collections.sort(swords);
                this.swordDamage = swords.get(0).getSwordDamage();
                this.totalDamage = this.baseHeroDamage + this.swordDamage;
                System.out.printf("You equipped the strongest sword in your inventory. You now deal %d damage \n", this.totalDamage);
            } else {
                System.out.println("You have no swords in your backpack.");
            }
        } else {
            System.out.println("You have no items in your backpack.");
        }

    }


    public HealthPotion returnHealthPotionFromBackpack() {
        ArrayList<HealthPotion> healthPotions = new ArrayList<>();
        for (Item item : backpack.getItems()) {
            if (item instanceof HealthPotion) {
                healthPotions.add((HealthPotion) item);
            }
            Collections.sort(healthPotions);
            for (HealthPotion potion : healthPotions) {
                return potion;
            }
        }
        return null;
    }


    public void consumeHealthPotion() {
        restoreHealth(returnHealthPotionFromBackpack());
        backpack.removeItemFromBackpack(returnHealthPotionFromBackpack());
    }

    public void restoreHealth(HealthPotion potion) {
        if (getHealth() < getMaxHealth()) {
            setHeroHealth(getHealth() + potion.getHealthPoints());
            if (getHealth() >= getMaxHealth()) {
                setHeroHealth(getMaxHealth());
            }
        }
    }

    public int[] westOfHero() {
        return new int[]{this.row, this.col - 1};
    }

    public int[] eastOfHero() {
        return new int[]{this.row, this.col + 1};
    }

    public int[] northOfHero() {
        return new int[]{this.row - 1, this.col};
    }

    public int[] southOfHero() {
        return new int[]{this.row + 1, this.col};
    }

    public void moveWest() {
        this.col -= 1;
    }

    public void moveEast() {
        this.col += 1;
    }

    public void moveNorth() {
        this.row -= 1;
    }

    public void moveSouth() {
        this.row += 1;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public BagOfGold getBagOfGold() {
        return bagOfGold;
    }

    @Override
    public int getDamage() {
        return this.damage;
    }

    public void displayInfo() {
        System.out.println("---- Hero ----");
        super.displayInfo();
        System.out.printf("Level: %d  -  Gold: %s  -  Total Damage: %d\n", this.level, this.bagOfGold, this.totalDamage);
        System.out.printf("---- Backpack: %d items ----\n", this.backpack.numberOfItem());
    }
}
