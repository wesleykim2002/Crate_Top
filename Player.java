/*
 * Player.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 12/18/2018
 * Create player one object.
 */

import java.util.ArrayList;
import java.awt.*;
import java.io.*; 
import java.awt.image.*;
import javax.imageio.*;

class Player extends Character {
  
  //arraylist of weapons which Player One can switch to 
  private ArrayList<Weapon> weapons;//creates a new list of weapons: the player's bag of what weapons they currently have 
  private int currentWeaponSlot;
  private Weapon currentWeapon;
  private String weaponName;
  
  //constructor for Players 
  Player(double h,int x,int y,int s,String f) {
    super(h,x,y,s,f);
    //create weapons bag and add pistol 
    this.weapons = new ArrayList<Weapon>();
    weapons.add(new Pistol());
    //start off with currentWeaponSlot in slot 0
    this.currentWeaponSlot = 0;
    this.currentWeapon = weapons.get(currentWeaponSlot); 
  }
  
  //method to load in sprites
  public void loadSprites() {
    try {   
      BufferedImage spriteSheet = ImageIO.read(new File(getFile()));
           
      final int rows = 4;
      final int cols = 5;
      
      setWidth(500/cols);
      setHeight(360/rows);
      
      BufferedImage[] sprites = new BufferedImage[rows*cols];
      setSprites(sprites);
      
      for(int j = 0;j < rows;j++) {
        for(int i = 0;i < cols;i++) {
          getSprites()[(j * cols) + i] = spriteSheet.getSubimage(i * getWidth(),j * getHeight(),getWidth(),getHeight());
        }
      }
    } catch(Exception e){
      System.out.println("error loading sheet");};
  }
  
  //method to draw current player sprite
  public void draw(Graphics g) {
    g.drawImage(getSprites()[getCurrentSprite()],getX(),getY(),null);
    g.setColor(Color.white);
    g.fillRect(getX()+25, getY()-15, 50, 10);
    g.setColor(Color.green);
    double healthbar = ((getHealth()*50)/100);
    int barW = (int)(healthbar);
    g.fillRect(getX()+25, getY()-15, barW, 10);
    g.setColor(Color.black);
    g.drawRect(getX()+25, getY()-15, 50, 10);
    if (name(getWeapon()).equals("Pistol")){
      g.drawString(name(getWeapon()) + " - infinite", getX()+20, getY() - 25);
    }else{
      g.drawString(name(getWeapon()) + " - " + getWeapon().getAmmo(), getX()+20, getY() - 25);
    }
  }

  //getters 
  public Weapon getWeapon() {
    return this.currentWeapon;
  }
  
  public int getWeaponSlot() {
    return this.currentWeaponSlot;
  }
  
  public ArrayList<Weapon> getWeaponArray() {
    return this.weapons;
  }
  
  //checking collision player and any projectile 
  public void checkCollision(Projectile currentProjectile) {
    if(((currentProjectile.getX() >= getX()) && (currentProjectile.getX() <= getX()+getWidth())) && ((currentProjectile.getY() >= getY()) && (currentProjectile.getY() <=getY() + getHeight())) && (currentProjectile.checkVisible())) {
      setHealth(getHealth() - currentProjectile.getDamage());
      currentProjectile.setVisible(false);
    }
  }
  
  //switch to next weapon
  public void nextWeapon(int currentWeaponSlot,int weaponsUnlocked) {
    if((this.weapons.size() > weaponsUnlocked) || (weaponsUnlocked == 9)){
      if((this.weapons.get(currentWeaponSlot + 1).getAmmo() != 0) && (currentWeaponSlot != 9)) {
        currentWeaponSlot++;
      }else if(this.weapons.get(currentWeaponSlot + 1) == null) {
        currentWeaponSlot = 0;
      }
    }
       
  }
  
  //switch to previous weapon
  public void previousWeapon(int currentWeaponSlot,int weaponsUnlocked) {
    if((this.weapons.size() > weaponsUnlocked) || (weaponsUnlocked == 9)){
      if((this.weapons.get(currentWeaponSlot - 1).getAmmo() != 0) && (currentWeaponSlot != 0)) {
        currentWeaponSlot--;
      }else if(currentWeaponSlot == 0) {
        do {
          currentWeaponSlot = 10;
          currentWeaponSlot--;
        }while(weapons.get(currentWeaponSlot) != null);
      }
    }

  }
  
  //casting weapon names for weapon label display
  public String name(Weapon w){
    String n;
    if (w instanceof Pistol){
      n = "Pistol";
    }else if(w instanceof Uzi){
      n = "Uzi";
    }else if(w instanceof Shotgun){
      n = "Shotgun";
    }else if(w instanceof Railgun){
      n = "Railgun";
    }else{
      n = "";
    }
    return n;
  }
  
}//close PlayerOne 
  