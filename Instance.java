
import java.util.*;

public class Instance {

    int id;
    public Vector<Double> data = new Vector<Double>();

    public Instance() {

        setId(0);
    }

    public Instance(int oid) {

        setId(oid);
    }

    public Instance(int oid, Vector<Double> data) {

        setId(oid);
        setData(data);
    }

    public int getSize() {

        return data.size();
    }

    public double getValue(int pos) {

        return data.get(pos);
    }

    public void setValue(double value, int pos) {
        if (pos <= data.size()) {
            data.add(pos, value);
        }
    }

    public void setValue(double value) {
        data.addElement(value);
    }
    
    public void substituiValue(int pos, double value) {
        if (pos <= data.size()) {
        	data.setElementAt(value, pos);
        }
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Double> getData() {
        return data;
    }

    public void setData(Vector<Double> data) {
        this.data = data;
    }

}
