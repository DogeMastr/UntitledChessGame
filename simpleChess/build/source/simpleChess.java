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
 Images

 SHIT LEFT TO DO:
  check
 */

ImageDB imageDB;

ArrayList<Tile> chessBoard;

int moveT1 = -1; //the number of the tiles to be swapped
int moveT2 = -1;
int clicked = 0; //how many tiles are clicked at the end of the frame

float spacing;
boolean mouseHeld = false;

boolean turn; //whose turn is it? true = white/green
boolean finished; //if the game has finished
boolean menuOpen; //if a menu is open

boolean blockingN = false; //blocking booleans for not
boolean blockingS = false; //highlighting tiles that should be blocked
boolean blockingE = false;
boolean blockingW = false;
boolean blockingNW = false;
boolean blockingNE = false;
boolean blockingSE = false;
boolean blockingSW = false;

public void setup() {
  
  //fullScreen();
  if (width <= height) {
    spacing = width/10;
  } else {
    spacing = height/10;
  }
  rectMode(CORNER);
  textAlign(LEFT, TOP);
  textSize(spacing);

  imageDB = new ImageDB();

  chessBoard = new ArrayList<Tile>();

  initBoard();

  turn = true;
  finished = false;
  menuOpen = false;
}

public void initBoard() {
  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      chessBoard.add(new Tile(j*spacing + spacing, i*spacing + spacing));
    }
  }

  boolean previous = true;
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).colour = previous;
    if ((i+1)%8 != 0) {
      previous = !previous;
    }

    if (i <= 15) {
      chessBoard.get(i).team = 1;
      chessBoard.get(i).type = 0;
    } else if (i >= 48) {
      chessBoard.get(i).team = 0;
      chessBoard.get(i).type = 0;
    } else if (i > 15) {
      chessBoard.get(i).team = -1;
    }

    chessBoard.get(0).type = 1;
    chessBoard.get(1).type = 2;
    chessBoard.get(2).type = 3;
    chessBoard.get(3).type = 4;
    chessBoard.get(4).type = 5;
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
  background(67, 70, 82);
  if(turn){
    background(255,0,0);
  } else {
    background(0,255,0);
  }
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).run();
  }
  clickPeice();
  showMoves();
  checkAndPromotion();

  if(finished){
    victoryMenu(moveT2);
  }
}

public void clickPeice() {
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
    if (chessBoard.get(i).selected) {
      switch(clicked) {
      case 0:
        if (chessBoard.get(i).type == -1) {
          chessBoard.get(i).selected = false;
          break;
        }
        moveT1 = i;
        clicked++;
        break;
      case 1:
        if (moveT1 == i && moveT1 != -1) {
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

public void showMoves() {
  //this shows your posible moves by turning the tile a shade of blue
  if (moveT1 != -1 && chessBoard.get(moveT1).selected) {
    //first peice is selected

    //this works no touch
    for (int i = 0; i < chessBoard.size(); i++) {
      if (moveT1 != i) {
        switch(chessBoard.get(moveT1).type) {
        case 0: //pawn
          /*
              if x = tile.x
           if team green
           if y = tile.y + 1
           true
           if team red
           if y = tile.y - 1
           true

           wowow
           sudo
           so for the starting position for pawns, you want to make a new boolean called awoken = false.

           */
          if (chessBoard.get(i).x == chessBoard.get(moveT1).x) {
            if (chessBoard.get(moveT1).team == 1) {
              if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(moveT1).tWidth) {
                if (chessBoard.get(i).type == -1) {
                  chessBoard.get(i).highlighted = true;
                }
              } else if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(moveT1).tWidth*2 && chessBoard.get(moveT1).awoken == false) {
                if (chessBoard.get(i).type == -1) {
                  chessBoard.get(i).highlighted = true;
                }
              }
            } else {
              if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(moveT1).tWidth) {
                if (chessBoard.get(i).type == -1) {
                  chessBoard.get(i).highlighted = true;
                }
              } else if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(moveT1).tWidth*2 && chessBoard.get(moveT1).awoken == false) {
                if (chessBoard.get(i).type == -1) {
                  chessBoard.get(i).highlighted = true;
                }
              }
            }
          }
          //for taking peices diagonally
          if (chessBoard.get(moveT1).team == 1) {
            if (chessBoard.get(i).type != -1 && chessBoard.get(i).team != 1) {
              if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(moveT1).tWidth) {
                if (chessBoard.get(i).x == chessBoard.get(moveT1).x + chessBoard.get(moveT1).tWidth) {
                  chessBoard.get(i).highlighted = true;
                }
                if (chessBoard.get(i).x == chessBoard.get(moveT1).x - chessBoard.get(moveT1).tWidth) {
                  chessBoard.get(i).highlighted = true;
                }
              }
            }
          } else if (chessBoard.get(i).type != -1 && chessBoard.get(i).team != 0) {
            if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(moveT1).tWidth) {
              if (chessBoard.get(i).x == chessBoard.get(moveT1).x + chessBoard.get(moveT1).tWidth) {
                chessBoard.get(i).highlighted = true;
              }
              if (chessBoard.get(i).x == chessBoard.get(moveT1).x - chessBoard.get(moveT1).tWidth) {
                chessBoard.get(i).highlighted = true;
              }
            }
          }
          break;
        case 2: //knight
          if (chessBoard.get(i).team == chessBoard.get(moveT1).team) {
            break;
          }
          if (chessBoard.get(i).x == chessBoard.get(moveT1).x + (chessBoard.get(i).tWidth)*2) {
            if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            } else if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          if (chessBoard.get(i).x == chessBoard.get(moveT1).x - (chessBoard.get(i).tWidth)*2) {
            if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            } else if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          if (chessBoard.get(i).y == chessBoard.get(moveT1).y + (chessBoard.get(i).tWidth)*2) {
            if (chessBoard.get(i).x == chessBoard.get(moveT1).x + chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            } else if (chessBoard.get(i).x == chessBoard.get(moveT1).x - chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          if (chessBoard.get(i).y == chessBoard.get(moveT1).y - (chessBoard.get(i).tWidth)*2) {
            if (chessBoard.get(i).x == chessBoard.get(moveT1).x + chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            } else if (chessBoard.get(i).x == chessBoard.get(moveT1).x - chessBoard.get(i).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          break;
        case 5: //king
          if (chessBoard.get(i).team == chessBoard.get(moveT1).team) {
            break;
          }
          if (chessBoard.get(i).y == chessBoard.get(moveT1).y - chessBoard.get(moveT1).tWidth) {
            if (chessBoard.get(i).x >= chessBoard.get(moveT1).x - chessBoard.get(moveT1).tWidth
              && chessBoard.get(i).x <= chessBoard.get(moveT1).x + chessBoard.get(moveT1).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          if (chessBoard.get(i).y == chessBoard.get(moveT1).y + chessBoard.get(moveT1).tWidth) {
            if (chessBoard.get(i).x >= chessBoard.get(moveT1).x - chessBoard.get(moveT1).tWidth
              && chessBoard.get(i).x <= chessBoard.get(moveT1).x + chessBoard.get(moveT1).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          if (chessBoard.get(i).y == chessBoard.get(moveT1).y) {
            if (chessBoard.get(i).x == chessBoard.get(moveT1).x - chessBoard.get(moveT1).tWidth
              || chessBoard.get(i).x == chessBoard.get(moveT1).x + chessBoard.get(moveT1).tWidth) {
              chessBoard.get(i).highlighted = true;
            }
          }
          break;
        }
      }
    }

    //fuck shit
    for (int i = moveT1; i < chessBoard.size(); i++) {
      if (moveT1 != i) {
        switch(chessBoard.get(moveT1).type) {
          case 4: //queen (is before the others so it can just copy them)
          case 1: //rook
          	if (chessBoard.get(i).x == chessBoard.get(moveT1).x) {
              if(chessBoard.get(i).team == chessBoard.get(moveT1).team){
                blockingS = true;
              } else if (chessBoard.get(i).team == -1){
                if(!blockingS){
                  chessBoard.get(i).highlighted = true;
                }
              } else {
                if(!blockingS){
                  chessBoard.get(i).highlighted = true;
                  blockingS = true;
                }
              }
          	} else if (chessBoard.get(i).y == chessBoard.get(moveT1).y) {
              if(chessBoard.get(i).team == chessBoard.get(moveT1).team){
                blockingE = true;
              } else if (chessBoard.get(i).team == -1){
                if(!blockingE){
                  chessBoard.get(i).highlighted = true;
                }
              } else {
                if(!blockingE){
                  chessBoard.get(i).highlighted = true;
                  blockingE = true;
                }
              }
          	}
          	if (chessBoard.get(moveT1).type == 1) { //allows the queen to copy the bishop without extra code
          		break;
          	}
          case 3: //bishop
          	/*
          			for every peice to the right
          	 check for x = piece.x & y = i*height
          	 */
          	for (int j = 0; j < 8; j++) {
          		if (chessBoard.get(i).y == chessBoard.get(moveT1).y + j*chessBoard.get(0).tWidth) {
          			if (chessBoard.get(i).x == chessBoard.get(moveT1).x + j*chessBoard.get(0).tWidth) {

                  if(chessBoard.get(moveT1).team == chessBoard.get(i).team){
                    blockingSE = true;
                  }
                  if(chessBoard.get(i).team == -1){
                    if(!blockingSE){
                      chessBoard.get(i).highlighted = true;
                    }
                  } else if(chessBoard.get(i).team != chessBoard.get(moveT1).team){
                    if(!blockingSE){
                      chessBoard.get(i).highlighted = true;
                      blockingSE = true;
                    }
                  }


          			}
          			if (chessBoard.get(i).x == chessBoard.get(moveT1).x - j*chessBoard.get(0).tWidth) {
                  if(chessBoard.get(moveT1).team == chessBoard.get(i).team){
                    blockingSW = true;
                  }
                  if(chessBoard.get(i).team == -1){
                    if(!blockingSW){
                      chessBoard.get(i).highlighted = true;
                    }
                  } else if(chessBoard.get(i).team != chessBoard.get(moveT1).team){
                    if(!blockingSW){
                      chessBoard.get(i).highlighted = true;
                      blockingSW = true;
                    }
                  }
          			}
          		}
          	}
          	break;
        }
      }
    }
    for (int i = moveT1; i >= 0; i--) {
      if (moveT1 != i) {
        switch(chessBoard.get(moveT1).type) {
          case 4: //queen (is before the others so it can just copy them)
          case 1: //rook
            if (chessBoard.get(i).x == chessBoard.get(moveT1).x) {
              if(chessBoard.get(i).team == chessBoard.get(moveT1).team){
                blockingN = true;
              } else if (chessBoard.get(i).team == -1){
                if(!blockingN){
                  chessBoard.get(i).highlighted = true;
                }
              } else {
                if(!blockingN){
                  chessBoard.get(i).highlighted = true;
                  blockingN = true;
                }
              }
            } else if (chessBoard.get(i).y == chessBoard.get(moveT1).y) {
              if(chessBoard.get(i).team == chessBoard.get(moveT1).team){
                blockingW = true;
              } else if (chessBoard.get(i).team == -1){
                if(!blockingW){
                  chessBoard.get(i).highlighted = true;
                }
              } else {
                if(!blockingW){
                  chessBoard.get(i).highlighted = true;
                  blockingW = true;
                }
              }
            }
            if (chessBoard.get(moveT1).type == 1) { //allows the queen to copy the bishop without extra code
              break;
            }
          case 3: //bishop
            /*
                for every peice to the right
             check for x = piece.x & y = i*height
             */
            for (int j = 0; j < 8; j++) {
              if (chessBoard.get(i).y == chessBoard.get(moveT1).y - j*chessBoard.get(0).tWidth) {
                if (chessBoard.get(i).x == chessBoard.get(moveT1).x + j*chessBoard.get(0).tWidth) {
                  if(chessBoard.get(moveT1).team == chessBoard.get(i).team){
                    blockingNE = true;
                  }
                  if(chessBoard.get(i).team == -1){
                    if(!blockingNE){
                      chessBoard.get(i).highlighted = true;
                    }
                  } else if(chessBoard.get(i).team != chessBoard.get(moveT1).team){
                    if(!blockingNE){
                      chessBoard.get(i).highlighted = true;
                      blockingNE = true;
                    }
                  }
                }
                if (chessBoard.get(i).x == chessBoard.get(moveT1).x - j*chessBoard.get(0).tWidth) {
                  if(chessBoard.get(moveT1).team == chessBoard.get(i).team){
                    blockingNW = true;
                  }
                  if(chessBoard.get(i).team == -1){
                    if(!blockingNW){
                      chessBoard.get(i).highlighted = true;
                    }
                  } else if(chessBoard.get(i).team != chessBoard.get(moveT1).team){
                    if(!blockingNW){
                      chessBoard.get(i).highlighted = true;
                      blockingNW = true;
                    }
                  }
                }
              }
            }
            break;
        }
      }
    }
  } else {
    for (int i = 0; i < chessBoard.size(); i++) {
      chessBoard.get(i).highlighted = false;
    }
    blockingN = false;
    blockingS = false;
    blockingE = false;
    blockingW = false;
    blockingNW = false;
    blockingNE = false;
    blockingSW = false;
    blockingSE = false;
  }
}

public void movePeice() {
  //check of they are on different teams
  if (checkLegalMove(moveT1, moveT2)) {
    //checks to see if it was a king and winning the game
    if(chessBoard.get(moveT2).type == 5){
      //the king was just taken
      finished = true;
    }
    //The peice is moving from one space to another
    chessBoard.get(moveT2).type = chessBoard.get(moveT1).type;
    chessBoard.get(moveT1).type = -1;
    //keeps the same team
    chessBoard.get(moveT2).team = chessBoard.get(moveT1).team;
    chessBoard.get(moveT1).team = -1;
    //wakes the peice (for checking pawns first move)
    chessBoard.get(moveT2).awoken = true;
  }
  //no longer clicked
  chessBoard.get(moveT1).selected = false;
  chessBoard.get(moveT2).selected = false;
}

public void checkAndPromotion(){
  for(int i = 0; i < chessBoard.size() - 1; i++){
    if(i < 8 || i > 55){
      if(chessBoard.get(i).type == 0){ //check if the pawn is in the top or bottom 8 spaces
        //dissable use of the chess board
        menuOpen = true;
        //promotion menu
        fill(0);
        rect(0,height/3,width,height/3);
        if(mouseX < width/4){
          fill(125);
          rect(0,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 1;
            menuOpen = false;
          }
        } else if(mouseX < width/2){
          fill(125);
          rect(width/4,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 2;
            menuOpen = false;
          }
        } else if(mouseX > width - width/4){
          fill(125);
          rect((width/4)*3,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 4;
            menuOpen = false;
          }
        } else if(mouseX > width/2){
          fill(125);
          rect(width/2,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 3;
            menuOpen = false;
          }
        }
        //replace with images
        if(chessBoard.get(i).team == 0){
          fill(255,0,0);
        } else {
          fill(0,255,0);
        }
        text("1",width/8,height/2);
        text("2",width/2 - width/8,height/2);
        text("3",width/2 + width/8,height/2);
        text("4",width - width/8,height/2);
      }
    }
  }
}

public boolean checkLegalMove(int tile1, int tile2) {
  if (chessBoard.get(tile1).team == chessBoard.get(tile2).team) {
    return false;
  }
  if(chessBoard.get(tile1).team == 0 && turn == true){
    if (chessBoard.get(tile2).highlighted) {
      turn = false;
      return true;
    }
  } else if (chessBoard.get(tile1).team == 1 && turn == false){
    if (chessBoard.get(tile2).highlighted) {
      turn = true;
      return true;
    }
  }
  return false;
}

public boolean bMousePressed() {
  //b for better
  //is true for one frame when mouse is pressed
  if (mousePressed & mouseHeld == false) {
    mouseHeld = true;
    return true;
  }
  if (!mousePressed) {
    mouseHeld = false;
  }
  return false;
}

public void victoryMenu(int team){
  //draws a victory menu depending on who wins
    //team is 0 if red won
    //team is 1 ig green won
  background(125);
  menuOpen = true;
  textAlign(CENTER,CENTER);
  textSize(spacing/2);
  fill(0);
  text("Victory!",width/2,height/8);
  if(team == 0){
    text("The Green team won the game!",width/2,height/4);
  } else {
    text("The Red team won the game!",width/2,height/4);
  }
  text("Click anywhere to reset",width/2,height/2);

  if(bMousePressed()){
    setup();
  }
}
class ImageDB {
	//loads all the images at the start
	ArrayList<PImage> lightList;

	ArrayList<PImage> darkList;

	/*
		LEMME EXPLAIN THE NAME
		p13

		p - peice
		1 - team
		3 - type
	*/

	ImageDB(){
		lightList = new ArrayList<PImage>();
		darkList = new ArrayList<PImage>();

		for(int i = 0; i < 6; i++){
			PImage temp = loadImage("data/0"+i+".png");
			temp.resize((int)spacing,(int)spacing);
			lightList.add(temp);
		}
	}
}
class Tile {
  float x;
  float y;
  float tWidth;
  int type;
  int team;

  boolean colour;
  boolean selected;

  boolean highlighted;

  boolean awoken;
  Tile(float x, float y) {
    this.x = x;
    this.y = y;

    type = -1; //8 types of peice -1 for a blank space
    tWidth = spacing;
    selected = false;
    awoken = false;
  }

  public void run() {
    display();
    select();

    if (type == -1) {
      team = -1;
    }
  }

  public void display() {
    //if colour is true its a white space
    if (colour) {
      fill(255);
      if (mouseOver() || selected) {
        fill(191);
      }
    } else {
      fill(0);
      if (mouseOver() || selected) {
        fill(63);
      }
    }

    if (highlighted) {
      if (colour) {
        fill(170, 170, 255);
      } else {
        fill(86, 86, 171);
      }
    }
    rect(x, y, tWidth, tWidth);

    if (team == 1) {
      fill(0, 255, 0);
    } else {
      fill(255, 0, 0);
    }

    if (type != -1) {
      image(imageDB.lightList.get(type), x, y);
    }
  }

  public boolean mouseOver() {
    if(!menuOpen){
      if (mouseX > x && mouseX < x + tWidth) {
        if (mouseY > y && mouseY < y + tWidth) {
          return true;
        }
      }
    }
    return false;
  }

  public void select() {
    if (mouseOver() && bMousePressed()) {
      selected = !selected;
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
