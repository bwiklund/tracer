package tracer;

import processing.core.PApplet;

public class FilmGraph {
	public static void DrawGraph( Tracer p ){
		int w = 800;
		int h = 100;
		int x = 10;
		int y = 10;
		
		int bgcolor = p.color(0,0,0);
		
		p.pushMatrix();
		p.translate(x,y);
		p.scale(0.5f,0.5f);
		p.rect(0,0,w,h);
		
		for( int i = 0; i < 800; i++ ){
			p.color(255,0,0);
			p.strokeWeight(3);
			p.point(i,(float) ((float) h-(Film.getRed(i)*h)));
			p.point(i,(float) ((float) h-(Film.getGreen(i)*h)));
			p.point(i,(float) ((float) h-(Film.getBlue(i)*h)));
		}
		
		p.popMatrix();
	}
}
