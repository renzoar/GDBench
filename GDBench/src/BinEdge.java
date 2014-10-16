/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author RobertoAntonio
 */
public class BinEdge {
    int size;
    double log;
    int counter1;
    int counter2;
    double value;
    int source_node;
    public BinEdge(int size ,int source_node) {
        this.source_node=source_node;
        this.size = size - source_node;
        this.log = Math.round(Math.log(this.size)/Math.log(2));
        this.counter1 = 0;
        this.counter2 = 0;
        this.value = 0;
        
    }
    
    public int nextCell(){
        
        if (this.counter1==0)
        {
            this.counter1++;
            return (int)Math.round(size/2) +source_node ;
        }
        
        if (counter1>= log)
            return -1;
        
        
        
        if (counter2 < Math.pow(2, counter1) && counter2!=0)
        {
            value+= (size/(Math.pow(2, counter1)));
            counter2++; 
            
            return  (int)Math.round(value) +source_node ;
        }
        else if (counter2 >= Math.pow(2, counter1))
        {
            counter2=0;
            counter1++;
        }
        
        if(counter2 == 0)
        {
            
            value = size/(Math.pow(2, counter1+1));
            if(value <1)
                return -1;
            counter2++;
        }
        
        return (int)Math.round(value) +source_node ;
    }
    
    
        
        
       
    
    
}
