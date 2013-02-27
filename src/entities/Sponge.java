package entities;


public class Sponge extends Entity {
	//public float sphereradius_sq;
	
	public Sponge(DVector pos, float sphereradius) {
		super(pos);
		//this.sphereradius_sq = sphereradius*sphereradius;
		test = new DVector();
		test2 = new DVector();
	}
	
	DVector test,test2;
	
	public boolean hitTest( DVector raypos ){
		
		test.set( raypos ); test.sub( pos );

		float outer = 0.5f;

		boolean hit = false;
		//see of it's in the main cube
		if( Math.abs(test.x) <= outer ){
		if( Math.abs(test.y) <= outer ){
		if( Math.abs(test.z) <= outer ){
			hit = true;
		}}}
		
		if( hit ){
			hit = rec( test, 0 );
		}
		
		return hit;
	}
	
	public boolean rec( DVector t, int level ){
		
		float outer = 0.5f;
		float inner = outer / 3f;
		boolean hit = true;
		int misses = 0;
		
		if( Math.abs(test.x) < inner  ){ misses += 1; }
		if( Math.abs(test.y) < inner  ){ misses += 1; }
		if( Math.abs(test.z) < inner  ){ misses += 1; }
		
		if( misses == 2 || misses == 3 ){ hit = false; }
		
		return hit;
		
	}
}
