package tracer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import entities.*;


public class Bucket extends Thread {
	Tracer raymond;
	int x1,x2,y1,y2;
	Scene scene;
	public boolean idle = true;
	Random rand = new Random();
	
	ArrayList<Ray> q = new ArrayList<Ray>();
	ArrayList<Ray> q2 = new ArrayList<Ray>();
	
	public Bucket(Scene scene, Tracer raymond){//, int x1, int y1, int x2, int y2){
		this.raymond = raymond;
		this.scene = scene;
		//this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
	}
	
	public void run(){
		//int starttime = raymond.millis();
		render();
		//raymond.println( "Thread says: " + (raymond.millis()-starttime) );
	}
	
	public void render(){
		//Sphere object = new Sphere( new PVector(0,0,2),0.5f );
		//CrazyBoolean object = new CrazyBoolean( new PVector(0,0,2),1f );
		//Sponge object = new Sponge( new PVector(0,0,1.5f),1f );
		//Sphere lightsource = new Sphere( new PVector( 0,-1.2f,1.5f ), 0.4f );

		DVector pos = scene.cameraPos;
		
		int w = raymond.w;
		int h = raymond.h;
		x1 = 0; x2 = w;

		
		while( true ){
			int doline = raymond.nextline++;
			if( doline >= h ){ break; }
	
			int maxbounces = raymond.settings.maxbounces;
			int passesperupdate = raymond.settings.passesperupdate;
			
			for (int x = x1; x < x2; x++) {
				for( int pass = 0; pass < passesperupdate; pass++ ){
			//	for (int y = y1; y < y2; y++) {
				//if( true ){ raymond.count[0] += 1; continue; }
					
					int y = doline; 
					//set the direction by screen space
					DVector dir = new DVector(x-w/2,y-h/2,w/2);
					dir.z *= 1.5f;
					dir.add((rand.nextDouble()-0.5)*0.5,(rand.nextDouble()-0.5)*0.5,0); //antialias
					dir.normalize();
					double wavelength = 350+rand.nextDouble()*350;//      0-1 represents 350 to 700
					
					Ray r = new Ray(rand,pos.get(),dir,wavelength,1,0,x,y,0);
					
					
					q.add(r);
					/*q.add(r.clone());
					q.add(r.clone());
					q.add(r.clone());
					q.add(r.clone());*/
					
					
					
				//}
			}}
			
			while( q.size() > 0 || q2.size() > 0 ){
				
				q.addAll(q2);
				q2.clear();
				
				for( Iterator<Ray> it = q.iterator(); it.hasNext(); ){
					
					Ray r = it.next();
					
					double intensity = r.calculate(this,maxbounces);
						
					double red   = Film.getRed(r.wavelength) * intensity;
					double green = Film.getGreen(r.wavelength) * intensity;
					double blue  = Film.getBlue(r.wavelength) * intensity;
					
					int i = r.x+r.y*raymond.w;
					raymond.plate[i*3+0] += red;
					raymond.plate[i*3+1] += green;
					raymond.plate[i*3+2] += blue;
					raymond.count[i] += 1;
					
					//it.remove();
				}
				
				q.clear();
				
			}
		}
	}
}
