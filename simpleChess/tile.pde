class Tile {
  float x;
  float y;
  float tWidth;
  int type;
  boolean team;

  boolean colour;
  boolean clicked;

  Tile(float x, float y) {
    this.x = x;
    this.y = y;

    type = -1; //8 types of peice + 1 for blank space
    tWidth = spacing;
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

    if(team){
      fill(0,255,0);
    } else {
      fill(255,0,0);
    }

    if(type != -1){
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
    if(mouseOver() && bMousePressed()){
      clicked = !clicked;
    }
  }
}
