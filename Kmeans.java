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
	
	public void setDF(DistanceFunction df) {
		this.df = df;
	}

	public void kmeansFunction() {

		double[] r = new double[k];
		double min;
		int minPos;
		boolean brk = false;;

		for (int i = 0; i < maxIterations; i++) {   //Iterations
			
			//@debug
			for (int x = 0; x < k; x++) {
				System.out.print("Head - C[" + x +"]: ");
				for (int y = 0; y < dataset.getDimensionality(); y++) {
					System.out.print(cluster[x].getHead().getValue(y) + " ");
				}
				System.out.println();
			}
			//--
			
			for (int x = 0; x < dataset.getCardinality(); x++) {  //Cardinality
				min = (Double.MAX_VALUE);
				minPos = -1;
				for (int y = 0; y < k; y++) {                    //Cluster size
					r[y] = df.getDistance(cluster[y].getHead(), dataset.getInstance(x));

					//@debug
					System.out.println("d(C"+y+",X"+x+"): " + r[y]);
					//--
				}

				for (int y = 0; y < k; y++) {
					if (r[y] < min) {
						min = r[y];
						minPos = y;
					}
					//tratar se for (==)
				}

				//@debug
				System.out.println("min == "+min);
				System.out.println("C["+minPos+"] <-- X"+x);
				//--

				cluster[minPos].addInstance(dataset.getInstance(x), x);
			}

			for (int y = 0; y < k; y++) {
				Instance average = calculateAverage(cluster[y]);
				if (!hasChange(average, cluster[y].getHead())) {
					cluster[y].setHead(average);
					cluster[y].resetTop();
				} else {
					brk = true;
					break;
				}
			}
			if (brk) {
				break;
			}
		}
	}

	public Instance calculateAverage(Cluster cluster) {

		double d = 0;
		Instance mean = new Instance();

		for (int x = 0; x < dataset.getDimensionality(); x++) { //dimenção
			d = 0;
			for (int y = 0; y < cluster.getTop(); y++) { // tamanho do cluster
				d += cluster.getBody().getInstance(y).getValue(x);
			}
			d /= dataset.getDimensionality();
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

	public void printCluster() {

		for (int x = 0; x < k; x++) {
			System.out.println("C" + x + ": ");
			for (int y = 0; y < cluster[x].getTop(); y++) {
				System.out.print("{");
				for (int z = 0; z < dataset.getDimensionality(); z++) {
					System.out.print(cluster[x].getBody().getInstance(y).getValue(z));
					if (z != dataset.getDimensionality()-1) {
						System.out.print(" ");
					}
				}
				System.out.println("}");
			}
		}

		/*
		for (int x = 0; x < k; x++) {
			System.out.print("Cohesion["+x+"]: {");
			for (int y = 0; y < cluster[x].getTop(); y++) {
				System.out.print(cluster[x].getCohesion()[y]);
				if (y != cluster[x].getTop()-1) {
					System.out.print(" ");
				}
			}
			System.out.println("}");

			System.out.println("Separation: " + cluster[x].getSeparation());

		}
		*/
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
