public class Kmeans {

	DistanceFunction df;
	Dataset dataset;
	Cluster[] cluster;
	int k;
	int maxIterations;
	//ProgressBar progressBar = new ProgressBar();

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

		double r;
		double min;
		int minPos;
		
		//progressBar.setVisible(true);
		//progressBar.setMaximum(k);
		for (int i = 0; i < maxIterations; i++) {
			//progressBar.setValue(0);
			System.out.println("iteration: "+(i+1));
			for (int x = 0; x < dataset.getCardinality(); x++) {
				min = (Double.MAX_VALUE);
				minPos = -1;
				//progressBar.setString("x = "+x);
				for (int y = 0; y < k; y++) {
					r = df.getDistance(cluster[y].getHead(), dataset.getInstance(x));
					if (r < min) {
						min = r;
						minPos = y;
					}
					//progressBar.upValue();
				}
				cluster[minPos].addInstance(x);
				System.out.println(".");
			}
			if (i < maxIterations-1) {
				if (hasChange()) {
					for (int y = 0; y < k; y++) {
						cluster[y].setHead(calculateAverage(cluster[y]));
						cluster[y].resetTop();
					}
				} else {
					break;
				}
			}
		}
		//progressBar.setVisible(false);
	}

	public boolean hasChange() {
		
		for (int x = 0; x < k; x++) {
			if (cluster[x].getOldPos().getSize() != cluster[x].getPos().getSize()) {
				return true;
			} else {
				for (int y = 0; y < cluster[x].getPos().getSize(); y++) {
					if (cluster[x].getPos().getValue(y) != cluster[x].getOldPos().getValue(y)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public Instance calculateAverage(Cluster cluster) {

		double value = 0;
		Instance mean = new Instance();

		for (int x = 0; x < dataset.getDimensionality(); x++) {
			value = 0;
			for (int y = 0; y < cluster.getTop(); y++) {
				value += dataset.getInstance(cluster.getPos().getValue(y)).getValue(x);
			}
			value /= cluster.getTop();
			mean.setValue(value, x);
		}
		return mean;
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

	public void printClusterInstances() {

		for (int x = 0; x < k; x++) {
			System.out.println("C" + x + ": ");
			for (int y = 0; y < cluster[x].getTop(); y++) {
				System.out.print("{");
				for (int z = 0; z < dataset.getDimensionality(); z++) {
					System.out.print(dataset.getInstance(cluster[x].getPos().getValue(y)).getValue(z));
					if (z != dataset.getDimensionality()-1) {
						System.out.print(" ");
					}
				}
				System.out.println("}");
			}
		}
	}
	
	public String outClusterInstances() {
		
		String out = "";
		for (int x = 0; x < k; x++) {
			out += ("C"+x+": ");
			for (int y = 0; y < cluster[x].getTop(); y++) {
				for (int z = 0; z < dataset.getDimensionality(); z++) {
					out += (dataset.getInstance(cluster[x].getPos().getValue(y)).getValue(z));
					if (z != dataset.getDimensionality()-1) {
						out += (",");
					}
				}
				out += ("\n");
			}
		}
		return out;
	}

	public String outCluster() {

		String out = "";
		for (int x = 0; x < k; x++) {
			out += ("C"+x+": "+cluster[x].getTop()+" ("+(double)cluster[x].getTop()*100/dataset.getCardinality()+"%)\n");
		}
		return out;
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
