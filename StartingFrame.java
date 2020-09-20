/*
 * StartMenu.java
 * Version 1.0
 * Jason Tan & Wesley KIm 
 * 01/10/2019
 * Starting menu GUI 
 */


//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory; 

import java.awt.*;

class StartingFrame extends JFrame { 

  JFrame thisFrame;
  
  //Constructor - this runs first
  StartingFrame() { 
    super("");
    this.thisFrame = this; 
    
    //configure the window
    this.setSize(600,600);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable(false);
    
    //create frame without boarder and background colour 
    this.setUndecorated(true);
    setBackground(new Color(255,255,255));
    
    //create panel to store starting screen information and jbuttons
    JPanel mainPanel = new MainPanel();
    mainPanel.setLayout(null);
    
    //import title
    ImageIcon titlePic = new ImageIcon("title.png");
    JLabel title = new JLabel(titlePic);
    
    //Create a JButton to start game 
    ImageIcon startIcon = new ImageIcon("startButton.png");
    
    JButton startButton = new JButton(startIcon);
    startButton.setBackground(new Color(0,0,0,0));
    startButton.setRolloverIcon(new ImageIcon("startButtonPressed.png")); 
    startButton.setBorder(BorderFactory.createEmptyBorder()); 
    startButton.setFocusPainted(false); 
    startButton.addActionListener(new StartButtonListener());
    
    //create JButton to open file with high scores 
    ImageIcon highScoreIcon = new ImageIcon("highScoreButton.png");
    
    JButton highScores = new JButton(highScoreIcon);
    highScores.setBackground(new Color(0,0,0,0));
    highScores.setRolloverIcon(new ImageIcon("highScoreButtonPressed.png")); 
    highScores.setBorder(BorderFactory.createEmptyBorder()); 
    highScores.setFocusPainted(false); 
    highScores.addActionListener(new HighScoreButtonListener());
    
    //create JButton to open instructions 
    ImageIcon instructionsIcon = new ImageIcon("instructionsButton.png");
    
    JButton instructionsButton = new JButton(instructionsIcon);
    instructionsButton.setBackground(new Color(0,0,0,0));
    instructionsButton.setRolloverIcon(new ImageIcon("instructionsButtonPressed.png")); 
    instructionsButton.setBorder(BorderFactory.createEmptyBorder()); 
    instructionsButton.setFocusPainted(false); 
    instructionsButton.addActionListener(new InstructionsButtonListener());
    
    //JButton to quit 
    ImageIcon quitIcon = new ImageIcon("quitButton.png");
    
    JButton quitButton = new JButton(quitIcon);
    quitButton.setBackground(new Color(0,0,0,0));
    quitButton.setRolloverIcon(new ImageIcon("quitButtonPressed.png")); 
    quitButton.setBorder(BorderFactory.createEmptyBorder()); 
    quitButton.setFocusPainted(false); 
    quitButton.addActionListener(new QuitButtonListener());
    
    //add buttons to JPanel 
    title.setBounds(-410,-150,1440,550);
    startButton.setBounds(200,240,200,60);
    instructionsButton.setBounds(150,300,300,60);
    highScores.setBounds(150,360,300,60);
    quitButton.setBounds(200,420,200,60);
    mainPanel.add(title);
    mainPanel.add(startButton);
    mainPanel.add(instructionsButton);
    mainPanel.add(highScores);
    mainPanel.add(quitButton);
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  class MainPanel extends JPanel {
    
    public void paintComponent(Graphics g){     
      super.paintComponent(g); //required
      repaint();
    }
  }
  
  //inner class used to detect button press to open high score file 
  class HighScoreButtonListener implements ActionListener { 
    public void actionPerformed(ActionEvent event) {
      thisFrame.dispose();
      new HighScoreFrame(); //opens new window with high scores 
    }
  }
  
  //inner class used to detect when start button is pressed
  class StartButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      thisFrame.dispose();
      new GameFrame(); //opens game screen
    }
    
  }
  
  //inner class used to detect when instructions button is pressed
  class InstructionsButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  { 
      thisFrame.dispose();
      new InstructionsFrame(); //create a new instructions game frame
      
    }
    
  }
  
  //inner class used to detect when quit button is pressed
  class QuitButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.exit(0);
      
    }
    
  }
  
  //Main method starts this application
  public static void main(String[] args) { 
    new StartingFrame();

  }
  
}
