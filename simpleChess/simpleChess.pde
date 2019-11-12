//basic chess no fancy shit ok cool
/*
  SHIT TO FIX:
  Set up the board so the peices start in the right positions

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

void setup() {
  fullScreen();
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

    if(i <= 7){
      chessBoard.get(i).type = i;
    } else if(i <= 15){
      chessBoard.get(i).type = 8;
    } else if(i >= 48){
      chessBoard.get(i).type = 8;
    }
  }
}

void draw() {
  background(67,70,82);
  clickPeice();
}

int clicked = 0; //how many tiles are clicked at the end of the frame
void clickPeice(){
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

void movePeice(){
  //The peice is moving from one space to another
  chessBoard.get(moveT2).type = chessBoard.get(moveT1).type;
  chessBoard.get(moveT1).type = -1;
  //no longer clicked
  chessBoard.get(moveT1).clicked = false;
  chessBoard.get(moveT2).clicked = false;
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
