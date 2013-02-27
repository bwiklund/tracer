package tracer;
import java.util.Random;

import entities.Entity;
import entities.DVector;
import entities.Sphere;


public class Ray {
	public DVector pos,dir; //position and direction
	public double coef,light;
	public DVector travel,tmp,walln;
	
	public boolean done = false;

	//http://en.wikipedia.org/wiki/Trichromatic_vision
	//400-750
	public double wavelength;
	public Random rand;
	public int x,y;
	
	int bounces,maxbounces;
	
	public Ray(Random rand, DVector pos, DVector dir, double wavelength, double coef, double light, int x, int y, int bounces ){
		this.rand = rand;
		this.pos = pos; 
		this.dir = dir; 
		this.wavelength = wavelength;
		this.coef = coef; 
		this.light = light;
		this.x = x; 
		this.y = y;
		this.bounces = bounces;
		
		//tmps
		travel = new DVector(); 
		walln = new DVector(); 
		tmp = new DVector();
	}
	
	public Ray clone(){
		return new Ray( rand, pos.get(), dir.get(), wavelength, coef, light, x, y, bounces );
	}
	
	public void step(Entity e){
		pos.add( dir );
		if( e.hitTest(pos) ){ bounceRandom(); }
	}

	public void travel(double dist) {
		travel.set(dir);
		travel.mult((float)dist);
		pos.add(travel);
	}
	
	public void bounceRandom(){
		dir.set(rand.nextDouble()-0.5,rand.nextDouble()-0.5,rand.nextDouble()-0.5);
		dir.normalize();
	}
	
	
	public void bounce(Entity entity){
		//ask the object to update the direction of the ray
		entity.reflect( this ); 
		
		//blur it up some
		
		//double noiseyness = Math.pow(rand.nextDouble(),entity.focus)-1; 
		double noiseyness = 0.3*2/entity.focus; // 0 = inf, 1 = 1, inf = 0 //the 2 is to fix the random range from 0.5 1.0
		//System.out.println( noiseyness*(rand.nextDouble()-0.5) );
		dir.add( noiseyness*(rand.nextFloat()-0.5),
				 noiseyness*(rand.nextFloat()-0.5),
				 noiseyness*(rand.nextFloat()-0.5));
		dir.normalize();

		/*if( entity instanceof Sphere ){
		}*/
		
		
		//dim the light
		coef *= entity.diffuse;
		
		
		//affect the color based on wavelength
		double color_absorb = 1-Film.getResponse(entity.absorb_wavelength1, entity.absorb_width1, wavelength);
		coef *= ( entity.absorb_amount1 * color_absorb + 1 * (1-entity.absorb_amount1) );
		
		//add light if we hit a light source
		light += entity.lightlevel;
		

		if( walln.dot(dir) < 0 ){
			//bounce it back out again
			entity.reflect(this);
			//should fix the reflection code so you can't aim inwards. unless it's refracting light.
		}
		
		if( light > 0 ){ //for now
			done = true;
		}
	}

	public double calculate(Bucket b, int maxbounces) {
		//double rayAmbientlight = 0;
		Scene scene = b.scene;
		//do the hit tests
		int size = scene.ents.size();
		
		for( ; bounces < maxbounces; bounces++ ){

			//branch out and destroy the cpu
			/*if( bounces==1){
				Ray branch = this.clone(); branch.bounces++; b.q2.add(branch);
			}*/
			
			int closestobj = -1;
			double travel = 10000000;
			
			
			for( int j = 0; j < size; j++ ){
				double testdistance = scene.ents.get(j).intersect(this);
				if( testdistance > 0.00005 ){ //should be zero, but there is floating error
					if( testdistance < travel ){
						travel = testdistance;
						closestobj = j;
					}
				}
			}
			
			if( travel != 10000000 ){
				travel(travel);

				
				bounce(scene.ents.get(closestobj));
				
			} else {
				//nothing to hit, die
				//rayAmbientlight = scene.ambientlight;
				break;
			}
			

			
			if( coef == 0 || done == true ){ break; }
			
		}
		
		//if( r.dir.y < 0 ){ light += 0.7f; } //key light 
		double intensity = (light/* + rayAmbientlight*/) * coef;
		return intensity;
	}
}
