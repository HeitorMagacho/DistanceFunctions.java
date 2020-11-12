//import java.math.BigDecimal;
/*
 * This class implements the Dice metric distance function.
 * @class Dice implementation.
 * */
public class Dice extends DistanceFunction {
	
	/*
	 * Dice constructor.
	 * */
	public Dice() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Dice distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
	    double sum1 = 0.0;
	    double sum2 = 0.0;
	    double sum3 = 0.0;
		
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			sum1 += Math.pow((s1.getValue(x) - s2.getValue(x)), 2);
			sum2 += Math.pow(s1.getValue(x), 2);
			sum3 += Math.pow(s2.getValue(x), 2);
		}
		
		//answer = Double.valueOf(formatter.format(answer));

		if ((sum2 + sum3) == 0) {
			return 0.0;
		}
		
		answer = (sum1 / (sum2 + sum3));
		answer = 1 - answer;
		return Truncate(2, answer);
	}
	
}
