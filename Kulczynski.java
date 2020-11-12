//import java.math.BigDecimal;
/*
 * This class implements the Kulczynski metric distance function.
 * @class Kulczynski implementation.
 * */
public class Kulczynski extends DistanceFunction {
	
	/*
	 * Kulczynski constructor.
	 * */
	public Kulczynski() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Kulczynski distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		L1 df = new L1();
	    double sum1 = df.getDistance(s1, s2);
		double sum2 = 0;
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			
			sum2 += Math.min(s1.getValue(x), s2.getValue(x));
		}
		
		if (sum2 == 0) {
			return 0;
		}
		
		answer = sum1/sum2;
		return Truncate(2, answer);
		
	}
	
}
