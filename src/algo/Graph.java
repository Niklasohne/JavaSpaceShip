package algo;

import jsonStuff.JsonClass;
import jsonStuff.Json_Edge;

import java.util.*;


public class Graph {


    //list of all nodes
    private GNode[] nodes;

    //id(placde in node array) of start/finish node
    private GNode start;
    private GNode finish;

    //Compare the Weights of 2 Nodes
    private static Comparator<GNode> fway = (o1, o2) -> (int) (o1.getCurWeight() - o2.getCurWeight());


    //getter
    public GNode[] getNodes() {
        return nodes;
    }

    public GNode getFinish() {
        return finish;
    }

    public GNode getStart() {
        return start;
    }


    public Graph(JsonClass jsonClass, String startLabel, String stopLabel) {
        nodes = new GNode[jsonClass.getNodes().length];

        //Translate json nodes to GNodes
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new GNode(jsonClass.getNodes()[i].getLabel());
            if (nodes[i].getLabel().equals(startLabel))
                start = nodes[i];
            if (nodes[i].getLabel().equals(stopLabel))
                finish = nodes[i];
        }


        //Add edges bidirectional to the nodes
        for (int i = 0; i < jsonClass.getEdges().length; i++) {
            Json_Edge cur = jsonClass.getEdges()[i];
            nodes[cur.getSource()].addEdge(cur.getTarget(), cur.getCost());
            nodes[cur.getTarget()].addEdge(cur.getSource(), cur.getCost());
        }
    }


    //go throuh whole graph to find fastest way to finishNode
    public void findWay() {

        PriorityQueue<GNode> aktiv = new PriorityQueue<>(fway);

        aktiv.add(start);
        start.setCurWeight(0);

        int opCount = 0;
        while (!aktiv.isEmpty()) {
            //System.out.println("[" +opCount++ + "] :" + aktiv);
            GNode cur = aktiv.poll();
            for (Integer nachbar : cur.getConList()) {
                if (cur.getCurWeight() + cur.getConTo(nachbar) < nodes[nachbar].getCurWeight()) {
                    nodes[nachbar].setParent(cur);
                    nodes[nachbar].setCurWeight(cur.getCurWeight() + cur.getConTo(nachbar));
                    aktiv.add(nodes[nachbar]);
                    nodes[nachbar].setDone(false);
                }
            }
            cur.setDone(true);
            if (isRDY(finish))
                return;
        }
    }

    public void kartograph() {

        PriorityQueue<GNode> aktiv = new PriorityQueue<>(fway);
        aktiv.add(start);
        start.setCurWeight(0);

        while (!aktiv.isEmpty()) {
            GNode cur = aktiv.poll();
            for (Integer nachbar : cur.getConList()) {
                if (cur.getCurWeight() + cur.getConTo(nachbar) < nodes[nachbar].getCurWeight()) {
                    nodes[nachbar].setParent(cur);
                    nodes[nachbar].setCurWeight(cur.getCurWeight() + cur.getConTo(nachbar));
                    aktiv.add(nodes[nachbar]);
                }
            }
        }
    }


    //returns true if all to the finish node connectet modes are done -> the fastest way now is 100% the fastest way
    public boolean isRDY(GNode n) {
        if (!n.isDone())
            return false;
        for (Integer nachbar : n.getConList()) {
            if (!nodes[nachbar].isDone()) {
                return false;
            }
        }
        return true;
    }


    //returns way to the finish node
    public String wegToString() {
        GNode cur = finish;

        List<String> out = new ArrayList<>();
        while (cur != null) {
            out.add(cur.getLabel());
            cur = cur.getParent();
        }
        String[] outArr = new String[out.size()];
        out.toArray(outArr);
        String x = "THE FASTEST WAY IS: \n";
        for (int i = 0; i < outArr.length - 1; i++) {
            x += "(" + outArr[outArr.length - 1 - i] + ") -> ";
        }
        x += "("+outArr[0] + ")\nAnd " + finish.getEntf() + "Units long";
        return x;
    }

}