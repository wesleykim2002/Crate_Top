/*
 * Projectile.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/16/2019
 * To create class for bullets
 */

//imports
import java.awt.*;

class Projectile {
  
  //variable for all bullets
  private int bulletSpeed;
  private int x;
  private int y;
  private Image projectileImage;
  private Boolean visible;
  private double damage;
  
  //constructor for bullets 
  Projectile(int xC,int yC,int s,String fileName,double d) {
    this.x = xC;
    this.y = yC;
    this.bulletSpeed = s;
    this.visible = true;
    this.damage = d;
    this.projectileImage = Toolkit.getDefaultToolkit().getImage(fileName);
  }
  
   //  drawing the bullet
  public void draw(Graphics g) {
    if(this.visible) {
      g.drawImage(projectileImage,(int)getX(),(int)getY(),null);
    }
  }
    
  //method for moving the bullets 
  public void moveX() {
    this.x += getSpeed();
  }
  
  public void moveY() {
    this.y += getSpeed();
  }
  
  //setters
  public void setVisible(boolean value) {
    this.visible = value;
  }
  
  public void setX(int newX) {
    this.x = newX;
  }
  
  public void setY(int newY) {
    this.y = newY;
  }
  
  public void setProjectile(String imageFile) {
    this.projectileImage = Toolkit.getDefaultToolkit().getImage(imageFile);
  }
  
  //getters
  public double getDamage() {
    return this.damage;
  }
    
  public boolean checkVisible() {
    return this.visible;
  }
  
  public int getSpeed() {
    return this.bulletSpeed;
  }
  
  public int getX() {
    return this.x;
  }
  
  public int getY() {
    return this.y;
  }
  
}//close Projectile