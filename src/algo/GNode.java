package algo;

import java.util.HashMap;
import java.util.Set;

public class GNode {

    private final static int COSTMULTIPLR = 10; // to use priority queue effizently we need int and not 0.x numbers
    private String label; //label
    private HashMap<Integer, Float> conTo = new HashMap<>(); //all connected GNodes by id
    private float curWeight = Float.MAX_VALUE; //fastest known way to the Node, if not reachable until now Float.MAX_VALUE
    private GNode parent; //parent of the node, start is root
    private boolean done = false;
    //create new node
    public GNode(String label) {
        this.label = label;
    }

    //add new edge to the node
    public void addEdge(int id, float cost) {
        this.conTo.put(id, cost*COSTMULTIPLR);
    }

    //getter
    public float getCurWeight() {
        return curWeight;
    }
    public String getLabel() {
        return label;
    }
    public GNode getParent() {
        return parent;
    }

    public float getConTo(int id){
        return conTo.get(id);// /COSTMULTIPLR;
    }

    public float getEntf(){
        return curWeight /COSTMULTIPLR;
    }

    public Set<Integer> getConList(){
        return conTo.keySet();
    }

    //setter
    public void setCurWeight(float curWeight) {
        this.curWeight = curWeight;
    }
    public void setParent(GNode parent) {
        this.parent = parent;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return label;
    }
}
