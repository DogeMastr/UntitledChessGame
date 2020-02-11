class ImageDB {
  //loads all the images at the start
  ArrayList<PImage> lightCurrent;
  ArrayList<PImage> darkCurrent;

  ArrayList<PImage> lightDefault;
  ArrayList<PImage> darkDefault;

  ArrayList<PImage> lightAnimals;
  ArrayList<PImage> darkAnimals;
  PImage temp;

  /*
		LEMME EXPLAIN THE NAME
   		13.png

   		1 - team
   		3 - type
   	*/

  ImageDB() {
    lightCurrent = new ArrayList<PImage>();
    darkCurrent = new ArrayList<PImage>();

    lightDefault = new ArrayList<PImage>();
    darkDefault = new ArrayList<PImage>();

    lightAnimals = new ArrayList<PImage>();
    darkAnimals = new ArrayList<PImage>();

    for (int i = 0; i < 6; i++) {
      temp = loadImage("skins/default/0"+i+".png");
      temp.resize((int)spacing, (int)spacing);
      lightDefault.add(temp);
      temp = loadImage("skins/default/1"+i+".png");
      temp.resize((int)spacing, (int)spacing);
      darkDefault.add(temp);

      temp = loadImage("skins/animals/0"+i+".png");
      temp.resize((int)spacing, (int)spacing);
      lightAnimals.add(temp);
      temp = loadImage("skins/animals/1"+i+".png");
      temp.resize((int)spacing, (int)spacing);
      darkAnimals.add(temp);
    }

    lightCurrent = lightAnimals;
    darkCurrent = darkAnimals;
  }
}
