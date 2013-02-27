package tracer;

public class Film {
	public static double getRed( double wavelength ){
		return getResponse( 645, 200, wavelength);
	}
	public static double getGreen( double wavelength ){
		return getResponse( 580, 200, wavelength);
	}
	public static double getBlue( double wavelength ){
		return getResponse( 500, 200, wavelength); //biassssss
	}
	public static double getResponse( double center, double width, double wavelength ){
		//returns 0-1
		double ret = Math.max(0,1-Math.abs( center - wavelength )/80);
		return ret;
	}
}
