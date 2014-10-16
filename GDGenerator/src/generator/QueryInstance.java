package generator;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author renzo
 */
public class QueryInstance {

    private String type = "";
    private ArrayList<String> parameters = new ArrayList<String>();

    public QueryInstance(String _type) {
        this.type = _type;
    }

    public String getType() {
        return this.type;
    }

    public void addParameter(String _value) {
        parameters.add(_value);
    }

    public String getParameter(int n) {
        if (n == 0 || n > parameters.size()) {
            return null;
        } else {
            return parameters.get(n-1);
        }
    }

    public Iterator<String> parametersIterators() {
        return parameters.iterator();
    }
    
    public int parametersNumber(){
        return parameters.size();
    }
    
}
