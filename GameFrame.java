/**
 * GameFrame.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/17/2019
 * To create GameFrame in which the game is played on as well as determining what each character does. 
 */

//Sound Imports
import java.io.File;
import javax.sound.sampled.*;

//Graphics &GUI imports
import javax.swing.*;
import java.awt.*;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//imports 
import java.util.ArrayList;

class GameFrame extends JFrame{
  
  //variables 
  private int screenWidth = 1750;
  private int screenHeight = 1000;
  private double zombieSpawnRate = 0;
  private int zombieRate = 3;
  private long time;
  private Map maps = null;
  private int score;
  private int weaponsUnlocked = 1;
  private boolean playerOneUp,playerOneRight,playerOneDown,playerOneLeft, playerOneShoot, playerOneN, playerOneP;
  private boolean playerTwoUp,playerTwoRight,playerTwoDown,playerTwoLeft, playerTwoShoot, playerTwoN, playerTwoP;
  
  //player array
  private Player[] players = new Player[2];
  
  //array lists
  private ArrayList<Projectile> projectileH= new ArrayList<Projectile>();
  private ArrayList<Projectile> projectileV= new ArrayList<Projectile>();
  private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
  private ArrayList<Crate> crates = new ArrayList<Crate>();
  
  private String [][]world;
  private int maxX, maxY, GridToScreenRatioX, GridToScreenRatioY;
  
  //class variable (non-static)
  static GameAreaPanel gamePanel;
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  GameFrame(){
    super("CRATE TOP");  
    this.thisFrame = this;
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    try {
      maps = new Map();
    }catch(Exception e) {
      e.printStackTrace();
    }
    
    //call map to draw 
    world = maps.getMap();
    GridToScreenRatioY = maxY / (world.length+1);  //ratio to fit in screen as square map
    GridToScreenRatioX = maxX / (world.length+1);  //ratio to fit in screen as square map
    
    // Set the frame to full screen 
    this.setSize(screenWidth,screenHeight);
    //this.setUndecorated(true);  //Set to true to remove title bar
    this.setResizable(false);
    
    //Set up the game panel (where we put our graphics)
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
    
    //create Player objects 
    players[0] = maps.getPlayer(1);
    players[1] = maps.getPlayer(2);
    
  } //End of Constructor
  
  //the main gameloop - this is where the game state is updated
  public void animate() {
    
    while(true){
      try{ Thread.sleep(500);} catch (Exception exc){}  //delay
      this.repaint();
    }    
  }
  
  /** --------- GAME  ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g){
      super.paintComponent(g); //required
      
      //current time
      time = System.nanoTime()/1000000000;
      
      if((players[0] != null) || (players[1] != null)) {
        //drawing background
        Image background = Toolkit.getDefaultToolkit().getImage("background.png");
        for (int i = 0; i<world.length; i++){
          for (int j=0; j<world[0].length; j++){
            g.drawImage(background,j*GridToScreenRatioX,i*GridToScreenRatioY,GridToScreenRatioX,GridToScreenRatioY,this);
          }
        }
        
        //drawing crates if spawned
        for(int i = 0;i < crates.size();i++) {
          Boolean crateInteraction = crates.get(i).checkCollision(players,score);
          if((crates.get(i).checkVisible()) && (crateInteraction == false)) {
            crates.get(i).draw(g);
          }
          //if player collides with player
          if(crateInteraction) {
            int available = score/500;
            String returnedWeapon = crates.get(i).open(available);
            if (players[0] != null){
              if(returnedWeapon.equals("uzi")) {
                for(int k = players[0].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[0].getWeaponArray().get(k) instanceof Uzi) {
                    players[0].getWeaponArray().get(k).setAmmo(150);
                  }else{
                    players[0].getWeaponArray().add(new Uzi());
                  }
                }
              }else if(returnedWeapon.equals("shotgun")) {
                for(int k = players[0].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[0].getWeaponArray().get(k) instanceof Shotgun) {
                    players[0].getWeaponArray().get(k).setAmmo(30);
                  }else{
                    players[0].getWeaponArray().add(new Shotgun());
                  }
                }
              }else if(returnedWeapon.equals("railgun")) {
                for(int k = players[0].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[0].getWeaponArray().get(k) instanceof Railgun) {
                    players[0].getWeaponArray().get(k).setAmmo(20);
                  }else{
                    players[0].getWeaponArray().add(new Railgun());
                  }
                }
              }
            }
            
            if (players[1]!=null){
              if(returnedWeapon.equals("uzi")) {
                for(int k = players[1].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[1].getWeaponArray().get(k) instanceof Uzi) {
                    players[1].getWeaponArray().get(k).setAmmo(150);
                  }else{
                    players[1].getWeaponArray().add(new Uzi());
                  }
                }
              }else if(returnedWeapon.equals("shotgun")) {
                for(int k = players[1].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[1].getWeaponArray().get(k) instanceof Shotgun) {
                    players[1].getWeaponArray().get(k).setAmmo(30);
                  }else{
                    players[1].getWeaponArray().add(new Shotgun());
                  }
                }
              }else if(returnedWeapon.equals("railgun")) {
                for(int k = players[1].getWeaponArray().size()-1;k > -1;k--) {
                  if(players[1].getWeaponArray().get(k) instanceof Railgun) {
                    players[1].getWeaponArray().get(k).setAmmo(20);
                  }else{
                    players[1].getWeaponArray().add(new Railgun());
                  }
                }
              }
            }
            crates.remove(i);
          }
        }
        
        //drawing zombies unless they are dead, in that case your score will be accumulated
        if (zombies.size()>0){
          for(int i = zombies.size()-1;i > -1;i--) {
            //adding delay for zombies spawning
            try
            {
              Thread.sleep(5);
            }
            catch(InterruptedException ex)
            {
              Thread.currentThread().interrupt();
            }
            
            zombies.get(i).move(players);
            zombies.get(i).attack(players);
            zombies.get(i).update();
            zombies.get(i).draw(g);
            
            //if zombies health is zero, they are dead and remove from arraylist of zombies
            if(zombies.get(i).getHealth() <= 0) {
              int randomScore = (int)(Math.random()*60) + 50;
              int crateChance = (int)(Math.random()*100);
              //spawning crates (40% chance)
              if(crateChance <= 40) {
                  crates.add(new Crate(zombies.get(i).getX(),zombies.get(i).getY()));
              }
              score += randomScore; //adding a random score for zombies 
              zombies.remove(i);
              for(int j = 0;j < players.length;j++) {
                if(players[j].getHealth() != 100) {
                  players[j].setHealth(players[j].getHealth() + 0.5);
                }
              }
            }
          }
        }
        
        
        //drawing players 
        for(int i = 0;i < players.length;i++) {
          if(players[i].getHealth()  <= 0) {
            players[i].setLive(false);
            players[i].setX(100000);
            players[i].setY(100000);
          }
          players[i].update();
          players[i].draw(g);
        }
        
        if((players[0].getLife() == false) && (players[1].getLife() == false)) {
          players[0] = null;
          players[1] = null;
        }
        
        //drawing current score
        String currentScore = ""+score;
        g.setColor(Color.BLACK);
        g.setFont(new Font("Chalkboard SE", Font.PLAIN, 40));
        g.drawString("sCOrE: " + currentScore,50,50);
        
        //moving player one
        if (players[0] != null){
          if((playerOneRight) && (players[0].getX() + players[0].getSpeed()< 1650)) {
            players[0].setX(players[0].getX() + players[0].getSpeed());
            playerOneRight = false;
          }
          
          if((playerOneLeft) && (players[0].getX()  - players[0].getSpeed() > 0)) {
            players[0].setX(players[0].getX() - players[0].getSpeed());
            playerOneLeft = false;
          }
          
          if((playerOneUp) && (players[0].getY() - players[0].getSpeed() > 0)){
            players[0].setY(players[0].getY() - players[0].getSpeed());
            playerOneUp = false;
          }
          
          if((playerOneDown) && (players[0].getY()  + players[0].getSpeed()< 850)){
            players[0].setY(players[0].getY() + players[0].getSpeed());
            playerOneDown = false;
          }
          
          //shoot
          if (playerOneShoot){
            Weapon currentWeapon = players[0].getWeapon();
            if(currentWeapon instanceof Pistol) {
              shoot("pistolSound.wav");
              if((players[0].getDirection().equals("right")) || (players[0].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[0].getX()+100,players[0].getY()+40,10,"hBullet.png",currentWeapon.getDamage()));
              }else if((players[0].getDirection().equals("left")) || (players[0].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,-10,"hBullet.png",currentWeapon.getDamage()));
              }else if((players[0].getDirection().equals("up")) || (players[0].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,-10,"vBullet.png",currentWeapon.getDamage()));
              }else if((players[0].getDirection().equals("down")) || (players[0].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,10,"vBullet.png",currentWeapon.getDamage()));
              }
            }else if((currentWeapon instanceof Uzi) && (currentWeapon.getAmmo() > 0)){
              shoot("uziSound.wav");
              if((players[0].getDirection().equals("right")) || (players[0].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[0].getX()+100,players[0].getY()+40,12,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("left")) || (players[0].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,-12,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("up")) || (players[0].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,-12,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("down")) || (players[0].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,10,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
            }else if(currentWeapon instanceof Shotgun) {
              shoot("shotgunSound.wav");
              if((players[0].getDirection().equals("right")) || (players[0].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[0].getX()+110,players[0].getY()+45,0,"rShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("left")) || (players[0].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,0,"lShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("up")) || (players[0].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,0,"uShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("down")) || (players[0].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,0,"dShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
              
            }else if(currentWeapon instanceof Railgun) {
              shoot("railgunSound.wav");
              if((players[0].getDirection().equals("right")) || (players[0].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[0].getX()+110,players[0].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[0].getX()+110,players[0].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[0].getX()+110,players[0].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("left")) || (players[0].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[0].getX()-45,players[0].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("up")) || (players[0].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[0].getX()+55,players[0].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[0].getDirection().equals("down")) || (players[0].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[0].getX()+20,players[0].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
            }
            playerOneShoot = false;
          }
        }
        
        //moving player two
        if (players[1] != null){
          if((playerTwoRight) && (players[1].getX() + players[1].getSpeed()< 1650)) {
            players[1].setX(players[1].getX() + players[1].getSpeed());
            playerTwoRight = false;
          }
          
          if((playerTwoLeft) && (players[1].getX()  - players[1].getSpeed() > 0)) {
            players[1].setX(players[1].getX() - players[1].getSpeed());
            playerTwoLeft = false;
          }
          
          if((playerTwoUp) && (players[1].getY() - players[1].getSpeed() > 0)){
            players[1].setY(players[1].getY() - players[1].getSpeed());
            playerTwoUp = false;
          }
          
          if((playerTwoDown) && (players[1].getY()  + players[1].getSpeed()< 850)){
            players[1].setY(players[1].getY() + players[1].getSpeed());
            playerTwoDown = false;
          }
          
          //shooting for player 2
          if (playerTwoShoot){
            Weapon currentWeapon = players[1].getWeapon();
            if(currentWeapon instanceof Pistol) {
              shoot("pistolSound.wav");
              if((players[1].getDirection().equals("right")) || (players[1].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[1].getX()+100,players[1].getY()+40,10,"hBullet.png",currentWeapon.getDamage()));
              }else if((players[1].getDirection().equals("left")) || (players[1].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,-10,"hBullet.png",currentWeapon.getDamage()));
              }else if((players[1].getDirection().equals("up")) || (players[1].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,-10,"vBullet.png",currentWeapon.getDamage()));
              }else if((players[1].getDirection().equals("down")) || (players[1].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,10,"vBullet.png",currentWeapon.getDamage()));
              }
            }else if((currentWeapon instanceof Uzi) && (currentWeapon.getAmmo() > 0)){
              shoot("uziSound.wav");
              if((players[1].getDirection().equals("right")) || (players[1].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[1].getX()+100,players[1].getY()+40,12,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("left")) || (players[1].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,-12,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("up")) || (players[1].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,-12,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("down")) || (players[1].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,10,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
            }else if(currentWeapon instanceof Shotgun) {
              shoot("shotgunSound.wav");
              if((players[1].getDirection().equals("right")) || (players[1].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[1].getX()+110,players[1].getY()+45,0,"rShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("left")) || (players[1].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,0,"lShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("up")) || (players[1].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,0,"uShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("down")) || (players[1].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,0,"dShotgunShell.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
              
            }else if(currentWeapon instanceof Railgun) {
              shoot("railgunSound.wav");
              if((players[1].getDirection().equals("right")) || (players[1].getDirection().equals("stand right"))) {
                projectileH.add(new Projectile(players[1].getX()+110,players[1].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[1].getX()+110,players[1].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[1].getX()+110,players[1].getY()+45,20,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("left")) || (players[1].getDirection().equals("stand left"))) {
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                projectileH.add(new Projectile(players[1].getX()-45,players[1].getY()+40,-20,"hBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("up")) || (players[1].getDirection().equals("stand up"))) {
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[1].getX()+55,players[1].getY()-90,-20,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }else if((players[1].getDirection().equals("down")) || (players[1].getDirection().equals("stand down"))) {
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                projectileV.add(new Projectile(players[1].getX()+20,players[1].getY()+90,20,"vBullet.png",currentWeapon.getDamage()));
                currentWeapon.setAmmo(currentWeapon.getAmmo() - 1);
              }
            }
            playerTwoShoot = false;
          }
        }
        
        //moving and drawing projectiles shooting horizontally 
        if (projectileH.size()>0){
          for(int i = projectileH.size()-1;i>-1;i--) {
            if((projectileH.get(i).checkVisible()) && (projectileH.get(i).getSpeed() == 0)) {
              try
              {
                Thread.sleep(1);
              }
              catch(InterruptedException ex)
              {
                Thread.currentThread().interrupt();
              }
              
              projectileH.remove(i);
            }
          if((projectileH.get(i).checkVisible()) || (projectileH.get(i).getX() > 1750) || (projectileH.get(i).getX() < 0) || (projectileH.get(i).getY() < 0) || (projectileH.get(i).getY() > 1000)) {
            projectileH.get(i).moveX();
            projectileH.get(i).draw(g);
          }else{
            projectileH.remove(i);
          }
        }
        }
        
        //moving and drawing projectiles shooting vertically 
        if (projectileV.size()>0){
          for(int i = projectileV.size()-1;i > -1;i--) {
            if((projectileV.get(i).checkVisible()) && (projectileV.get(i).getSpeed() == 0)) {
              try
              {
                Thread.sleep(1);
              }
              catch(InterruptedException ex)
              {
                Thread.currentThread().interrupt();
              }
              projectileV.remove(i);
            }
            if((projectileV.get(i).checkVisible()) || (projectileV.get(i).getX() > 1750) || (projectileV.get(i).getX() < 0) || (projectileV.get(i).getY() < 0) || (projectileV.get(i).getY() > 1000)) {
              projectileV.get(i).moveY();
              projectileV.get(i).draw(g);
            }else{
              projectileV.remove(i);
            }
          }
        }
        
        //checking if projectiles shooting horizontally hit zombies 
        for(int i = 0;i < projectileH.size();i++) {
          for(int j = 0;j < zombies.size();j++) {
            zombies.get(j).checkCollision(projectileH.get(i),g);
          }
        }
        
        //checking if projectiles shooting vertically hit zombies
        for(int i = 0;i < projectileV.size();i++) {
          for(int j = 0;j < zombies.size();j++) {
            zombies.get(j).checkCollision(projectileV.get(i),g);
          }
        }
        
        if ((players[0] != null)&&(players[1] != null)){
        //checking if projectiles shooting horizontally hit players 
        for(int i = 0;i < projectileH.size();i++) {
          for(int j = 0;j < players.length;j++) {
            players[j].checkCollision(projectileH.get(i));
          }
        }
        
        //checking for projectiles shooting vertically hit players 
        for(int i = 0;i < projectileV.size();i++) {
          for(int j = 0;j < players.length;j++) {
            players[j].checkCollision(projectileV.get(i));
          }
        }
        }
        
        //spawning in zombies 
        if(time - zombieSpawnRate > 4) {
          int randomZombieRate = (int)(Math.random()*zombieRate);
          zombieSpawnRate = time;
          for(int i = 0;i < randomZombieRate;i++) {
            int randomLocation = (int)(Math.random()*2);
            int xRandom = (int)(Math.random()*1600) + 100;
            if(randomLocation == 0) {
              zombies.add(new Zombie(40,xRandom,0,1,"zombie.png"));
            }else{
              zombies.add(new Zombie(40,xRandom,1650,1,"zombie.png"));
            }
          }
        }
        
        setDoubleBuffered(true);
        
        //repaint screen
        repaint();
      
      }else{ //if both players are dead, the game closes and opens the end game frame
        thisFrame.dispose();
        new EndGameFrame(score);
      }
        
    }
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    ArrayList <KeyEvent> keys = new ArrayList<KeyEvent>();
    
//------------ PLAYER CONTROLS -------------//
    public void keyTyped(KeyEvent e) {
    }   
    
    public void keyPressed(KeyEvent d) {
      if (!keys.contains(d)){
        keys.add(d);
      }
      
      for (int i=0; i<keys.size(); i++){
        
// ----------------- PLAYER ONE CONTROLS --------------------- //
        if(players[0] != null) {
          //key pressed to move up
          if((keys.get(i).getKeyCode() == 87)&&(players[0].getY() - players[0].getSpeed() > 0)) { //if "W" is pressed
            playerOneUp = true;
            players[0].setDirection("up");
          }
          
          //key pressed to move left 
          if((keys.get(i).getKeyCode() == 65)&&(players[0].getX() - players[0].getSpeed() > 0)) { //if "A" is pressed
            playerOneLeft = true;
            players[0].setDirection("left");
          }
          
          //key pressed to move down
          if((keys.get(i).getKeyCode() == 83)&&(players[0].getY() + players[0].getSpeed() < 950)) { //if "S" is pressed
            playerOneDown = true;
            players[0].setDirection("down");
          }
          
          //key pressed to move right 
          if((keys.get(i).getKeyCode() == 68)&&(players[0].getX() + players[0].getSpeed() < 1700)) { //if "D" is pressed
            playerOneRight = true;
            players[0].setDirection("right");
          }
          
          //key pressed to shoot
          if(keys.get(i).getKeyCode() == KeyEvent.VK_SPACE) { //if space is pressed
          }
        }
        
// -------------------- PLAYER TWO CONTROLS ------------------ //      
        
        if(players[1].getLife() == true) {
          //key pressed to move up
          if((keys.get(i).getKeyCode() == 38)&&(players[1].getY() - players[1].getSpeed() > 0)) { //if the down arrow key is pressed
            playerTwoUp = true;
            players[1].setDirection("up");
          }
          
          //key pressed to move left 
          if((keys.get(i).getKeyCode() == 37)&&(players[1].getX() - players[1].getSpeed() > 0)) { //if the right arrow key is pressed
            playerTwoLeft = true;
            players[1].setDirection("left");
          }
          
          //key pressed to move down 
          if((keys.get(i).getKeyCode() == 40)&&(players[1].getY() + players[1].getSpeed() < 950)) { //if the down arrow key is pressed
            playerTwoDown = true;
            players[1].setDirection("down");
          }
          
          //key pressed to move right
          if((keys.get(i).getKeyCode() == 39)&&(players[1].getX() + players[1].getSpeed() < 1700)) { //if the right arrow key is pressed
            playerTwoRight = true;
            players[1].setDirection("right");
          }
          
          //key pressed to shoot
          if(keys.get(i).getKeyCode() == KeyEvent.VK_SLASH) { //if space is pressed
          }
        }
        
        //if escape key is pressed 
        if (keys.get(i).getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
          new EscapeFrame();
          keys.remove(i);
        } 
      }
    }
    
    public void keyReleased(KeyEvent e) {
      
//--------- KEY RELEASE ON PLAYER ONE ---------//
      if(players[0].getLife() == true) {
        //in the event that the e key is pressed, player one will switch to their next weapon, assuming there is one 
        if(e.getKeyCode() == KeyEvent.VK_E) {
          players[0].nextWeapon(players[0].getWeaponSlot(),weaponsUnlocked);
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 69){
              keys.remove(i);
            }
          }
        }
        
        //in the event if the q key is pressed, player one will switch to their previous weapon, assuming there is one
        if(e.getKeyCode() == KeyEvent.VK_Q) {
          players[0].previousWeapon(players[0].getWeaponSlot(),weaponsUnlocked);
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 81){
              keys.remove(i);
            }
          }
        }
        
        if((e.getKeyCode() == KeyEvent.VK_A) && (players[0].getX() > 0)){
          playerOneLeft = false;
          players[0].setDirection("stand left");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 65){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == KeyEvent.VK_S) && (players[0].getY() < 900)){
          playerOneDown = false;
          players[0].setDirection("stand down");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 83){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == KeyEvent.VK_D) && (players[0].getX() < 1700)){
          playerOneRight = false;
          players[0].setDirection("stand right");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 68){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == 87) && (players[0].getY() > 0)) {
          playerOneUp = false;
          players[0].setDirection("stand up");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 87){
              keys.remove(i);
            }
          }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
          playerOneShoot = true;
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 32){
              keys.remove(i);
            }
          }
        }
      }
      
      
      
//--------- KEY RELEASE ON PLAYER TWO --------//
      
      if(players[1].getLife() == true) {
        //in the event if the comma key is pressed, player two will switch to their previous weapon, assuming there is one
        if(e.getKeyCode() == KeyEvent.VK_COMMA) {
          players[1].previousWeapon(players[1].getWeaponSlot(),weaponsUnlocked);
          if(players[1].getWeapon() instanceof Pistol) {
            players[1].setFile("pistol2.png");
          }else if(players[1].getWeapon() instanceof Uzi) {
            players[1].setFile("uzi2.png");
          }else if(players[1].getWeapon() instanceof Shotgun) {
            players[1].setFile("shotgun2.png");
          }else if(players[1].getWeapon() instanceof Railgun) {
            players[1].setFile("railgun2.png");
          }
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 188){
              keys.remove(i);
            }
          }
        }
        
        //in the event that the period key is pressed, player two will switch to their previous weapon, assuming there is one
        if(e.getKeyCode() == KeyEvent.VK_PERIOD) {
          players[1].nextWeapon(players[1].getWeaponSlot(),weaponsUnlocked);
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 190){
              keys.remove(i);
            }
          }
        }
        
        if((e.getKeyCode() == 37) && (players[1].getX() > 20)) {
          playerTwoLeft = false;
          players[1].setDirection("stand left");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 37){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == 40) && (players[1].getY() < 800)) {
          playerTwoDown = false;
          players[1].setDirection("stand down");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 40){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == 39) && (players[1].getX() < 1630)) {
          playerTwoRight = false;
          players[1].setDirection("stand right");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 39){
              keys.remove(i);
            }
          }
        }else if((e.getKeyCode() == 38) && (players[1].getY() > 20)){
          playerTwoUp = false;
          players[1].setDirection("stand up");
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 38){
              keys.remove(i);
            }
          }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SLASH) {
          playerTwoShoot = true;
          
          for (int i=(keys.size()-1); i>=0; i--){
            if (keys.get(i).getKeyCode() == 191){
              keys.remove(i);
            }
          }
        }
      }
    }
  }//end of keyboard listener
  
  //shooting audio for every time player shoots a weapon which corresponds with the current weapon
  public void shoot(String fileName){
    try {
      File audioFile = new File(fileName);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioStream);
      clip.start();
      
    }catch (Exception e) {
      e.printStackTrace();
    }
  }
}