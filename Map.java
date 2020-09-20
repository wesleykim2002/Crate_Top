/*
 * Map.java
 * Version 1.0
 * Jason Tan & Wesley Kim 
 * 01/07/2019
 * Loads all of the maps from the text files and makes them accessible from the main class
 */

//imports 
import java.util.Scanner;
import java.io.File;

class Map{
  private String [][]map;
  private Player playerOne;
  private Player playerTwo;
  
  Map(){
    
    //create player objects
    this.playerOne = new Player(100,700,500,10,"pistol1.png");
    this.playerTwo = new Player(100,800,500,10,"pistol2.png");
    
    //call method to load in map txt file 
    try {
    map = boardLoad("room 1.txt");
    }catch(Exception e) { e.printStackTrace();}
  }
  
  public String[][] getMap(){
    return map;
  }
  
  //getters 
  public Player getPlayer(int playerNum) {
    if(playerNum == 1) {
      return this.playerOne;
    }else{
      return this.playerTwo;
    }
  }
  
  //method to load board into a 2D string array
  public static String[][] boardLoad(String name) throws Exception{
    int row,column;
    File myFile = new File(name);
    Scanner input = new Scanner(myFile);
    row = input.nextInt();
    column = input.nextInt();
    String[][]board = new String[row][column];
    for (int i=0; i<row; i++){
      String gameLine = input.next();
      for (int j=0; j<gameLine.length(); j++){
        board[i][j] = gameLine.substring(j,j+1);
      }
    }
    input.close();
    return board;
  }
}