
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author renzo
 */
public class TestDataInstance {


    private String type = "";
    private ArrayList<String> values = new ArrayList<String>();
    
    
    public TestDataInstance(String _type){
        this.type = _type;
    }
    
    String getType(){
        return this.type;
    }
            
    public void setValue(int param_number, String value) {
        values.add(param_number-1, value);
    }

    public String getValue(int param_number) {
        if (param_number == 0 || param_number > values.size()) {
            return null;
        } else {
            return values.get(param_number-1);
        }
    }

    public Iterator<String> parametersIterators() {
        return values.iterator();
    }
    
    public int parametersNumber(){
        return values.size();
    }
        
        
    
    
}
