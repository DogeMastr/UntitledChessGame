//basic chess no fancy shit ok cool
/*
 SHIT IM DOING:
  Pawn promotion

 SHIT LEFT TO DO:
  win condition
  menu and gameover screens
  check
 */
ArrayList<Tile> chessBoard;

int moveT1 = -1; //the number of the tiles to be swapped
int moveT2 = -1;
int clicked = 0; //how many tiles are clicked at the end of the frame

float spacing;
boolean mouseHeld = false;

boolean turn; //whose turn is it? true = white/green

void setup() {
  size(800, 800);
  //fullScreen();
  if (width <= height) {
    spacing = width/10;
  } else {
    spacing = height/10;
  }
  rectMode(CORNER);
  textAlign(LEFT, TOP);
  textSize(spacing);

  chessBoard = new ArrayList<Tile>();

  initBoard();

  turn = true;
}

void initBoard() {
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
    chessBoard.get(1).type = 0;
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

void draw() {
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
}

void clickPeice() {
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

boolean blockingN = false;
boolean blockingS = false;
boolean blockingE = false;
boolean blockingW = false;
boolean blockingNW = false;
boolean blockingNE = false;
boolean blockingSW = false;
boolean blockingSE = false;
void showMoves() {
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

void movePeice() {
  //check of they are on different teams
  if (checkLegalMove(moveT1, moveT2)) {
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

boolean menuOpen = false;
void checkAndPromotion(){
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
          println(1);
          rect(0,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 1;
            menuOpen = false;
          }
        } else if(mouseX < width/2){
          fill(125);
          println(2);
          rect(width/4,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 2;
            menuOpen = false;
          }
        } else if(mouseX > width - width/4){
          fill(125);
          rect((width/4)*3,height/3,width/4,height/3);
          println(4);
          if(bMousePressed()){
            chessBoard.get(i).type = 4;
            menuOpen = false;
          }
        } else if(mouseX > width/2){
          fill(125);
          println(3);
          rect(width/2,height/3,width/4,height/3);
          if(bMousePressed()){
            chessBoard.get(i).type = 3;
            menuOpen = false;
          }
        }
      }
    }
  }
}

boolean checkLegalMove(int tile1, int tile2) {
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

boolean bMousePressed() {
  //b for better
  //is true for one frame when mouse is pressed
  if (mousePressed & mouseHeld == false) {
    mouseHeld = true;
    println("Clicked!");
    return true;
  }
  if (!mousePressed) {
    mouseHeld = false;
  }
  return false;
}
