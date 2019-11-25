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

void setup() {
  size(800,800);
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

void initBoard(){
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

void draw() {
  background(67,70,82);
  clickPeice();
  for (int i = 0; i < chessBoard.size(); i++) {
    chessBoard.get(i).run();
  }
}

int clicked = 0; //how many tiles are clicked at the end of the frame
void clickPeice(){
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

void showMoves(){
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

void movePeice(){
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

boolean checkLegalMove(int tile1, int tile2){
  if(chessBoard.get(tile1).team == chessBoard.get(tile2).team){
    return false;
  }
  return true;
}

boolean mouseHeld = false;
boolean bMousePressed(){
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
