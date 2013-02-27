package tracer;
import java.util.Vector;

import entities.DVector;
import entities.Entity;
import entities.Sphere;

import processing.core.PApplet;
import processing.core.PImage;


public class Tracer extends PApplet {
	public static Tracer top;
	PImage img;
	int w,h;
	double[] plate;
	int[] count;
	
	//some settings that should have a ui
	
	Settings settings;
	Scene scene;
	
	int seed = 0;
	
	public void setup(){
		top = this;
		settings = new Settings();
		w = h = (int) (settings.windowsize/settings.scale);
		plate = new double[w*h*3];
		count = new int[w*h];
		size((int)(w*settings.scale),(int)(h*settings.scale));
		img = createImage(w, h, RGB);
		
		frameRate(200);
		
		seed = (int) (Math.random()*1337);
	}
	
	int millis = 0;
	long lastphotonscast = 0;
	long totalphotonscast = 0;
	
	Vector<Bucket> buckets;
	int nextline = 0;
	
	public void initBuckets(){
		buckets = new Vector<Bucket>();
		for( int i = 0; i < settings.nthreads; i++ ){
			//Bucket b = new Bucket(new Scene(seed),this);//,0,i*h/nthreads,w,(int)((i+1)*h/nthreads));
			Bucket b = new Bucket(new CornellBoxScene(seed),this);//,0,i*h/nthreads,w,(int)((i+1)*h/nthreads));
			buckets.add(b);
		}
	}
	
	public void runBuckets(){
		nextline = 0;
		for( int i = 0; i < settings.nthreads; i++ ){
			buckets.get(i).start();
		}
		for( int i = 0; i < settings.nthreads; i++ ){
			try {
				buckets.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void draw(){
		img.loadPixels();
		colorMode( HSB,1,1,1 );
		
		//for(int i = 0; i < settings.passesperupdate; i++ ){
			initBuckets();
			runBuckets();
		//}
		
		totalphotonscast = 0;
		
		//settings.exposure = (float) mouseX / 100;
		
		colorMode( RGB, 1 );
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				double div = count[x+y*h] / settings.exposure;
				totalphotonscast += count[x+y*h];
				int i = (x+y*w)*3;
				int c = 0;
				if( mousePressed && mouseButton == LEFT ){
					//show grayscale, so early color renders dont look like shit
					c = color(
							(float) ((plate[i+0] + plate[i+1] + plate[i+2])/div/3)
						);
				} else { //show color
					c = color(
							(float)(plate[i+0]/div),
							(float)(plate[i+1]/div),
							(float)(plate[i+2]/div)
						);
				}
				img.pixels[x + y * w] = c;
			}
		}
		
		img.updatePixels();
		image(img, 0, 0, (float)(w*settings.scale), (float)(h*settings.scale) );
		
		println("Ms: " + (millis()-millis) + " - Pass: " + frameCount + " - "
				+ totalphotonscast + " rays cast so far - "
				+ (totalphotonscast-lastphotonscast)/(millis()-millis)*1000 + " rays per second.");
		millis = millis();
		lastphotonscast = totalphotonscast;
		//if( frameCount % 1 == 0 ){
		if( keyPressed ){
			savedframecount += 1;
			saveFrame("out2/" + savedframecount + "_box.tiff");
			println("Saved a frame.");
			//wipePlate();
		}
		
		if( mousePressed && mouseButton == RIGHT ){
			FilmGraph.DrawGraph(this);
		}
	}
	
	int savedframecount = 1001;
	
	public void wipePlate(){
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				plate[x+y*w] = count[x+y*w] = 0;
			}
		}
	}
}
