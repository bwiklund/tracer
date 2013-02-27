package entities;

import tracer.Ray;

public class Entity {
	public DVector pos;
	public double lightlevel = 0;
	public double focus = 1;
	public double diffuse = 0.5f;
	
	public double absorb_wavelength1 = 0.5;
	public double absorb_amount1 = 0;
	public double absorb_width1 = 1;
	
	public Entity(){
		//
	}
	
	public Entity( DVector pos ){
		this.pos = pos;
	}
	
	public boolean hitTest( DVector p ){
		return false;
	}
	
	public double intersect( Ray ray ){
		return 0;
	}
	
	public void reflect(Ray ray) {
		//
	}
}
