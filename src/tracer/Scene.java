package tracer;

import java.util.Random;
import java.util.Vector;


import entities.*;

public class Scene {
	public Vector<Entity> ents;
	public double ambientlight = 0.4;
	public DVector cameraPos;
	public Scene(){;}
	
	
	
	public Scene(int seed){
		cameraPos = new DVector(0.2,0,0);
		ents = new Vector<Entity>();
		
		Random rand = new Random(1337+seed);
		//overhead light
		Entity object = new Sphere( new DVector(0,-5000,0),3000 );
		object.lightlevel = 20;
		object.diffuse = 1;
		object.absorb_wavelength1 = 400;
		object.absorb_amount1 = 1;
		object.absorb_width1 = 200;
		ents.add( object );
		
		//floor
		/*object = new Sphere( new DVector(0,70,0),68 );
		object.lightlevel = 0;
		object.diffuse = 0.8;
		object.focus = 2;
		ents.add( object );*/
		
		object = new Plane( new DVector(0,2,0), new DVector(0,-1,0) );
		object.lightlevel = 0;
		object.diffuse = 0.5;
		object.focus = 0;
		ents.add( object );
		

		object = new Plane( new DVector(0,0,6), new DVector(0,0,-1) );
		object.lightlevel = 0;
		object.diffuse = 0.5;
		object.focus = 0;
		ents.add( object );
		
		//key lights
		object = new Sphere( new DVector(0,0,-10),4 );
		object.lightlevel = 5;
		object.diffuse = 1;
		object.absorb_wavelength1 = 400;
		object.absorb_amount1 = 1;
		object.absorb_width1 = 200;
		ents.add( object );
		
		//grid
		float perrow = 5;
		for( int i = 0; i < perrow*perrow; i++ ){
			DVector pos = new DVector(i%perrow,Math.floor(i/perrow),0);
			pos.sub((perrow-1)/2,(perrow-1)/2,0);
			pos.mult( 4/perrow ); pos.z = 4;
			object = new Sphere( pos,0.4 );
			
			object.lightlevel = 0;//Math.round(rand.nextDouble()*0.8f)*5;
			object.diffuse = Math.sqrt(rand.nextDouble());
			object.focus = rand.nextDouble()*2 + 0.8;
			object.absorb_wavelength1 = 300 + rand.nextDouble()*500;
			object.absorb_amount1 = rand.nextDouble();
			object.absorb_width1 = 400*rand.nextDouble();
			ents.add( object );
		}
	}
}
