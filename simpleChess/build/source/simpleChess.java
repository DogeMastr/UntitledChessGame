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
  SHIT IM DOING:
    So you cant move a blank space to take a peice
    check for legal moves

  SHIT LEFT TO DO:
    turnsr
    win condition
    menu and gameover screens
*/
ArrayList<Tile> chessBoard;

int moveT1 = -1; //the number of the tiles to be swapped
int moveT2 = -1;

float spacing;

public void setup() {
  
  //fullScreen();
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
      chessBoard.get(i).team = 1;
      chessBoard.get(i).type = 0;
    } else if(i >= 48){
      chessBoard.get(i).team = 0;
      chessBoard.get(i).type = 0;
    } else if(i > 15){
      chessBoard.get(i).team = -1;
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
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).run();
  }
}

int clicked = 0; //how many tiles are clicked at the end of the frame
public void clickPeice(){
/*
  If tile pressed:
    Check how many tiles are pressed
      if 0:
        Check if its a blank space
        if so then stop
        else
        record moveT1
      if 1:
        record moveT2
        movePeice();
*/

  for (int i = 0; i < chessBoard.size(); i++) {
    println(clicked);
    if(chessBoard.get(i).selected){
      switch(clicked){
        case 0:
          if(chessBoard.get(i).type == -1){
            chessBoard.get(i).selected = false;
            break;
          }
          moveT1 = i;
          clicked++;
          break;
        case 1:
          if(moveT1 == i && moveT1 != -1){
            break;
          }
          moveT2 = i;
          clicked++;
          break;
        case 2:
          movePeice();
          clicked = 0;
          break;
      }
    }
  }
}

public void showMoves(){
  //this shows your posible moves by turning the tile a shade of green
  if(clicked == 1){
    //first peice is selected
    for(int i = 0; i < chessBoard.size(); i++){
      if(moveT1 != i){
        switch(chessBoard.get(moveT1).type){
          case 0: //pawn
            break;
          case 1: //rook
          case 2: //knight
          case 3: //bishop
          case 4: //queen
          case 5: //king
        }
      }
    }
  }
}

public void movePeice(){
  //check of they are on different teams
  if(checkLegalMove(moveT1, moveT2)){
    //The peice is moving from one space to another
    chessBoard.get(moveT2).type = chessBoard.get(moveT1).type;
    chessBoard.get(moveT1).type = -1;
    //keeps the same team
    chessBoard.get(moveT2).team = chessBoard.get(moveT1).team;
    chessBoard.get(moveT1).team = -1;
  }
  //no longer clicked
  chessBoard.get(moveT1).selected = false;
  chessBoard.get(moveT2).selected = false;
}

public boolean checkLegalMove(int tile1, int tile2){
  if(chessBoard.get(tile1).team == chessBoard.get(tile2).team){
    return false;
  }
  return true;
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
  int team;

  boolean colour;
  boolean selected;

  Tile(float x, float y) {
    this.x = x;
    this.y = y;

    type = -1; //8 types of peice + 1 for blank space
    tWidth = spacing;
    selected = false;
  }

  public void run() {
    display();
    select();
  }

  public void display() {
    //if colour is true its a white space
    if(colour){
      fill(255);
      if(mouseOver() || selected){
        fill(191);
      }
    } else {
      fill(0);
      if(mouseOver() || selected){
        fill(63);
      }
    }

    rect(x, y, tWidth, tWidth);

    if(team == 1){
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
      selected = !selected;
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
