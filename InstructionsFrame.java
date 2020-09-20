/* 
 * InstructionsFrame.java
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

class InstructionsFrame extends JFrame {
   
  JFrame thisFrame;
  
  InstructionsFrame() {
    
    super("Instructions");
    this.thisFrame = this;
    
    //set screen size 
    this.setSize(1200,720);
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel instructionsPanel = new InstructionsPanel();
    instructionsPanel.setLayout(null);
    
    //adding images
    ImageIcon titleIcon = new ImageIcon("title.png");
    JLabel titlePic = new JLabel(titleIcon);
    ImageIcon barrelIcon = new ImageIcon("barrel.png");
    JLabel barrelPic = new JLabel(barrelIcon);
    ImageIcon backIcon = new ImageIcon("backIcon.png");
    
    //adding JButton to go back to start screen 
    JButton back = new JButton(backIcon);
    back.setBackground(new Color(0,0,0,0));
    back.setBorder(BorderFactory.createEmptyBorder()); 
    back.setRolloverIcon(new ImageIcon("backIconPressed.png")); 
    back.setFocusPainted(false); 
    back.addActionListener(new BackButtonListener());
    
    //add JLabels to state instructions
    JLabel title = new JLabel("iNStruCTionS");
    JLabel firstInstruction = new JLabel("conTROLs");
    JLabel playerOneControls = new JLabel("Player One: WASD to move. 'Q' to swtich to previous weapon, 'E' to switch to next weapon, and space key to shoot");
    JLabel playerTwoControls = new JLabel("Player Two: Arrow keys to move. '.' to swtich to next weapon, ',' to switch to previous weapon and '/' to shoot");
    JLabel secondInstruction = new JLabel("ObjecTIVe");
    JLabel objective = new JLabel("The objective is simple, kill or be killed, the longer you survive and more points you obtain.");
    JLabel objectiveContd = new JLabel("You may even earn a spot on the high score board.");
    JLabel thirdInstruction = new JLabel("eaRNing POinTS");
    JLabel earningPoints = new JLabel("Kill zombies and demons with your weapons which you will unlock as you progress through the levels to obtain points");
    
    //set text font and sizefor each instruction
    title.setFont(new Font("Chalkboard SE", Font.BOLD, 30));
    firstInstruction.setFont(new Font("Chalkboard SE", Font.BOLD,20));
    playerOneControls.setFont(new Font("Chalkboard",Font.PLAIN,16));
    playerTwoControls.setFont(new Font("Chalkboard",Font.PLAIN,16));
    secondInstruction.setFont(new Font("Chalkboard SE",Font.BOLD,20));
    objective.setFont(new Font("Chalkboard",Font.PLAIN,16));
    objectiveContd.setFont(new Font("Chalkboard",Font.PLAIN,16));
    thirdInstruction.setFont(new Font("Chalkboard SE",Font.BOLD,20));
    earningPoints.setFont(new Font("Chalkboard",Font.PLAIN,16));
    
    //add labels to instructionsPanel 
    instructionsPanel.add(title);
    instructionsPanel.add(back);
    instructionsPanel.add(firstInstruction);
    instructionsPanel.add(playerOneControls);
    instructionsPanel.add(playerTwoControls);
    instructionsPanel.add(secondInstruction);
    instructionsPanel.add(objective);
    instructionsPanel.add(objectiveContd);
    instructionsPanel.add(thirdInstruction);
    instructionsPanel.add(earningPoints);
    instructionsPanel.add(titlePic);
    instructionsPanel.add(barrelPic);
    
    //set location of labels 
    back.setLocation(20,10);
    title.setLocation(500,10);
    barrelPic.setLocation(950,10);
    firstInstruction.setLocation(75,100);
    playerOneControls.setLocation(130,135);
    playerTwoControls.setLocation(130,160);
    secondInstruction.setLocation(75,210);
    objective.setLocation(130,245);
    objectiveContd.setLocation(145,270);
    thirdInstruction.setLocation(75,310);
    earningPoints.setLocation(130,345);
    titlePic.setLocation(-100,275);
    
    //set size of labels 
    back.setSize(80,80);
    title.setSize(210,35);
    barrelPic.setSize(200,250);
    firstInstruction.setSize(125,25);
    playerOneControls.setSize(1000,25);
    playerTwoControls.setSize(1000,25);
    secondInstruction.setSize(125,25);
    objective.setSize(1000,25);
    objectiveContd.setSize(500,25);
    thirdInstruction.setSize(175,25);
    earningPoints.setSize(1000,25);
    titlePic.setSize(1440,500);

    //add the main panel to the frame
    this.add(instructionsPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //add panel to frame for refreshing images 
  class InstructionsPanel extends JPanel {
    
    public void paintComponent(Graphics g){     
      super.paintComponent(g); //required
      repaint();
    }
  }
  
  //action listener when back button is clicked
  class BackButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent event) { 
      thisFrame.dispose();//disposes current screen
      new StartingFrame();//opens starting screen
    }
  }
  
}
  
    
    
    
    
    
    