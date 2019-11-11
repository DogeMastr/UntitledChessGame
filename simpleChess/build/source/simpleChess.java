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
ArrayList<Tile> chessBoard;

int moveT1; //the number of the tiles to be swapped
int moveT2;

public void setup() {
  
  rectMode(CORNER);
  textAlign(LEFT,TOP);
  textSize(width/10);

  chessBoard = new ArrayList<Tile>();

  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      for (int k = 0; k < 2; k++){
        if(k == 1){
          chessBoard.add(new Tile(i*width/10 + width/10, j*width/10 + width/10, true, (int)random(0,9)));
        } else {
          chessBoard.add(new Tile(i*width/10 + width/10, j*width/10 + width/10, false, (int)random(0,9)));
        }
      }
    }
  }
}

public void draw() {
  background(67,70,82);
  // println(chessBoard.get(0).clicked);
  int clicked = 0; //how many tiles are clicked at the end of the frame
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).run();

    /*
    if 2 peices clicked = true, move()
    */
    if(chessBoard.get(i).clicked){
      switch(clicked){
        case 0:
          moveT1 = i;
          clicked++;
          break;
        case 1:
          moveT2 = i;
          movePeice();
          clicked = 0;
          break;
      }
    }
  }
}


public void movePeice(){
  //The peice is moving from one space to another
  chessBoard.get(moveT2).type = chessBoard.get(moveT1).type;
  chessBoard.get(moveT1).type = 0;
  println(chessBoard.get(moveT2).type);
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

  Tile(float x, float y, boolean _colour, int type) {
    this.type = type; //8 types of peice + 1 for blank space
    this.x = x;
    this.y = y;
    this.colour = _colour;
    tWidth = 80;

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
    } else {
      fill(0);
    }

    rect(x, y, tWidth, tWidth);

    fill(0,255,0);
    if (type != 0) {
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
    if(mouseOver() && bMousePressed() && clicked == false){
      clicked = true;
      println("ok");
    }
    if(mouseOver() && bMousePressed() && clicked == true){
      clicked = false;
      println("not ok");
    }
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "simpleChess" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
