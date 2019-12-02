package com.company;

public abstract class Creature {
    private String name;
    protected int health;  //Changed health, maxHealth, damage to protected.
    protected int damage;
    protected int maxHealth;

    //private final int MAX_HEALTH =  200;

    public Creature(String name, int health, int damage, int maxHealth) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxHealth = maxHealth;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public abstract int getDamage(); //Changed to int (Mantas)

    public int setHeroDamage(int damage) { // Setter for new damage?
        return this.damage = damage;
    }

    public int getHealth() { // getter for health
        return health;
    }

    public int setHeroHealth(int health) { //Setter for health ?
        return this.health = health;
    }

    public int setHealth(int health){
        return this.health = health;
    } //Setter for monster health.

    public void setHeroMaxHealth(int health) {
        this.maxHealth = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    } //Överflödig?

    public void displayInfo() {
        System.out.printf(
                "Name: %s - Health: %d/%d - Damage: %s\n",
                this.name, this.health, this.maxHealth, this.damage
        );
    }

    /*
    @Override
    public String toString() {

        return "Creature{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", damage=" + damage +
                ", maxHealth=" + maxHealth +
                '}';
    }
    */
}
