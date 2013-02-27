package tracer;

import java.util.Random;
import java.util.Vector;


import entities.*;

public class CornellBoxScene extends Scene {
	
	public CornellBoxScene(int seed){
		cameraPos = new DVector(0,0,-1);
		
		ents = new Vector<Entity>();
		
		Random rand = new Random(1337+seed);
		
		ambientlight = 1;
		
		//overhead light
		Entity object = new Sphere( new DVector(0,-102,2),100.003 );
		object.lightlevel = 20;
		object.diffuse = 1;
		object.absorb_wavelength1 = 400;
		object.absorb_amount1 = 0;
		object.absorb_width1 = 200;
		ents.add( object );
		
		//planes
		
		//floor
		object = new Plane( new DVector(0,2,0), new DVector(0,-1,0) );
		object.lightlevel = 0;
		object.diffuse = 0.9;
		object.focus = 1;
		ents.add( object );
		
		//backwall
		object = new Plane( new DVector(0,0,6), new DVector(0,0,-1) );
		object.lightlevel = 0;
		object.diffuse = 0.9;
		object.focus = 0.1;
		ents.add( object );
		
		//ceiling
		object = new Plane( new DVector(0,-2,0), new DVector(0,1,0) );
		object.lightlevel = 0;
		object.diffuse = 0.9;
		object.focus = 0.1;
		ents.add( object );
		
		//leftwall
		object = new Plane( new DVector(-2,0,0), new DVector(1,0,0) );
		object.lightlevel = 0;
		object.diffuse = 0.9;
		object.focus = 0.1;
		object.absorb_wavelength1 = 540;
		object.absorb_amount1 = 1;
		object.absorb_width1 = 50;
		ents.add( object );
		
		//rightwall
		object = new Plane( new DVector(2,0,0), new DVector(-1,0,0) );
		object.lightlevel = 0;
		object.diffuse = 0.9;
		object.focus = 0.1;
		object.absorb_wavelength1 = 610;
		object.absorb_amount1 = 1;
		object.absorb_width1 = 50;
		ents.add( object );
		
		
		
		
		//props
		
		object = new Sphere( new DVector(1.0,1.2,5.0),0.8 );
		object.lightlevel = 0;//Math.round(rand.nextDouble()*0.8f)*5;
		object.diffuse = 0.9;
		object.focus = 5;
		object.absorb_wavelength1 = 0;
		object.absorb_amount1 = 0;
		object.absorb_width1 = 0;
		ents.add( object );
		

		object = new Sphere( new DVector(-0.5,1.0,4),1 );
		object.lightlevel = 0;//Math.round(rand.nextDouble()*0.8f)*5;
		object.diffuse = 0.9;
		object.focus = 1;
		object.absorb_wavelength1 = 0;
		object.absorb_amount1 = 0;
		object.absorb_width1 = 0;
		ents.add( object );
	}
}
