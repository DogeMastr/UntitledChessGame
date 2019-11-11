class Tile {
  float x;
  float y;
  float tWidth;
  float type;
  boolean colour;

  Tile(float x, float y, boolean colour) {
    type = 0;
    this.x = x;
    this.y = y;
    this.colour = colour;
    println("wtf");
  }

  void run() {
    println("wtf");
  }
  void display() {
    if (colour) {
      fill(0);
    } else {
      fill(255);
    }
    rect(x, y, tWidth, tWidth);

    if (type != 0) {
      text(type, x, y);
    }
  }
}
