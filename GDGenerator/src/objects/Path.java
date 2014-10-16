/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.LinkedHashSet;

/**
 *
 * @author renzo
 */
public class Path {

    private LinkedHashSet<Long> nodes;
    private long source = 0;
    private long target = 0;

    public Path(long source_node) {
        this.source = source_node;
        this.target = source_node;
        nodes = new LinkedHashSet<Long>();
        nodes.add(source);
    }

    public long getSourceNode(){
        return this.source;
    }
    
    public long getTargetNode() {
        return this.target;
    }

    public void addNode(long new_node) {
        if (nodes.add(new_node)) {
            target = new_node;
        }
    }
    
    public int getPathSize(){
        return nodes.size() - 1;
    }
    
}
