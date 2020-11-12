import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Dataset {


    int cardinality;
    int dimensionality;
    String filename;
    Instance[] data;
    
    public Dataset() {
    	
    	int c = 0;
    	int d = 0;

    	System.out.println("Inicializing Dataset...");

		while (c == 0) {
			c = Integer.parseInt(JOptionPane.showInputDialog("Enter with the Cardinality: "));
		}
		while (d == 0) {
			d = Integer.parseInt(JOptionPane.showInputDialog("Enter with the Dimensionality: "));
		}
        setCardinality(c);
        setDimensionality(d);
    }
    
    public Dataset(int cardinality, int dimensionality) {

        setCardinality(cardinality);
        setDimensionality(dimensionality);
    }
    
    public int getCardinality() {
        return cardinality;
    }

    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }

    public int getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(int dimensionality) {
        this.dimensionality = dimensionality;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public void setNew () {
    	data = new Instance[getCardinality()];
    	for (int x = 0; x < getCardinality(); x++) {
			data[x] = new Instance();
		}
    }
    
    public void setData(Instance instance, int pos) {
    	
    	for (int x = 0; x < getDimensionality(); x++) {
    		data[pos].setValue(instance.getValue(x));
    	}
    }

    public void open() {
    	
    	JButton open = new JButton();
    	JFileChooser fc = new JFileChooser();
    	String line = new String();
    	//String ext;
		//boolean findExt = false;
    	String separator = "";
    	String filename = "C:/Users/Asus/Desktop/Doentes.txt";
    	
		//--Filename & Extension
		fc.setDialogTitle("Choose the file: ");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
			filename = fc.getSelectedFile().getAbsolutePath();
		}
		
		/*--ext
		ext = "";
		findExt = false;
		
		for (int x = 0; x < filename.length(); x++) {
			if (filename.charAt(x) == '.') {
				findExt = true;
			}
			if (findExt) {
				ext += filename.charAt(x);
			}
		}
		*/
		System.out.println(filename);
		//--
		
		//--Separator
		while (separator.isEmpty() || separator == null) {
			separator = JOptionPane.showInputDialog("Enter with the separator: ");
		}
		//--
    	
    	File arq = new File(filename);
    	
    	if (arq.exists()) {
    		
    		try {
    			
    			setNew();
    			
    			FileReader reader = new FileReader(filename);
    			BufferedReader buffer = new BufferedReader(reader);
    			
    			/*--ler a cardinalidade e dimensionalidade direto do arquivo(prototipo)
    			String allBytes = "";
    			Path path = Paths.get(filename);
    			int nSpace = 0;
    			int nComma = 0;
    			int nSemicolon = 0;
    			
    			allBytes = Files.readAllBytes(path).toString();
    			
    			for (int x = 0; x < allBytes.length(); x++) {
    				if (allBytes.charAt(x) == '\n') {
    					setCardinality(getCardinality() + 1);
    				} else
    				if (allBytes.charAt(x) == ' ') {
        				nSpace++;
        			} else
        			if (allBytes.charAt(x) == ',') {
            			nComma++;
            		} else
            		if (allBytes.charAt(x) == ';') {
                		nSemicolon++;
                	}	
    			}
    			if (nSpace > 0) {
    				separator = " ";
    				setDimensionality(nSpace / getCardinality() + 1);
    			}
    			if (nComma > 0) {
    				separator = ",";
    				setDimensionality(nComma / getCardinality() + 1);
    			}
    			if (nSemicolon > 0) {
    				separator = ";";
    				setDimensionality(nSemicolon / getCardinality() + 1);
    			}
    			/*--*/
    			
    			for (int x = 0; x < getCardinality(); x++) {
    				
    				line = buffer.readLine();
    				String value[] = line.split(separator);
    				
    				for (int y = 0; y < getDimensionality(); y++) {
    					
                        double valueD = Double.parseDouble(value[y]);
                        data[x].setValue(valueD);
                        
                    }
    				
    			}
    			
    			buffer.close();
    			
    		} catch (Exception e) {
    			System.out.print(e.getMessage());
    		}
    	}
    }
    
    public void printInstances() {
    	for (int x = 0; x < getCardinality(); x++) {
			for (int y = 0; y < data[0].getSize(); y++) {
				System.out.print(data[x].getValue(y));
				System.out.print(" ");
			}
			System.out.println();
		}
   }
   
   public Instance getInstance(int pos) {
	   return data[pos];
   }
   
   public Instance[] getData() {
	   return data;
   }
   
   public void Normalize(Instance[] dataset) {
	   
	   Instance vetMin = new Instance();
	   double value;
	   
	   if (dataset.length > 0)
	   for (int x = 0; x < dataset[0].getSize(); x++) {
		   vetMin.setValue(Double.MAX_VALUE);
	   }
	   
	   for (int x = 0; x < getCardinality(); x++) {
		   for (int y = 0; y < getDimensionality(); y++) {
			   if (dataset[x].getValue(y) < vetMin.getValue(y)) {
				   vetMin.substituiValue(y, dataset[x].getValue(y));
			   }
		   }
	   }
	   
	   for (int x = 0; x < getCardinality(); x++) {
		   for (int y = 0; y < getDimensionality(); y++) {
			   value = dataset[x].getValue(y) - vetMin.getValue(y);
			   dataset[x].substituiValue(y, value);
		   }
	   }
	   
	   for (int x = 0; x < dataset[0].getSize(); x++) {
		   vetMin.substituiValue(x, 0);
	   }
	   
	   for (int x = 0; x < getCardinality(); x++) {
		   for (int y = 0; y < getDimensionality(); y++) {
			   if (dataset[x].getValue(y) > vetMin.getValue(y)) {
				   vetMin.substituiValue(y, dataset[x].getValue(y));
			   }
		   }
	   }
	   
	   for (int x = 0; x < getCardinality(); x++) {
		   for (int y = 0; y < getDimensionality(); y++) {
			   if (vetMin.getValue(y) != 0) {
				   value = dataset[x].getValue(y) / vetMin.getValue(y);
				   dataset[x].substituiValue(y, value);
			   }
		   }
	   }
   }
}
    	

    