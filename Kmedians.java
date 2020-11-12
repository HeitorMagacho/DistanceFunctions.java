import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Kmedians {

	DistanceFunction df;
	Dataset dataset;
	Cluster[] cluster;
	int k;
	int maxIterations;

	public Kmedians(Dataset dataset, int maxIterations) {
		
		RadioButton radioButtonFrame;
		
		radioButtonFrame = new RadioButton();
		radioButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		radioButtonFrame.setResizable(false);
		radioButtonFrame.setSize(200, 650);	

		this.dataset = dataset;
		this.maxIterations = maxIterations;
		int[] element = chooseInstance();
		cluster = new Cluster[k];
		for (int x = 0; x < k; x++) {
			cluster[x] = new Cluster(dataset.getInstance(element[x]), dataset.getCardinality(), dataset.getDimensionality());
		}
		radioButtonFrame.setVisible(true);
		while (radioButtonFrame.isVisible()) {try { Thread.sleep (1000); } catch (InterruptedException ex) {}}
		this.df = radioButtonFrame.getDf();
	}
	
	public int[] chooseInstance() {

		int k = 0;

		while (k == 0) {
			k = Integer.parseInt(JOptionPane.showInputDialog("How many Clusters?"));
		}
		this.k = k;
		int[] element = new int[k];
		for(int x = 0; x < k; x++) {
			element[x] = -1;
			while (element[x] == -1) {
				element[x] = Integer.parseInt(JOptionPane.showInputDialog("Instance "+x+":"));
			}
		}
		return element;
	}

	public void kmediansFunction() {

		double[] r = new double[k];
		double min;
		int minPos;

		forIterations: for (int i = 0; i < maxIterations; i++) {   //Iterations
			
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
				if (calculateMedian(cluster[y]) == null) {
					break forIterations;
				} else {
					cluster[y].setHead(calculateMedian(cluster[y]));
					cluster[y].resetTop();
				}
			}
		}
	}

	public Instance calculateMedian(Cluster cluster) {

		double value = 0;
		boolean end;
		Instance median = new Instance();
		double[] vector = new double[dataset.getDimensionality()];
		int size = cluster.getTop();
//--
		for (int x = 0; x < size; x++) { //cluster size
			
			for (int y = 0; y < vector.length; y++) { //dimensionality
				vector[y] = cluster.getBody().getInstance(x).getValue(y);
			}
			
			for(int i = 0; i < cluster.getTop(); i++){
				for(int j = 0; j < cluster.getTop()-1; j++){
					if(vector[j] > vector[j + 1]){
						double aux = vector[j];
						vector[j] = vector[j+1];
						vector[j+1] = aux;
					}
				}
			}
			
			if (size % 2 == 0) {
				value = ((vector[(size/2)-1] + vector[size/2]) / 2);
			} else {
				value = (vector[(size-1)/2]);
			}
			
			median.setValue(value, x);
		}
//--
		end = checkEnd(median, cluster.getHead());

		if (end) {
			return null;
		} else {
			return median;
		}
	}

	public boolean checkEnd(Instance mean, Instance head) {

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
	
	public void cohesion() {

		double[] cohesion;

		for (int x = 0; x < k; x++) {
			cohesion = new double[cluster[x].getTop()];
			for (int y = 0; y < cluster[x].getTop(); y++) {
				cohesion[y] = df.getDistance(cluster[x].getHead(), dataset.getInstance(cluster[x].getPos().getValue(y)));
			}
			cluster[x].setCohesion(cohesion);
		}
	}

	public void separation() {

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
			cluster[x].setSeparation(df.getDistance(cluster[x].getHead(), finalAverage));
		}
	}
}