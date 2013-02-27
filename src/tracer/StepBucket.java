package tracer;
import entities.*;


public class StepBucket extends Thread {
	Tracer raymond;
	int x1,x2,y1,y2;
	public StepBucket(Tracer raymond, int x1, int y1, int x2, int y2){
		this.raymond = raymond;
		this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
	}
	
	public void run(){
		//Sphere object = new Sphere( new PVector(0,0,2),1f );
		//CrazyBoolean object = new CrazyBoolean( new PVector(0,0,2),1f );
		//Sponge object = new Sponge( new PVector(0,0,1.5f),1f );
		Sphere lightsource = new Sphere( new DVector( 0,-1.2f,1.5f ), 0.4f );
		DVector pos = new DVector(0,0,0);
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				
				//set the direction by screen space
				DVector dir = new DVector(x-raymond.w/2,y-raymond.h/2,raymond.w/2);
				dir.z *= 1.5f;
				dir.add(raymond.random(-0.25f,0.25f),raymond.random(-0.25f,0.25f),0); //antialias
				float seed = (float)Math.random() * 0.002f + 0.002f;
				dir.normalize();
				StepRay r = new StepRay(pos.get(),dir,seed);
				
				float light = 0.3f; //ambient light
				//do the hit tests
				for( int i = 0; i < 3000; i++ ){
					//r.step(object,lightsource);
					if( r.light != 0 ){ light = r.light; break; } //found a light source
					if( r.coef < 1/256f ){ break; } //too dark
					if( r.pos.y < -1 ){ break; } //out of range
				}
				
				if( r.pos.z < 1 && r.pos.y < -1 ){ light = 2f; } //key light 
				
				raymond.plate[x+y*raymond.w] += r.coef*light;
				raymond.count[x+y*raymond.w] += 1;
				
			}
		}
	}
}
