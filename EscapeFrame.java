/*
 * EscapeFrame.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/15/2019
 * Frame when user presses escape to ask if they wish to quit
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

class EscapeFrame extends JFrame {
  
  JFrame escapeFrame; 
  
  EscapeFrame() {
    super("");
    
    this.escapeFrame = this;
    
    //set screen size 
    this.setSize(400,300);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable (false);
  
    //create panel 
    JPanel escapePanel = new EscapePanel();
    escapePanel.setLayout(null);
    
    //JLabel asking if they wish to quit 
    JLabel confirm = new JLabel("Are you sure you wish");
    JLabel confirm2 = new JLabel("you wish to exit?");
    
    confirm.setFont(new Font("Chalkboard",Font.PLAIN, 20));
    confirm2.setFont(new Font("Chalkboard",Font.PLAIN, 20));
    
    //add JButtons whether they want to continue playing or quit
    ImageIcon resumeIcon = new ImageIcon("continueButton.png");
    
    JButton resume = new JButton(resumeIcon);
    resume.setBackground(new Color(0,0,0,0));
    resume.setBorder(BorderFactory.createEmptyBorder()); 
    resume.setRolloverIcon(new ImageIcon("continueButtonPressed.png")); 
    resume.setFocusPainted(false); 
    resume.addActionListener(new ResumeButtonListener());
    
    ImageIcon quitIcon = new ImageIcon("escapeButton.png");
    
    JButton escape = new JButton(quitIcon);
    escape.setBackground(new Color(0,0,0,0));
    escape.setBorder(BorderFactory.createEmptyBorder()); 
    escape.setRolloverIcon(new ImageIcon("escapeButtonPressed.png")); 
    escape.setFocusPainted(false); 
    escape.addActionListener(new EscapeButtonListener());
    
    //add buttons and labels to escapePanel 
    escapePanel.add(confirm);
    escapePanel.add(confirm2);
    escapePanel.add(resume);
    escapePanel.add(escape);
    
    //set location and size for JButtons and JLabels 
    confirm.setLocation(100,75);
    confirm.setSize(300,35);
    confirm2.setLocation(120,105);
    confirm2.setSize(300,35);
    
    resume.setLocation(30,200);
    resume.setSize(150,35);
    escape.setLocation(210,200);
    escape.setSize(150,35);
    
    //add the main panel to the frame
    this.add(escapePanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //inner class to refresh escapePanel
  class EscapePanel extends JPanel {
    
    public void paintComponent(Graphics g){     
      super.paintComponent(g); //required
      repaint();
    }
  }
  
  //inner class to detect when the resume button is clicked
  class ResumeButtonListener implements ActionListener { 
    public void actionPerformed(ActionEvent event) { 
      escapeFrame.dispose();//disposes current screen and resumes game
    }
  }
  
  //inner class to detect when quit button is clicked 
  class EscapeButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) { 
      System.exit(0);
    }
  }
  
}
    
  
  
  
  