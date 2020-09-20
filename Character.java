/*
 * Character.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 12/18/2018
 * To create the character object for zombies, demons and the players 
 */

//imports
import java.awt.*;
import java.awt.image.*;

abstract class Character {
  //counter variables - these are used to make the sprites change slowly
  private int upC = 0;
  private int downC = 0;
  private int leftC = 0;
  private int rightC = 0;
  
  //character variables 
  private double health;
  private int xCoordinate;
  private int yCoordinate;
  private String direction; 
  private String fileName;
  private int speed;
  private boolean live;
  private int height;
  private int width;
  
  int limit = 100;
  
  //class sprite sheets 
  BufferedImage[] sprites;
  int currentSprite;
  int currentStep;
  
  //constructor for all characters 
  Character(double h,int x,int y,int s,String fileName) {
    this.fileName = fileName;
    this.health = h;
    this.xCoordinate = x;
    this.yCoordinate = y;
    this.speed= s;
    //load sprite sheet
    loadSprites();
    this.currentSprite = 0;
    this.currentStep = 0;
    //declare position as standing 
    this.direction = "stand";
    //when character if created they are alive 
    this.live = true;
  }
  
  //abstract method to load in sprites
  abstract public void loadSprites();
  
  //abstract method for drawing character 
  abstract public void draw(Graphics g);
  
  //update current character sprite 
  public void update() { 
    if (this.currentStep>=128) {
      this.currentStep=0;
      this.direction="stand";
    }
    
    if(this.direction.equals("down")) {
      this.downC++;
      if (this.downC == limit){
        this.currentSprite++;
        this.currentStep++;
        this.downC = 0;
      }
      if (this.currentSprite>=5) {
        this.currentSprite=1;
      }
    }
    
    if(this.direction.equals("stand down")) {
      this.currentSprite = 0;
    }
    
    if(this.direction.equals("left")) { 
      if (this.currentSprite<6){
        this.currentSprite = 6;
      }else{
        this.leftC++;
        if (this.leftC == limit){
          this.currentSprite++;
          this.currentStep++;
          this.leftC = 0;
        }
        if (this.currentSprite>=10) {
          this.currentSprite=6;
        }
      }
    }
    
    if(this.direction.equals("stand left")) {
      this.currentSprite = 5;
    }
    
    if(this.direction.equals("right")) { 
      if (this.currentSprite<11){
        this.currentSprite = 11;
      }else{
        this.rightC++;
        if (this.rightC == limit){
          this.currentSprite++;
          this.currentStep++;
          this.rightC = 0;
        }
        if (this.currentSprite>=15) {
          this.currentSprite=11;
        }
      }
    }
    
    if(this.direction.equals("stand right")) {
      this.currentSprite = 10;
    }
    
    if(this.direction.equals("up")) {
      if (this.currentSprite<16){
        this.currentSprite=16;
      }else{
        this.upC++;
        if (this.upC == limit){
          this.currentSprite++;
          this.currentStep++;
          this.upC = 0;
        }
        if (this.currentSprite>= 20){
          this.currentSprite = 16;
        }
      }
    }
    
    if(this.direction.equals("stand up")) {
      this.currentSprite = 15;
    }
  }
    
  //setters 
  public void setHeight(int h) {
    this.height = h;
  }
  
  public void setWidth(int w) {
    this.width = w;
  }
  
  public void setLive(boolean value) {
    this.live = value;
  }
  
  public void setFile(String file) {
    this.fileName = file;
  }
  
  public void setHealth(double h) {
    this.health = h;
  }
  
  public void setDirection(String direction) {
    this.direction = direction;
  }
  
  public void setSprites(BufferedImage[] sprites) {
    this.sprites = sprites;
  }
  
  public void setX(int x) {
    this.xCoordinate = x;
  }
  
  public void setY(int y) {
    this.yCoordinate = y;
  }
  
  //getters 
  public int getHeight() {
    return this.height;
  }
  
  public int getWidth() {
    return this.width;
  }
  
  public boolean getLife() {
    return this.live;
  }
  
  public int getSpeed() {
    return this.speed;
  }
  
  public double getHealth() {
    return this.health;
  }
  
  public int getX() {
    int rounded = this.xCoordinate;
    return rounded;
  }
  
  public int getY() {
    return this.yCoordinate;
  }
  
  public String getFile() {
    return this.fileName;
  }
  
  public BufferedImage[] getSprites() {
    return this.sprites;
  }
  
  public String getDirection() {
    return this.direction;
  }
  
  public int getCurrentSprite() {
    return this.currentSprite;
  }
}//close Character 