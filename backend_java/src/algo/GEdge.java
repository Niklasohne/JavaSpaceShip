package algo;

public class GEdge {

    private String from;
    private String to;
    private String cost;

    public GEdge(String from, String to, String cost){
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
