
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author renzo
 */
public class QueryInstance {
    
    private String type = "";
    private String output = "";
    private long exectime = Long.MAX_VALUE;
    private ArrayList<Long> exectimes = new ArrayList<Long>();
    private ArrayList<String> parameters = new ArrayList<String>();
    private final int  best=1, avg=2, worst=3;
    
    public QueryInstance(String _type){
        this.type = _type;
    }
    
    String getType(){
        return this.type;
    }
            
    void setOutput(String _value){
        output = _value;
    }
    
    String getOutput(){
        return output;
    }
    
    void setExecutionTime(long _ms, boolean warmup){
        if(!warmup)
        {
            if(_ms<exectime)
                exectime = _ms;
            exectimes.add(_ms);
        }
    }
    
    long getExecutionTime(int type){
        if(type==best)
            return exectime;
        else if(type == worst)
        {
            Collections.sort(exectimes);
            return exectimes.get(exectimes.size()-1);
        }
        else
        {
            long time=0;
            for(int i = 0;i< exectimes.size();i++)
                time+=exectimes.get(i);
            return time/(exectimes.size());
        }
                    
    }

    public void setParameter(int param_number, String value) {
        parameters.add(param_number-1, value);
    }

    public String getParameter(int param_number) {
        if (param_number == 0 || param_number > parameters.size()) {
            return null;
        } else {
            return parameters.get(param_number-1);
        }
    }

    public Iterator<String> parametersIterators() {
        return parameters.iterator();
    }
    
    public int parametersNumber(){
        return parameters.size();
    }
    
    
}
