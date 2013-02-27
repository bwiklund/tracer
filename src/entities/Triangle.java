package entities;

import tracer.Ray;

public class Triangle extends Entity {

	public DVector normal;
	
	public DVector v1,v2,v3;
	
	public Triangle(DVector v1, DVector v2, DVector v3) {
		this.v1 = v1; this.v2 = v2; this.v3 = v3; 
		//these need to be lines
		DVector seg1 = v1.get(); seg1.sub(v2);
		DVector seg2 = v3.get(); seg2.sub(v2);
		normal = seg1.cross( seg2 );
		System.out.println( normal );
	}

	public double intersect(Ray ray){
		// http://en.wikipedia.org/wiki/Line-plane_intersection

		// this code is duplicated in plane, oh well
		//tmp is p0-L0
		ray.tmp.set( v1 ); ray.tmp.sub( ray.pos );
		double PLdotN = ray.tmp.dot( normal );
		double LdotN = ray.dir.dot( normal );
		double distToPlane = PLdotN/LdotN;
		
		if( distToPlane < 0 ){ //behind the ray
			return -1;
		} else { //in front, test if it hits the triangle
			
		}
		
		return distToPlane; // no hit
	}
	
	public void reflect( Ray ray ){
		ray.walln.set( normal ); //get the normal
		//ray.walln.normalize();
		ray.walln.mult(2 * ray.walln.dot(ray.dir));
		ray.dir.sub(ray.walln);
		ray.dir.normalize();
	}

}
