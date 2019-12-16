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

  void run() {
    display();
    select();

    if (type == -1) {
      team = -1;
    }
  }

  void display() {
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

  boolean mouseOver() {
    if(!menuOpen){
      if (mouseX > x && mouseX < x + tWidth) {
        if (mouseY > y && mouseY < y + tWidth) {
          return true;
        }
      }
    }
    return false;
  }

  void select() {
    if (mouseOver() && bMousePressed()) {
      selected = !selected;
    }
  }
}
