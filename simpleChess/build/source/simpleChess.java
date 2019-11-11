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
ArrayList chessBoard;

public void setup(){
	

	chessBoard = new ArrayList<tile>();

	for (int i = 0; i < 8; i++){
		for (int j = 0; j < 8; j++){
			chessBoard.add(new tile(i*50,j*50,true));
		}
	}
}

public void draw(){
	for(int i = 0; i <= chessBoard.size(); i++){
		// chessBoard.get(3).run();
		println(i);
		chessBoard.get(i).run();
	}
}

class tile{
	float x;
	float y;
	float tWidth;
	float type;
	boolean colour;

	tile(float x, float y, boolean colour){
		type = 0;
	}

	public void run(){
		println("wtf");
	}
	public void display(){
		if(colour){
			fill(0);
		} else {
			fill(255);
		}
		rect(x,y,tWidth,tWidth);

		if(type != 0){
			text(type,x,y);
		}
	}
}
  public void settings() { 	size(900,900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "simpleChess" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
