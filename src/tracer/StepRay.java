package tracer;
import entities.Entity;
import entities.DVector;


public class StepRay {
	public DVector pos,dir; //position and direction
	float seed = 1; //the random speed of the particle
	public float coef = 1; //the amount of light absorbed before finding a light source
	public float light = 0;
	public StepRay( DVector pos, DVector dir, float seed ){
		this.pos = pos; this.dir = dir; this.seed = seed; 
		dir.mult(seed);
	}
	
	public void step(Entity e, Entity lightsource){
		pos.add( dir );
		
		if( e.hitTest(pos) ){
			coef *= 0.90f; //absorb light
			dir.set((float)Math.random()-0.5f,(float)Math.random()-0.5f,(float)Math.random()-0.5f);
			dir.normalize(); dir.mult(seed);
		}
		
		if( lightsource.hitTest(pos) ){
			//light = 5f;
		}
	}
}
