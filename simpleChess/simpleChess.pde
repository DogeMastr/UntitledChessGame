//basic chess no fancy shit ok cool
ArrayList tileList;

void setup() {
  size(900, 900);

  tileList = new ArrayList<Tile>();

  for (int i = 0; i < 8; i++) {
    for (int j = 0; j < 8; j++) {
      tileList.add(new Tile(i*50, j*50, true));
    }
  }
}

void draw() {
  for (int i = 0; i <= tileList.size(); i++) {
    tileList.get(i).x = 0;
  }
}
