//basic chess no fancy shit ok cool
/*
  SHIT TO FIX:
    Displaying the right colours
    updating the type of the tile after moving them "correctly everytime"
    De-selecting tiles

  SHIT TO DO:
    fukin everything help me ok thanks
*/
ArrayList<Tile> chessBoard;

int moveT1; //the number of the tiles to be swapped
int moveT2;

void setup() {
  size(800, 800);
  rectMode(CORNER);
  textAlign(LEFT,TOP);
  textSize(width/10);

  chessBoard = new ArrayList<Tile>();

  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      chessBoard.add(new Tile(i*width/10 + width/10, j*width/10 + width/10, true, (int)random(0,9)));
    }
  }
  println(chessBoard.size());
}

void draw() {
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


void movePeice(){
  //The peice is moving from one space to another
  chessBoard.get(moveT1).type = chessBoard.get(moveT2).type;
  chessBoard.get(moveT2).type = 0;
  //no longer clicked
  chessBoard.get(moveT1).clicked = false;
  chessBoard.get(moveT2).clicked = false;
  println("Moved Peice!");
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
