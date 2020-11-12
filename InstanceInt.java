
import java.util.*;

public class InstanceInt {

    int id;
    public Vector<Integer> data = new Vector<Integer>();

    public InstanceInt() {

        setId(0);
    }

    public InstanceInt(int oid) {

        setId(oid);
    }

    public InstanceInt(int oid, Vector<Integer> data) {

        setId(oid);
        setData(data);
    }

    public int getSize() {

        return data.size();
    }

    public int getValue(int pos) {

        return data.get(pos);
    }

    public void setValue(int value, int pos) {
        if (pos <= data.size()) {
            data.add(pos, value);
        }
    }

    public void setValue(int value) {
        data.addElement(value);
    }
    
    public void substituiValue(int pos, int value) {
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

    public Vector<Integer> getData() {
        return data;
    }

    public void setData(Vector<Integer> data) {
        this.data = data;
    }

}
