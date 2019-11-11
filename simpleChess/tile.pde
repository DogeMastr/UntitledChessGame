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

  void run() {
    display();
    select();
  }

  void display() {
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

  boolean mouseOver(){
    if(mouseX > x && mouseX < x + tWidth){
      if(mouseY > y && mouseY < y + tWidth){
        return true;
      }
    }
    return false;
  }

  void select(){
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
