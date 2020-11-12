//import java.math.BigDecimal;
/*
 * This class implements the Gower metric distance function.
 * @class Gower implementation.
 * */
public class Gower extends DistanceFunction {
	
	/*
	 * Gower constructor.
	 * */
	public Gower() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Bray_Curtis distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		L1 df = new L1();
		double sum1 = df.getDistance(s1, s2);
		double n = s1.getSize();
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}
		
		//answer = Double.valueOf(formatter.format(answer));
		
		if (n == 0) {
			return 0;
		}

		answer = sum1 / n;
		return Truncate(2, answer);
	}
	
}
