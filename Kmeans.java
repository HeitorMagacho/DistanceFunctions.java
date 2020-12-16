public class Kmeans {

	DistanceFunction df;
	Dataset dataset;
	Cluster[] cluster;
	int k;
	int maxIterations;

	public Kmeans(Dataset dataset, int maxIterations, int[] element) {

		this.dataset = dataset;
		this.maxIterations = maxIterations;
		this.k = element.length;
		cluster = new Cluster[k];
		for (int x = 0; x < k; x++) {
			cluster[x] = new Cluster(dataset.getInstance(element[x]), dataset.getCardinality(), dataset.getDimensionality());
		}
	}
	
	public Kmeans(Dataset dataset, int maxIterations, Instance[] element) {

		this.dataset = dataset;
		this.maxIterations = maxIterations;
		this.k = element.length;
		cluster = new Cluster[k];
		for (int x = 0; x < k; x++) {
			cluster[x] = new Cluster(element[x], dataset.getCardinality(), dataset.getDimensionality());
		}
	}

	public void setDF(DistanceFunction df) {
		this.df = df;
	}

	public void kmeansFunction() {

		double[] r = new double[k];
		double min;
		int minPos;
		boolean hc = true;

		for (int i = 0; i < maxIterations; i++) {   //Iterations
			System.out.println("iteration: "+i);
			for (int x = 0; x < dataset.getCardinality(); x++) {  //Cardinality
				min = (Double.MAX_VALUE);
				minPos = -1;
				for (int y = 0; y < k; y++) {  //Cluster size
					r[y] = df.getDistance(cluster[y].getHead(), dataset.getInstance(x));

					//@debug
					//System.out.println("d(C"+y+",X"+x+"): " + r[y]);
					//--
				}

				for (int y = 0; y < k; y++) {
					if (r[y] < min) {
						min = r[y];
						minPos = y;
					}
				}

				//@debug
				//System.out.println("min == "+min);
				//System.out.println("C["+minPos+"] <-- X"+x);
				//--

				cluster[minPos].addInstance(dataset.getInstance(x), x);
			}

			hc = false;

			if (i < maxIterations-1) {
				for (int y = 0; y < k; y++) {
					Instance average = calculateAverage(cluster[y]);
					if ((!hasChange(average, cluster[y].getHead())) && (i < maxIterations)) {
						cluster[y].setHead(average);
					} else {
						i = maxIterations;
						hc = true;
					}
				}
				if (!hc) {
					for (int y = 0; y < k; y++) {
						cluster[y].resetTop();
					}
				}
			}
		}
	}
	
	public void printCentroid() {
		for (int x = 0; x < k; x++) {
			for (int y = 0; y < dataset.getDimensionality(); y++) {
				System.out.print(cluster[x].getHead().getValue(y));
				if (y != dataset.getDimensionality()-1) {
					System.out.print(",");
				}
			}
			System.out.println();
		}
	}
	
	public String outCentroid() {
		
		String out = "";
		for (int x = 0; x < k; x++) {
			for (int y = 0; y < dataset.getDimensionality(); y++) {
				out += cluster[x].getHead().getValue(y);
				if (y != dataset.getDimensionality()-1) {
					out += ",";
				} else {
					out += "\n";
				}
			}
		}
		return out;
	}

	public Instance calculateAverage(Cluster cluster) {

		double d = 0;
		Instance mean = new Instance();

		for (int x = 0; x < dataset.getDimensionality(); x++) { //dimenção
			d = 0;
			for (int y = 0; y < cluster.getTop(); y++) { // tamanho do cluster
				d += dataset.getInstance(cluster.getPos().getValue(y)).getValue(x);
			}
			d /= cluster.getTop();
			mean.setValue(d, x);
		}
		return mean;
	}

	public boolean hasChange(Instance mean, Instance head) {

		boolean end = true;

		for (int x = 0; x < dataset.getDimensionality(); x++) {
			if (mean.getValue(x) != head.getValue(x)) {
				end = false;
			}
		}

		return end;
	}

	public void printClusterInstaneces() {

		for (int x = 0; x < k; x++) {
			System.out.println("C" + x + ": ");
			for (int y = 0; y < cluster[x].getTop(); y++) {
				System.out.print("{");
				for (int z = 0; z < dataset.getDimensionality(); z++) {
					System.out.print(dataset.getInstance(cluster[x].getPos().getValue(y)).getValue(x));
					if (z != dataset.getDimensionality()-1) {
						System.out.print(" ");
					}
				}
				System.out.println("}");
			}
		}
	}
	
	public void printCluster() {
		
		for (int x = 0; x < k; x++) {
			System.out.println("C"+x+": "+cluster[x].getTop()+" ("+(double)cluster[x].getTop()*100/dataset.getCardinality()+"%)");
		}
	}

	public double cohesion() {

		double cohesion = 0;

		for (int x = 0; x < k; x++) {
			for (int y = 0; y < cluster[x].getTop(); y++) {
				cohesion += df.getDistance(cluster[x].getHead(), dataset.getInstance(cluster[x].getPos().getValue(y)));
			}
		}

		return cohesion;
	}

	public double separation() {

		double separation = 0;
		double d;
		Instance finalAverage = new Instance();

		for (int x = 0; x < dataset.getDimensionality(); x++) {
			d = 0;
			for (int y = 0; y < dataset.getCardinality(); y++) {
				d = dataset.getInstance(y).getValue(x);
			}
			d /= dataset.getCardinality();
			finalAverage.setValue(d);
		}

		for (int x = 0; x < k; x++) {
			separation += (df.getDistance(cluster[x].getHead(), finalAverage));
		}
		
		return separation;
	}
}
