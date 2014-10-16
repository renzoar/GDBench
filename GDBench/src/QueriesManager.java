
/**
 *
 * @author renzo
 */
import java.io.File;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueriesManager {

    private ArrayList<QueryInstance> querymix = new ArrayList<QueryInstance>();
    private ArrayList<TestDataInstance> testdata = new ArrayList<TestDataInstance>();
    private ArrayList<QueryInstance> instancesQ1 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ2 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ3 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ4 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ5 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ6 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ7 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ8 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ9 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ10 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ11 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ12 = new ArrayList<QueryInstance>();
    private ArrayList<QueryInstance> instancesQ13 = new ArrayList<QueryInstance>();
    private final int  random=1, interval=2, media=3;
    private final int seed = 80808080;
    private Random rand;
    public QueriesManager() {
        testdata = new ArrayList<TestDataInstance>();
        rand=new Random(seed);
    }
    public void putTdi(TestDataInstance tdi)
    {
        testdata.add(tdi);
    }
 
    public boolean loadTestData(int dataType,int query_number,long persons, long webpages, TestDriver td) {
        TestDataInstance tdi = null;
        
        //personID
        for (int i = 1 ; i <=query_number ; i++) {
            if (dataType==this.interval)
            {
                tdi = new TestDataInstance("personID");
                String value1= String.valueOf((persons/query_number)*i);
                tdi.setValue(1, value1);
                testdata.add(tdi);
                tdi = new TestDataInstance("personName");
                value1= td.Q4(Long.valueOf(value1));
                tdi.setValue(1, value1);
                testdata.add(tdi);
            }
            else if(dataType== this.media)
            {
                tdi = new TestDataInstance("personID");
                String value1= String.valueOf(((persons/2)-(query_number/2))+i);
                tdi.setValue(1, value1);
                testdata.add(tdi);
                tdi = new TestDataInstance("personName");
                value1= td.Q4(Long.valueOf(value1));
                tdi.setValue(1, value1);
                testdata.add(tdi);
            }
            else
            {
                tdi = new TestDataInstance("personID");
                String value1= String.valueOf(rand.nextInt((int)persons)+1);
                tdi.setValue(1, value1);
                testdata.add(tdi);
                tdi = new TestDataInstance("personName");
                value1= td.Q4(Long.valueOf(value1));
                tdi.setValue(1, value1);
                testdata.add(tdi);
            }
        }
        //personID2
        if(dataType== this.media)
        {
            for (int i = 1 ; i <=query_number*4 ; i++) {
                tdi = new TestDataInstance("personID2");
                String value1= String.valueOf(((persons/4)-(query_number*2))+i);
                tdi.setValue(1, value1);
                testdata.add(tdi);
            }
        }
        
        //WebPage
        for (int i = 1 ; i <=query_number ; i++) {
            tdi = new TestDataInstance("webpageID");
            if (dataType==this.interval)
            {
                String value1= String.valueOf((webpages/query_number)*i+persons);
                tdi.setValue(1, value1);
            }
            else if(dataType== this.media)
            {
                String value1= String.valueOf(((webpages/2)-(query_number/2))+i+persons);
                tdi.setValue(1, value1);                
            }
            else
            {
                String value1= String.valueOf(Math.round(rand.nextInt((int)webpages)+1)+persons);
                tdi.setValue(1, value1);
            }
            testdata.add(tdi);
        }
        //like
        for (int i = 1 ; i <=query_number ; i++) {
            tdi = new TestDataInstance("like");
            if (dataType==this.interval)
            {
                String value1= String.valueOf((persons/query_number)*i);
                String value2= String.valueOf((webpages/query_number)*i+persons);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else if(dataType== this.media)
            {
                String value1= String.valueOf(((persons/2)-(query_number/2))+i);                
                String value2= String.valueOf(((webpages/2)-(query_number/2))+i+persons);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else
            {
                String value1= String.valueOf(Math.round(rand.nextInt((int)persons)+1));
                String value2= String.valueOf(Math.round(rand.nextInt((int)webpages)+1)+persons);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            testdata.add(tdi);
        }
        //friend
        
        long medSup = persons/4;
        long medInf = 3*(persons/4);
        int id=1;
        BinEdge be = new BinEdge((int)persons, id);
        for (int i = 1 ; i <=query_number ; i++) {
            tdi = new TestDataInstance("friend");
            if (dataType==this.interval)
            {
                long node= (persons/query_number)*i;
                long value = node +((persons-node)/4);
                String value1= String.valueOf(node);
                String value2= String.valueOf(value);
                
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else if(dataType== this.media)
            {
                long node1=0;
                long node2=0;
                while (node1 <medSup || node1>medInf ||   node2 <medSup || node2>medInf)
                {
                    node1= be.nextCell()+1;
                    node2= be.nextCell()+1;
                    id+=2;
                    be = new BinEdge((int)persons, id);
                    
                    
                }
                
                String value1= String.valueOf(node1);                
                String value2= String.valueOf(node2);    
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else
            {
                String value1= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                String value2= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            testdata.add(tdi);
        }
        //like common
        for (int i = 1 ; i <=query_number ; i++) {
            tdi = new TestDataInstance("commonlike");
            if (dataType==this.interval)
            {
                long node= (persons/query_number)*i;
                long value = node +1;
                String value1= String.valueOf(node);
                String value2= String.valueOf(value);
                
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else if(dataType== this.media)
            {
                long node= (persons/2-query_number/2+1)+2*i-2;
                long value = node +1;
                String value1= String.valueOf(node);
                String value2= String.valueOf(value);
                
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else
            {
                String value1= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                String value2= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            testdata.add(tdi);
        }
        //path
        for (int i = 1 ; i <=query_number ; i++) {
            tdi = new TestDataInstance("path");
            if (dataType==this.interval)
            {
                String value1= String.valueOf((persons/query_number)*i);
                String value2= String.valueOf((persons)-(persons/query_number)*i+1);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else if(dataType== this.media)
            {
                String value1= String.valueOf(((persons/2)-(query_number/2))+i);                
                String value2= String.valueOf(((persons/2)-(query_number/2))-i);   
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            else
            {
                String value1= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                String value2= String.valueOf(Math.round(rand.nextInt((int)persons))+1);
                tdi.setValue(1, value1);
                tdi.setValue(2, value2);
            }
            testdata.add(tdi);
        }
        
        
        
        
//        Document doc = null;
//        try {
//            File fXmlFile = new File(filename);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            doc = dBuilder.parse(fXmlFile);
//            doc.getDocumentElement().normalize();
//            NodeList nList;
//
//            TestDataInstance tdi;
//            String type;
//            String value1;
//            String value2;
//            Element eElement;
//            nList = doc.getElementsByTagName("data");
//            for (int j = 0; j < nList.getLength(); j++) {
//                eElement = (Element) nList.item(j);
//                type = eElement.getAttribute("type");
//                value1 = eElement.getAttribute("value1");
//                value2 = eElement.getAttribute("value2");
//                tdi = new TestDataInstance(type);
//                if (value1 != null) {
//                    tdi.setValue(1, value1);
//                }
//                if (value2 != null) {
//                    tdi.setValue(2, value2);
//                }
//                testdata.add(tdi);
//            }
////aqui hay que cargar los datos generados
//            
//            
            return true;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
    }
    public ArrayList<Long> getPersonIds()
    {
        ArrayList<Long> personId = new ArrayList();
        TestDataInstance tdi;
        Iterator<TestDataInstance> it1;
        it1 = testdata.iterator();

        while (it1.hasNext()) {
            tdi = it1.next();
            if (tdi.getType().compareTo("personID2") == 0) 
                personId.add(Long.valueOf(tdi.getValue(1)));
        }
        return personId;
    }

    public void makeBasicQueryMix(int dataType) {
        TestDataInstance tdi;
        QueryInstance instance;
        int testDataCounter = 0;
        Iterator<TestDataInstance> it1;
        
        it1 = testdata.iterator();

        while (it1.hasNext()) {
            tdi = it1.next();

            if (tdi.getType().compareTo("personID") == 0) {
                testDataCounter++;

                instance = new QueryInstance("Q3");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ3.add(instance);

                instance = new QueryInstance("Q4");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ4.add(instance);
                
                
                instance = new QueryInstance("Q7");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ7.add(instance);

                instance = new QueryInstance("Q12");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ12.add(instance);
                
                if(dataType!=media){
                    instance = new QueryInstance("Q5");
                    instance.setParameter(1, tdi.getValue(1));
                    instancesQ5.add(instance);

                    instance = new QueryInstance("Q6");
                    instance.setParameter(1, tdi.getValue(1));
                    instancesQ6.add(instance);

                    instance = new QueryInstance("Q13");
                    instance.setParameter(1, tdi.getValue(1));
                    instancesQ13.add(instance);
                }
            }

            if (tdi.getType().compareTo("personName") == 0) {
                instance = new QueryInstance("Q1");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ1.add(instance);
            }

            if (tdi.getType().compareTo("webpageID") == 0) {
                instance = new QueryInstance("Q2");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ2.add(instance);
            }

            if (tdi.getType().compareTo("friend") == 0) {
                instance = new QueryInstance("Q10");
                instance.setParameter(1, tdi.getValue(1));
                instance.setParameter(2, tdi.getValue(2));
                instancesQ10.add(instance);

            }
            if (tdi.getType().compareTo("commonlike") == 0) {
                
                instance = new QueryInstance("Q11");
                instance.setParameter(1, tdi.getValue(1));
                instance.setParameter(2, tdi.getValue(2));
                instancesQ11.add(instance);
            }

            if (tdi.getType().compareTo("path") == 0) {
                instance = new QueryInstance("Q8");
                instance.setParameter(1, tdi.getValue(1));
                instance.setParameter(2, tdi.getValue(2));
                instancesQ8.add(instance);

                instance = new QueryInstance("Q9");
                instance.setParameter(1, tdi.getValue(1));
                instance.setParameter(2, tdi.getValue(2));
                instancesQ9.add(instance);
            }
            
            //Consultas generadas segun tecnica de la mediana
           
            if (tdi.getType().compareTo("FOF") == 0 && dataType==media) {
                testDataCounter++;
                instance = new QueryInstance("Q5");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ5.add(instance);
            }
            if (tdi.getType().compareTo("FOW") == 0 && dataType==media) {
                testDataCounter++;

                instance = new QueryInstance("Q6");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ6.add(instance); 
                
            } 
            if (tdi.getType().compareTo("FOFOF") == 0 && dataType==media) {
                testDataCounter++;
                instance = new QueryInstance("Q13");
                instance.setParameter(1, tdi.getValue(1));
                instancesQ13.add(instance);
            }

        }
    }

    public ArrayList<QueryInstance> makeQueryMix(int instances_for_query, String type) {
        querymix = new ArrayList<QueryInstance>();
        this.copyInstances(instancesQ1, instances_for_query);
        this.copyInstances(instancesQ2, instances_for_query);
        this.copyInstances(instancesQ3, instances_for_query);
        this.copyInstances(instancesQ4, instances_for_query);
        this.copyInstances(instancesQ5, instances_for_query);
        this.copyInstances(instancesQ6, instances_for_query);
        this.copyInstances(instancesQ7, instances_for_query);
        this.copyInstances(instancesQ8, instances_for_query);
        this.copyInstances(instancesQ9, instances_for_query);
        this.copyInstances(instancesQ10, instances_for_query);
        this.copyInstances(instancesQ11, instances_for_query);
        this.copyInstances(instancesQ12, instances_for_query);
        this.copyInstances(instancesQ13, instances_for_query);
        if(type.compareTo("r")==0){
            Collections.shuffle(querymix);
        }
        return querymix;
    }
    
    public Iterator<QueryInstance> getQueryMixIterator(){
        return querymix.iterator();
    }
    
    
    private void copyInstances(ArrayList<QueryInstance> instances, int instances_for_query){
        Iterator<QueryInstance> it;
        if (!instances.isEmpty()) {
            it = instances.iterator();
            for (int i = 1; i <= instances_for_query; i++) {
                if (!it.hasNext()) {
                    it = instances.iterator();
                }
                querymix.add(it.next());
            }
        }
    }
    
    
    public ArrayList<QueryInstance> makeQueryMixByQuery(int query, int instances_for_query, String type) {
        querymix = new ArrayList<QueryInstance>();
        if(query == 1){
            this.copyInstances(instancesQ1, instances_for_query);
        }
        if(query == 2){
            this.copyInstances(instancesQ2, instances_for_query);
        }
        if(query == 3){
            this.copyInstances(instancesQ3, instances_for_query);
        }
        if(query == 4){
            this.copyInstances(instancesQ4, instances_for_query);
        }
        if(query == 5){
            this.copyInstances(instancesQ5, instances_for_query);
        }
        if(query == 6){
            this.copyInstances(instancesQ6, instances_for_query);
        }
        if(query == 7){
            this.copyInstances(instancesQ7, instances_for_query);
        }
        if(query == 8){
            this.copyInstances(instancesQ8, instances_for_query);
        }
        if(query == 9){
            this.copyInstances(instancesQ9, instances_for_query);
        }
        if(query == 10){
            this.copyInstances(instancesQ10, instances_for_query);
        }
        if(query == 11){
            this.copyInstances(instancesQ11, instances_for_query);
        }
        if(query == 12){
            this.copyInstances(instancesQ12, instances_for_query);
        }
        if(query == 13){
            this.copyInstances(instancesQ13, instances_for_query);
        }
        
        if(type.compareTo("r")==0){
            Collections.shuffle(querymix);
        }
        return querymix;
    }

    public Query getQueriesByType(String type) {
        Query query = new Query(type);
        QueryInstance instance;
        Iterator<QueryInstance> it = querymix.iterator();
        while (it.hasNext()) {
            instance = it.next();
            if (type.compareTo(instance.getType()) == 0) {
                query.addInstance(instance);
            }
        }
        return query;
    }

    public int getQueryMixSize() {
        return querymix.size();
    }
    
    public long getTotalExecTime(int type){
        long ttime = 0;
        QueryInstance instance;
        Iterator<QueryInstance> it = querymix.iterator();
        while (it.hasNext()) {
            instance = it.next();
            ttime += instance.getExecutionTime(type);
        }
        return ttime;
    }
    
}
