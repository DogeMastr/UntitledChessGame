class ImageDB {
	//loads all the images at the start
	ArrayList<PImage> lightList;

	ArrayList<PImage> darkList;

	/*
		LEMME EXPLAIN THE NAME
		p13

		p - peice
		1 - team
		3 - type
	*/

	ImageDB(){
		lightList = new ArrayList<PImage>();
		darkList = new ArrayList<PImage>();

		for(int i = 0; i < 6; i++){
			PImage temp = loadImage("data/0"+i+".png");
			temp.resize((int)spacing,(int)spacing);
			lightList.add(temp);
		}
	}
}
