/*
 * Crate.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 12/20/2018
 * Create crate object 
 */

//imports 
import java.awt.*;

class Crate {
  
  //variables for crates
  private int x,y;
  private int width,height;
  private Boolean visible;
  private Image crate = Toolkit.getDefaultToolkit().getImage("crate.png");
  
  //constructor 
  Crate(int x,int y) {
    this.x = x;
    this.y = y;
    this.width = 68;
    this.height = 58;
    this.visible = true;
  }
  
  //method to return item when open crate 
  public String open(int available) {
    String returnedWeapon = "";
    if(available == 1) {
      returnedWeapon = "uzi";
    }else if(available == 2) {
      int randomWeapon = (int)(Math.random()*2);
      if(randomWeapon == 0) {;
        returnedWeapon = "uzi";
      }else{
        returnedWeapon = "shotgun";
      }
    }else{
      int randomWeapon = (int)(Math.random()*3);
      if(randomWeapon == 0) {
        returnedWeapon = "uzi";
      }else if(randomWeapon == 1) {
        returnedWeapon = "shotgun";
        return returnedWeapon;
      }else if(randomWeapon == 2) {
        returnedWeapon = "railgun";
      }
    }
    return returnedWeapon;
  }
      
  //drawing the crate 
  public void draw(Graphics g) {
    g.drawImage(crate,getX(),getY(),null);
  }
  
  //getters  
  public int getX() { 
    return this.x;
  }

  public int getY() {
    return this.y;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public Boolean checkVisible() {
    return this.visible;
  }
  
  //checking when player 
  public Boolean checkCollision(Player[] players,int score) {
    if(((players[0].getX() >= this.x) && (players[0].getX() <= this.x+this.width)) && ((players[0].getY() >= this.y) && (players[0].getY() <=this.y + this.height))) {
      return true;
    }
    
    if(((players[1].getX() >= this.x) && (players[1].getX() <= this.x+this.width)) && ((players[1].getY() >= this.y) && (players[1].getY() <=this.y + this.height))) {
      return true;
    }else{
      return false;
    }
  }
  
}//close Crate 
    
  
  