//import java.math.BigDecimal;
/*
* This class implements the Kumar_Hassebrook metric distance function.
* @class Kumar_Hassebrook implementation.
* */
public class Kumar_Hassebrook extends DistanceFunction {
	
	/*
	 * Kumar_Hassebrook constructor.
	 * */
	public Kumar_Hassebrook() {
		
		super();
	}
	
	/*
	 * Calculates the distance between instances s1 and s2.
	 * @param s1 First instance
	 * @param s2 Second instance
	 * @return The Kumar_Hassebrook distance between s1 and s2 
	 * */
	public double getDistance(Instance s1, Instance s2) {
		
		incrementStatistics();
		double answer = 0.0;
		Produto_Interno df = new Produto_Interno();
	    double sum1 = df.getDistance(s1, s2);
	    double sum2 = 0.0;
	    double sum3 = 0.0;
	    double sum4 = df.getDistance(s1, s2);
		
		if (s1.getSize() != s2.getSize()){
			throw new IndexOutOfBoundsException("The number of S1 dimensions is " + s1.getSize() + ", but S2 has " + s2.getSize() + " dimensions.");
		}

		for (int x = 0; x < s1.getSize(); x++){
			sum2 += Math.pow(s1.getValue(x), 2);
			sum3 += Math.pow(s2.getValue(x), 2);
		}
		
		//answer = Double.valueOf(formatter.format(answer));
		
		if ((sum2 + sum3 - sum3) == 0) {
			return 0.0;
		}
		
		answer = (sum1 / (sum2 + sum3 - sum4));
		return Truncate(2, answer);
	}
	
}
