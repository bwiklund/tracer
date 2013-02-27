package entities;

public class CrazyBoolean extends Entity {
	public float sphereradius;
	
	public CrazyBoolean(DVector pos, float sphereradius) {
		super(pos);
		this.sphereradius = sphereradius;
	}

	public boolean hitTest( DVector raypos ){
		boolean hit = false;
		
		DVector test = pos.get(); test.sub( raypos );
		
		if( test.x*test.x + test.y*test.y + test.z * test.z <= sphereradius * sphereradius ){
			hit = true;
		}
		if( Math.abs( raypos.x ) < 0.3 || Math.abs( raypos.y ) < 0.3 ){ //cut a hole
			hit = false;
		}
		if( Math.abs( 0.5-raypos.x ) < 0.1 || Math.abs( 0.5-raypos.y ) < 0.1 ){ //cut a hole
			hit = false;
		}
		float innerradius = 0.9f;
		if( test.x*test.x + test.y*test.y + test.z * test.z <= innerradius*innerradius ){
			hit = false;
		}
		if( test.x*test.x + test.y*test.y + test.z * test.z <= 0.6 ){
			hit = true;
		}
		
		return hit;
	}
}
