/*
 * Zombie.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 12/18/2018
 * Create zombie object 
 */

//imports
import java.awt.*;
import java.io.*; 
import java.awt.image.*;
import javax.imageio.*;

class Zombie extends Character {
  
  //constructor for class Zombie
  Zombie(double h,int x,int y,int s,String f) {
    super(h,x,y,s,f);
    loadSprites();
  }
 
  public void loadSprites() {
    try {   
      BufferedImage spriteSheet = ImageIO.read(new File(getFile()));
           
      final int rows = 4;
      final int cols = 5;
      
      setWidth(350/cols);
      setHeight(288/rows);
      
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
    
  //drawing the zombie
  public void draw(Graphics g) {
    if(getLife()) {
      g.drawImage(getSprites()[getCurrentSprite()],getX(),getY(),null);
    }
  }
  
  //moving toward players 
  public void move(Player[] players){
    //coordinates
    int x1 = players[0].getX();
    int y1 = players[0].getY();
    int x2 = players[1].getX();
    int y2 = players[1].getY();
    int x3 = getX();
    int y3 = getY();

    //x and y differences
    int deltaX1 = x3-x1;
    int deltaY1 = y3-y1;
    int deltaX2 = x3-x2;
    int deltaY2 = y3-y2;

    //distances
    double d1 = Math.sqrt(Math.pow(deltaX1,2)+Math.pow(deltaY1,2));
    double d2 = Math.sqrt(Math.pow(deltaX2,2)+Math.pow(deltaY2,2));

    if (d1<d2){
      if ((Math.abs(deltaX1)<Math.abs(deltaY1))&&(Math.abs(deltaX1)>=10)){
        if (deltaX1>=10){
          moveLeft();
        }else{
          moveRight();
        }
        }else if ((Math.abs(deltaX1)>Math.abs(deltaY1))&&(Math.abs(deltaY1)>=10)){
        if (deltaY1>=10){
          moveUp();
        }else{
          moveDown();
        }
      }else if(Math.abs(deltaX1)<10){
        if (deltaY1>=10){
          moveUp();
        }else{
          moveDown();
        }
      }else if(Math.abs(deltaY1)<10){
        if (deltaX1 >= 10){
          moveLeft();
        }else{
          moveRight();
        }
      }else if ((Math.abs(deltaX1)>Math.abs(deltaY1))&&(Math.abs(deltaY1)>=10)){
        if (deltaY1>=10){
          moveUp();
        }else{
          moveDown();
        }
      }
    }else {
      if ((Math.abs(deltaX2)<Math.abs(deltaY2))&&(Math.abs(deltaX2)>=10)){
        if (deltaX2>=10){
          moveLeft();
        }else{
          moveRight();
        }
        }else if ((Math.abs(deltaX2)>Math.abs(deltaY2))&&(Math.abs(deltaY2)>=10)){
        if (deltaY2>=10){
          moveUp();
        }else{
          moveDown();
        }
      }else if(Math.abs(deltaX2)<10){
        if (deltaY2>=10){
          moveUp();
        }else{
          moveDown();
        }
      }else if(Math.abs(deltaY2)<10){
        if (deltaX2 >= 10){
          moveLeft();
        }else{
          moveRight();
        }
      }else if ((Math.abs(deltaX2)>Math.abs(deltaY2))&&(Math.abs(deltaY2)>=10)){
        if (deltaY2>=10){
          moveUp();
        }else{
          moveDown();
        }
      }
    }
  }
  
  //method for zombies to attack players 
  public void attack(Player[] players) {
    //coordinates
    int x1 = players[0].getX();
    int y1 = players[0].getY();
    int x2 = players[1].getX();
    int y2 = players[1].getY();
    int x3 = getX();
    int y3 = getY();

    //x and y differences
    int deltaX1 = x3-x1;
    int deltaY1 = y3-y1;
    int deltaX2 = x3-x2;
    int deltaY2 = y3-y2;

    //distances
    double d1 = Math.sqrt(Math.pow(deltaX1,2)+Math.pow(deltaY1,2));
    double d2 = Math.sqrt(Math.pow(deltaX2,2)+Math.pow(deltaY2,2));
    
    if(d1 <= 100) {
      players[0].setHealth(players[0].getHealth() - 1);
    }else if(d2 <= 100) {
      players[1].setHealth(players[1].getHealth() - 1);
    }
  }
  
  //moving methods
  public void moveLeft(){
    setX(getX() - 1);
    setDirection("left");
  }
  public void moveRight(){
    setX(getX() + 1);
    setDirection("right");
  }
  public void moveUp(){
    setY(getY() - 1);
    setDirection("up");
  }
  public void moveDown(){
    setY(getY() + 1);
    setDirection("down");
  }  
  
  //checking collision with zombie and bullet 
  public void checkCollision(Projectile currentProjectile,Graphics g) {
    if(((currentProjectile.getX() >= getX()) && (currentProjectile.getX() <= getX()+getWidth())) && ((currentProjectile.getY() >= getY()) && (currentProjectile.getY() <=getY() + getHeight())) && (currentProjectile.checkVisible())) {
      setHealth(getHealth() - currentProjectile.getDamage());
      currentProjectile.setVisible(false);
    }
  }

}//close Zombie 