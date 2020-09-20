/* 
 * HighScoreFrame.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/09/2019
 * Window which contains high scores 
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
import java.awt.Font;
import javax.swing.BorderFactory; 
import java.awt.*;

class HighScoreFrame extends JFrame {
   
  JFrame thisFrame;
  
  HighScoreFrame() {
    
    super("High Scores");
    this.thisFrame = this;
    
    //set screen size 
    this.setSize(1200,720);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    //create panel
    JPanel highScoresPanel = new HighScoresPanel();
    highScoresPanel.setLayout(null);
    
    //create ImageIcons
    ImageIcon backIcon = new ImageIcon("backIcon.png");
    
    //add JLabel for title 
    JLabel title = new JLabel("hIGH ScOreS");
    title.setFont(new Font("Chalkboard SE",Font.BOLD,30));
    title.setSize(300,35);
    title.setLocation(500,20);
    
    //create JButton to return to start screen
    JButton back = new JButton(backIcon);
    back.setBackground(new Color(0,0,0,0));
    back.setBorder(BorderFactory.createEmptyBorder()); 
    back.setRolloverIcon(new ImageIcon("backIconPressed.png")); 
    back.setFocusPainted(false); 
    back.addActionListener(new BackButtonListener());
    
    //adding JButtons and JLabels to highScoresPanel
    highScoresPanel.add(back);
    highScoresPanel.add(title);
    
    //setting location for back button 
    back.setLocation(20,10);
    
    //setting size of JButtons 
    back.setSize(80,80);
    
    //add the high scores panel to the frame
    this.add(highScoresPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  class HighScoresPanel extends JPanel {
    
    public void paintComponent(Graphics g){     
      super.paintComponent(g); //required
      repaint();
    }
  }
  
  class BackButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event) { 
      thisFrame.dispose();//disposes current screen
      new StartingFrame();//opens starting screen
    }
  }
  
}
  
    
    
    
    
    
    