import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {

		RadioButton radioButtonFrame;
		

		Dataset dataset = new Dataset();
		dataset.open();
		DistanceFunction df = null;
				
		System.out.println("DATASET:");
		dataset.printInstances();

		System.out.println("Cardinality: " + dataset.getCardinality());
		System.out.println("Dimensionality: " + dataset.getDimensionality());
		
		radioButtonFrame = new RadioButton();
		radioButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		radioButtonFrame.setResizable(false);
		radioButtonFrame.setSize(200, 650);
		
		while (true) {
			radioButtonFrame.setVisible(true);
			while (radioButtonFrame.isVisible()) {try { Thread.sleep (1000); } catch (InterruptedException ex) {}}
			
			df = radioButtonFrame.getDf();
			if (df != null) {
				Print(dataset, df);
			} else {
				
				boolean[] extraOptions = radioButtonFrame.getExtraOptions();
				if (extraOptions[0]) { // Normalize
					dataset.Normalize(dataset.getData());
					dataset.printInstances();
				}
				if (extraOptions[1]) { //printStatistics
					System.out.print("Statistics == ");
					System.out.println(DistanceFunction.getStatistics() / 10);
				}
				if (extraOptions[2]) {
					Kmeans kmeans = new Kmeans(dataset, 100);
					kmeans.kmeansFunction();
					kmeans.printCluster();
				}
				if (extraOptions[3]) {
					//Kmedians Kmedians = new Kmedians();
					Kmedians kmedians = new Kmedians(dataset, 100);
					kmedians.kmediansFunction();
					kmedians.printCluster();
				}
			}
		}
	}

	public static void Print(Dataset dataset, DistanceFunction df) {
		System.out.println("");
		for (int x = 0; x < dataset.getCardinality(); x++) {
    		for (int y = 0; y < dataset.getCardinality(); y++) {
    			
    			if (x == y) {
    				System.out.print("0.00");
    				System.out.print(" | ");
    			} else {
    				System.out.print(df.getDistance(dataset.getInstance(x), dataset.getInstance(y)));
        			System.out.print(" | ");
    			}
    		}
    		
    		System.out.println();
    	}
		
	}
}
