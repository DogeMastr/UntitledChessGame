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
    Displaying the right colours

  SHIT TO DO:
    Set up the board so the peices start in the right positions
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
      println("tru");
    } else {
      println("fal");
    }

    if(i <= 7){
      chessBoard.get(i).type = i;
    } else if(i <= 15){
      chessBoard.get(i).type = 8;
    } else if(i >= 48){
      chessBoard.get(i).type = 8;
    }
  }
}

int clicked = 0; //how many tiles are clicked at the end of the frame
public void draw() {
  background(67,70,82);
  // println(chessBoard.get(0).clicked);
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
  boolean colour;

  boolean clicked;

  Tile(float x, float y) {
    this.type = -1; //8 types of peice + 1 for blank space
    this.x = x;
    this.y = y;
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

    fill(0,255,0);
    text(type, x, y);

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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "simpleChess" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
