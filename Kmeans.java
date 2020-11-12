import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Kmeans {

	DistanceFunction df;
	Dataset dataset;
	Cluster[] cluster;
	int k;
	int maxIterations;

	public Kmeans(Dataset dataset, int maxIterations) {
		
		RadioButton radioButtonFrame;
		
		radioButtonFrame = new RadioButton();
		radioButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		radioButtonFrame.setResizable(false);
		radioButtonFrame.setSize(200, 500);

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
	
	public void setDF(DistanceFunction df) {
		this.df = df;
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

	public void kmeansFunction() {

		double[] r = new double[k];
		double min;
		int minPos;

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
				if (calculateAverage(cluster[y]) == null) {
					i = maxIterations;
				} else {
					cluster[y].setHead(calculateAverage(cluster[y]));
					cluster[y].resetTop();
				}
			}
		}
	}

	public Instance calculateAverage(Cluster cluster) {

		double d = 0;
		boolean end;
		Instance mean = new Instance();

		for (int x = 0; x < dataset.getDimensionality(); x++) { //dimenção
			d = 0;
			for (int y = 0; y < cluster.getTop(); y++) { // tamanho do cluster
				d += cluster.getBody().getInstance(y).getValue(x);
			}
			d /= dataset.getDimensionality();
			mean.setValue(d, x);
		}

		end = checkEnd(mean, cluster.getHead());

		if (end) {
			return null;
		} else {
			return mean;
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