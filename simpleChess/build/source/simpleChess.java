import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class simpleChess extends PApplet {

//basic chess no fancy shit ok cool
/*
  SHIT TO FIX:

  SHIT TO DO:
    check for legal moves
    turnsr
    win condition
    menu and gameover screens
*/
ArrayList<Tile> chessBoard;

int moveT1; //the number of the tiles to be swapped
int moveT2;

float spacing;

public void setup() {
  
  if(width <= height){
    spacing = width/10;
  } else {
    spacing = height/10;
  }
  rectMode(CORNER);
  textAlign(LEFT,TOP);
  textSize(spacing);

  chessBoard = new ArrayList<Tile>();

  initBoard();
}

public void initBoard(){
  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
        chessBoard.add(new Tile(j*spacing + spacing, i*spacing + spacing));
    }
  }

  boolean previous = false;
  for(int i = 0; i < chessBoard.size(); i++){
    chessBoard.get(i).colour = previous;
    if((i+1)%8 != 0){
      previous = !previous;
    }

    if(i <= 15){
      chessBoard.get(i).team = true;
      chessBoard.get(i).type = 0;
    } else if(i >= 48){
      chessBoard.get(i).team = false;
      chessBoard.get(i).type = 0;
    }

    chessBoard.get(0).type = 1;
    chessBoard.get(1).type = 2;
    chessBoard.get(2).type = 3;
    chessBoard.get(3).type = 5;
    chessBoard.get(4).type = 4;
    chessBoard.get(5).type = 3;
    chessBoard.get(6).type = 2;
    chessBoard.get(7).type = 1;

    chessBoard.get(56).type = 1;
    chessBoard.get(57).type = 2;
    chessBoard.get(58).type = 3;
    chessBoard.get(59).type = 4;
    chessBoard.get(60).type = 5;
    chessBoard.get(61).type = 3;
    chessBoard.get(62).type = 2;
    chessBoard.get(63).type = 1;

  }
}

public void draw() {
  background(67,70,82);
  clickPeice();
}

int clicked = 0; //how many tiles are clicked at the end of the frame
public void clickPeice(){
  if(!chessBoard.get(moveT1).clicked){
    clicked = 0;
  }
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).run();

    /*
    if 2 peices clicked = true, move()
    */
    if(chessBoard.get(i).clicked){
      if(clicked == 0){
        moveT1 = i;
        clicked++;
      } else if(moveT1 != i) {
        moveT2 = i;
        movePeice();
        clicked = 0;
      }
    }
  }
}

public void movePeice(){
  //The peice is moving from one space to another
  chessBoard.get(moveT2).type = chessBoard.get(moveT1).type;
  chessBoard.get(moveT1).type = -1;

  //keeps the same team
  chessBoard.get(moveT2).team = chessBoard.get(moveT1).team;

  //no longer clicked
  chessBoard.get(moveT1).clicked = false;
  chessBoard.get(moveT2).clicked = false;
}

boolean mouseHeld = false;
public boolean bMousePressed(){
	//b for better
	//is true for one frame when mouse is pressed
	if(mousePressed & mouseHeld == false){
		mouseHeld = true;
		return true;
	}
	if(!mousePressed){
		mouseHeld = false;
	}
	return false;
}
class Tile {
  float x;
  float y;
  float tWidth;
  int type;
  boolean team;

  boolean colour;
  boolean clicked;

  Tile(float x, float y) {
    this.x = x;
    this.y = y;

    type = -1; //8 types of peice + 1 for blank space
    tWidth = spacing;
    clicked = false;
  }

  public void run() {
    display();
    select();
  }

  public void display() {
    //if colour is true its a white space
    if(colour){
      fill(255);
      if(mouseOver() || clicked){
        fill(191);
      }
    } else {
      fill(0);
      if(mouseOver() || clicked){
        fill(63);
      }
    }

    rect(x, y, tWidth, tWidth);

    if(team){
      fill(0,255,0);
    } else {
      fill(255,0,0);
    }

    if(type != -1){
      text(type, x, y);
    }

  }

  public boolean mouseOver(){
    if(mouseX > x && mouseX < x + tWidth){
      if(mouseY > y && mouseY < y + tWidth){
        return true;
      }
    }
    return false;
  }

  public void select(){
    if(mouseOver() && bMousePressed()){
      clicked = !clicked;
    }
  }
}
  public void settings() {  size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "simpleChess" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
