public abstract class DistanceFunction {
	
	static long statistics = 0;

	protected void incrementStatistics() {
		
		statistics = statistics + 1;
	}
	
	public DistanceFunction() {
		
		resetStatistics();
	}
	
	/*
	 * truncates the answers returners in the distances functions.
	 * @param size of how many digits after the point
	 * @param value of the double that must be truncate
	 * @return The value with size digits after the point
	 * */
	public double Truncate(int size, double value) {
		value =  value * Math.pow(10, size);
		int aux = (int) value;
		value = aux / Math.pow(10, size);
		return value;
	}
	
	/*
	 * truncates the answers returners in the distances functions.
	 * @param size of how many digits after the point
	 * @param value of the double that must be truncate
	 * @param boolean that choose if rounds or not
	 * @return The value with size digits after the point
	 * */
	public double Truncate(int size, double value, boolean round) {
		
		if (!round) {
			value =  value * Math.pow(10, size);
			int aux = (int) value;
			value = aux / Math.pow(10, size);
			return value;
		} else {
			int aux = (int) (value * Math.pow(10, size + 1));
			double auxd = aux;
			auxd = auxd / 10;
			while (auxd <= 1) {
				auxd--;
			}
			auxd = auxd * 10;
			aux = (int) (value * Math.pow(10, size));
			if (auxd >= 5) {
				aux++;
			}
			value = aux / Math.pow(10, size);
			return value;
		}
		
	}
	
	public void resetStatistics() {
		
		statistics = 0;
	}

	public static long getStatistics() {
		
		return statistics;
	}
	
	abstract double getDistance(Instance s1, Instance s2);
	
}
