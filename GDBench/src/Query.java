/**
 *
 * @author renzo
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Query {

    private ArrayList<QueryInstance> instances;
    private String type = "";


    public Query(String query_type) {
        type = query_type;
        instances = new ArrayList<QueryInstance>();
    }

    public long getTotalExecTime(int IType) {
        QueryInstance instance;
        long totaltime = 0;
        Iterator<QueryInstance> it = instances.iterator();
        while(it.hasNext()){
            instance = it.next();
            totaltime = totaltime + instance.getExecutionTime(IType);
        }
        return totaltime;
    }

    public String getType(){
        return type;
    }
    
    public float getAvgExecTime(int IType) {
        long totaltime = this.getTotalExecTime(IType);
        if(instances.size()==0){
            return 0;
        }
        return (float) totaltime / instances.size();
    }

    public long getMinExecTime(int IType) {
        QueryInstance instance;
        long mintime = Long.MAX_VALUE;
        long itime;
        Iterator<QueryInstance> it = instances.iterator();
        while(it.hasNext()){
            instance = it.next();
            itime = instance.getExecutionTime(IType);
            if(itime < mintime){
                mintime = itime; 
            }
        }
        return mintime;
    }

    public long getMaxExecTime(int IType) {
        QueryInstance instance;
        long maxtime = 0;
        long itime;
        Iterator<QueryInstance> it = instances.iterator();
        while(it.hasNext()){
            instance = it.next();
            itime = instance.getExecutionTime(IType);
            if(itime > maxtime){
                maxtime = itime; 
            }
        }
        return maxtime;
    }
    
    public int CountInstances(){
        return instances.size();
    }
    
    ArrayList<QueryInstance> getInstances(){
        return instances;
    }
    
    Iterator<QueryInstance> getInstancesIterator(){
        return instances.iterator();
    }

    public void addInstance(QueryInstance instance) {
        instances.add(instance);
    }

    double getVarExecTime(int IType) {
        QueryInstance instance;
        double totaltime = 0.0;
        Iterator<QueryInstance> it = instances.iterator();
        while(it.hasNext()){
            instance = it.next();
            totaltime += Math.pow((instance.getExecutionTime(IType)-getAvgExecTime(IType)),2);
        }
        return totaltime/instances.size();
    }

    double getStdDExecTime(int IType) {
        return Math.sqrt(getVarExecTime(IType));
    }
    
}
