package entities;

import tracer.Ray;

public class Plane extends Entity {
	public DVector normal;
	public Plane(DVector pos, DVector normal) {
		super(pos);
		this.normal = normal;
	}

	public double intersect(Ray ray){
		// http://en.wikipedia.org/wiki/Line-plane_intersection
		
		// d = ((p0 - L0) dot n) / L dot n

		//tmp is p0-L0
		ray.tmp.set( pos ); ray.tmp.sub( ray.pos );
		double PLdotN = ray.tmp.dot( normal );
		double LdotN = ray.dir.dot( normal );
		return PLdotN/LdotN;
	}
	
	public void reflect( Ray ray ){
		ray.walln.set( normal ); //get the normal
		//ray.walln.normalize();
		ray.walln.mult(2 * ray.walln.dot(ray.dir));
		ray.dir.sub(ray.walln);
		ray.dir.normalize();
		
		ray.walln.set( normal ); //so the ray can use it
	}
}
