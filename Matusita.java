//import java.math.BigDecimal;
/*
* This class implements the Matusita metric distance function.
* @class Matusita implementation.
* */
public class Matusita extends DistanceFunction {
	
	/*
	 * Matusita constructor.
	 * */
	public Matusita() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Matusita distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
	    double sum1 = 0.0;
	   
	    //System.out.println("->" + s1.getSize() + " " + s2.getSize());
	    	    

		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			sum1 += (s1.getValue(x) * s2.getValue(x));
			if (sum1 >= 0) {
				sum1 = Math.sqrt(sum1);
			} else {
				return 0.0;
			}
		}
		
		//answer = Double.valueOf(formatter.format(answer));
		
		answer = ((2 * s1.getSize()) - (2 * sum1));
		if (answer >= 0) {
			answer = Math.sqrt(answer);
			return Truncate(2, answer);
		} else {
			return 0.0;
		}
		
	}
	
}
