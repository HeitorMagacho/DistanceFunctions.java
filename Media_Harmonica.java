//import java.math.BigDecimal;
/*
* This class implements the Media_Harmonica metric distance function.
* @class Media_Harmonica implementation.
* */
public class Media_Harmonica extends DistanceFunction {
	
	/*
	 * Media_Harmonica constructor.
	 * */
	public Media_Harmonica() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Media_Harmonica distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			answer += ((s1.getValue(x) * s2.getValue(x)) / (s1.getValue(x) + s2.getValue(x)));
		}
		
		//answer = Double.valueOf(formatter.format(answer));

		return 2 * Truncate(2, answer);
	}
	
}
