/*
 * This class implements the L1 metric distance function.
 * @class L1 implementation.
 * */
public class L1 extends DistanceFunction {
	
	/*
	 * L1 constructor.
	 * */
	public L1() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The L1 distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		
		if (s1.getSize() != s2.getSize()){
			//throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			answer += Math.abs(s1.getValue(x) - s2.getValue(x));
		}
		
		return Truncate(2, answer);
		
	}
	
}
