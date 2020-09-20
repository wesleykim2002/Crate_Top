/* 
 * EndGameScreen.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/09/2019
 * Window which contains high scores 
 */

//Imports
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.BorderFactory; 
import java.awt.*;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

class EndGameFrame extends JFrame {
   
  JFrame thisFrame;
  JTextField name;
  int playerScore;
  
  EndGameFrame(int gameScore) {
    
    super("");
    this.thisFrame = this;
    
    //set screen size 
    this.setSize(720,480);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable (false);
    
    this.playerScore = gameScore;
    
    //create panel
    JPanel endGamePanel = new EndGamePanel();
    endGamePanel.setLayout(null);
    
   //adding JLabel to display final score
    String stringScore = ""+gameScore;
    JLabel finalScore = new JLabel("FINAL SCORE: " + stringScore);
    finalScore.setFont(new Font("Chalkboard SE",Font.BOLD,62));
    finalScore.setLocation(0,100);
    finalScore.setSize(10000,80);
    
    //adding JTextLabel 
    name = new JTextField("Enter your name");
    name.setLocation(0,200);
    name.setSize(720,50);
    
    //adding button to sumbit name
    ImageIcon submitPic = new ImageIcon("submitButton.png");
    
    JButton submit = new JButton(submitPic);
    submit.setBackground(new Color(0,0,0,0));
    submit.setRolloverIcon(new ImageIcon("submitButtonPressed.png"));
    submit.setBorder(BorderFactory.createEmptyBorder());
    submit.setFocusPainted(false);
    submit.addActionListener(new SubmitButtonListener());
    submit.setBounds(200,250,300,100);
    
    //JButton to quit
    ImageIcon quitIcon = new ImageIcon("quitButton.png");
    
    JButton quitButton = new JButton(quitIcon);
    quitButton.setBackground(new Color(0,0,0,0));
    quitButton.setRolloverIcon(new ImageIcon("quitButtonPressed.png"));
    quitButton.setBorder(BorderFactory.createEmptyBorder());
    quitButton.setFocusPainted(false);
    quitButton.addActionListener(new QuitButtonListener());
    quitButton.setBounds(300,350,100,100);
    
    //adding JLabels and JButtons to panel 
    endGamePanel.add(finalScore);
    endGamePanel.add(name);
    endGamePanel.add(submit);
    endGamePanel.add(quitButton);
    
    //add the high scores panel to the frame
    this.add(endGamePanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  class SubmitButtonListener implements ActionListener {  //this is the required class definition
    public  void actionPerformed(ActionEvent event) {     //this is the only method in this class and it will run automatically when the button is activated
    
    // This is where the code to respond to the button event goes
    String userName;
    
    userName = name.getText(); //get the text from the field and store it in a vairable
    try{
      storeScore(userName,playerScore);
    }catch(Exception e) {
      e.printStackTrace();
    }
    
    System.exit(0);//after they press submit the program will exit
    
    }
    
    //store score to file 
    public  void storeScore(String latestPlayer,int latestScore) throws Exception{
      File highScores = new File("highScores.txt");
      PrintWriter writer = new PrintWriter(highScores);
      Scanner input = new Scanner(highScores);
      String[] userNames = new String[3];
      int[] scores = new int[3];
      
      int counter = 0;
      int counterU = 0;
      int counterS = 0;
      while(input.hasNext()) {
        if (counter%2 == 1){
          userNames[counterU] = input.nextLine();
          counterU++;
        }else{
          scores[counterS] = input.nextInt();
          counterS++;
        }
        counter++;
      }
      
      //comparing score of latest player to players in file
      int start, item;
      String startU, itemU;
      for (int i=0; i<3; i++){
        for (int j=0; j<3; j++){
          start = scores[i];
          startU = userNames[i];
          item = scores[j];
          itemU = userNames[j];
          if (item<start){
            scores[i] = item;
            userNames[i] = itemU;
            scores[j] = start;
            userNames[j] = startU;
          }
        }
      }
      
      scores[2] = latestScore;
      userNames[2] = latestPlayer;
      
      for (int i=0; i<3; i++){
        for (int j=0; j<3; j++){
          start = scores[i];
          startU = userNames[i];
          item = scores[j];
          itemU = userNames[j];
          if (item<start){
            scores[i] = item;
            userNames[i] = itemU;
            scores[j] = start;
            userNames[j] = startU;
          }
        }
      }
      
      //writing arrays in file after compared
      for(int i = 0;i < userNames.length;i++) {
        writer.println(userNames[i]);
        writer.println(scores[i]);
      }
        
      input.close();
      writer.close();
    }
    
  } //end of submit button
  
  class QuitButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event)  {
      System.exit(0);
    }
    
  }
    
  class EndGamePanel extends JPanel {
    
    public void paintComponent(Graphics g){
      super.paintComponent(g); //required
      repaint();
    }
  }
  
}