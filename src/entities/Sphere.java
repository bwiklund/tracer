package entities;

import tracer.Ray;

public class Sphere extends Entity {
	public double sphereradius_sq;
	
	public Sphere(DVector pos, double sphereradius) {
		super(pos);
		this.sphereradius_sq = sphereradius*sphereradius;
		test = new DVector();
	}
	
	DVector test;
	
	public boolean hitTest( DVector raypos ){
		boolean hit = false;
		
		test.set( raypos ); test.sub( pos );
		
		if( test.x*test.x + test.y*test.y + test.z * test.z <= sphereradius_sq ){
			hit = true;
		}
		
		return hit;
	}
	
	public double intersect(Ray ray){
		//first check as a cube, to cheaply hittest
		//if(true)return -1;
		//Reference: http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
		//translate so ray starts at local 0,0,0
		ray.tmp.set( pos ); ray.tmp.sub(ray.pos);
		double LdotC = ray.dir.dot( ray.tmp );
		double Csquared = ray.tmp.dot(ray.tmp);
		double underSQRT = LdotC * LdotC - Csquared + sphereradius_sq;
		//there are 0-2 solutions, depending on how many intersections there are
		if( underSQRT < 0 ){ //no solutions, no intersections
			return -1;
		}
		if( underSQRT == 0 ){ //1 solution
			return LdotC;
		}
		
		underSQRT = Math.sqrt( underSQRT );
		double solution1 = LdotC + underSQRT;
		double solution2 = LdotC - underSQRT;
		
		if( solution1 > 0 && solution2 > 0 ){	//only project forwards
			return Math.min(solution1,solution2); 
		} else {
			return Math.max(solution1,solution2);//return Math.max(solution1,solution2); 
		}
	}
	
	public void reflect( Ray ray ){
		ray.walln.set(ray.pos); ray.walln.sub(pos); //get the normal
		ray.walln.normalize();
		ray.walln.mult(2 * ray.walln.dot(ray.dir));
		ray.dir.sub(ray.walln);
		ray.dir.normalize();

		ray.walln.set(ray.pos); ray.walln.sub(pos); 
		ray.walln.normalize();//do it again so the ray can use it. sloppy.
		
	}
}
