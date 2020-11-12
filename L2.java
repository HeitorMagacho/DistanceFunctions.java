//import java.math.BigDecimal;
/*
 * This class implements the L2 metric distance function.
 * @class L2 implementation.
 * */
public class L2 extends DistanceFunction {
	
	/*
	 * L2 constructor.
	 * */
	public L2() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The L2 distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			answer += Math.abs(Math.pow(s1.getValue(x) - s2.getValue(x), 2));
		}
		
		//answer = Double.valueOf(formatter.format(answer));
		
		answer = Math.sqrt(answer);
		return Truncate(2, answer);
	}
	
}
