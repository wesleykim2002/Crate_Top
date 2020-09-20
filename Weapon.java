/*
 * Weapons.java 
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 12/19/2018
 * Create super primary class for all weapons
 */

abstract class Weapon {
  //some vairables 
  private double damage;
  private int ammo;
  
  //constructor for all weapons 
  Weapon(double d) {
    this.damage = d;
  }
  
  //constructor for weapons with ammo
  Weapon(double d,int a) {
    this.damage = d;
    this.ammo = a;
  }
      
  //setters 
  public void setDamage(double d) {
    this.damage = d;
  }
  
  public void setAmmo(int a) {
    this.ammo = a;
  }
  
  //getters
  public double getDamage() {
    return this.damage;
  }
  
  public int getAmmo() {
    return this.ammo;
  }
  
}//close Weapon 
