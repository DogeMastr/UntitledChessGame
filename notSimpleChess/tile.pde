class Tile {
  float x;
  float y;
  float tWidth;
  int type;
  int team; // 0 or 1, -1 for blank space
  boolean hiddenKing; //if the piece is a hidden king 

  boolean colour;
  boolean selected;

  boolean highlighted;

  boolean awoken;
  Tile(float x, float y, boolean tiletype) {
    this.x = x;
    this.y = y;

    colour = tiletype;
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
      fill(191);
      if (mouseOver() || selected) {
        fill(128);
      }
    } else {
      fill(63);
      if (mouseOver() || selected) {
        fill(126);
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
      if (type != -1) {
        image(imageDB.darkCurrent.get(type), x, y);
      }
    } else {
      if (type != -1) {
        image(imageDB.lightCurrent.get(type), x, y);
      }
    }
  }

  boolean mouseOver() {
    if (!menuOpen) {
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
