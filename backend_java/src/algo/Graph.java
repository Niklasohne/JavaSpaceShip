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
        start.setCurWeight(0);

        GNode cur = start;
        while (cur.getCurWeight() < finish.getCurWeight()) {
            for (Integer nachbar : cur.getConList()) {
                GNode nbar = nodes[nachbar];
                if (cur.getCurWeight() + cur.getConTo(nachbar) < nbar.getCurWeight()) {
                    nbar.setParent(cur);
                    nbar.setCurWeight(cur.getCurWeight() + cur.getConTo(nachbar));
                    aktiv.add(nbar);
                }
            }
            cur = aktiv.poll();
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

    public String getWegUndUmfeld() {
        GNode node = finish;
        Set<GNode> nodeList = new HashSet<>();
        Set<GEdge> edgeList = new HashSet<>();
        Set<GEdge> realWay = new HashSet<>();

        while (node != null) {
            for (int i : node.getConList()) {
                nodeList.add(nodes[i]);
                for (int j : nodes[i].getConList()) {
                    //for (int k : nodes[j].getConList()) {
                    //    nodeList.add(nodes[k]);
                    //    edgeList.add(new GEdge(nodes[j].getLabel(), nodes[k].getLabel(), ""));
                    //}
                    nodeList.add(nodes[j]);
                    edgeList.add(new GEdge(nodes[i].getLabel(), nodes[j].getLabel(), ""));
                }
                if (node.getParent() != null &&nodes[i].getLabel().equals(node.getParent().getLabel()))
                    realWay.add(new GEdge(node.getLabel(), nodes[i].getLabel(), ""));
                else
                    edgeList.add(new GEdge(node.getLabel(), nodes[i].getLabel(), ""));
            }
            node = node.getParent();
        }


        edgeList.removeAll(realWay);

        String out = "{ \"nodes\":[";
        int i = 0;
        int j = nodeList.size()-1;
        for (GNode g : nodeList) {
            if(g.equals(start) || g.equals(finish))
                out += "{\"label\":\"" + g.getLabel() + "\",\"type\":\"0\" }";
            else
                out += "{\"label\":\"" + g.getLabel() + "\",\"type\":\"1\" }";
            if (i++ < j) {
                out += ",";
            }
        }

        //add way to json string
        i = 0;
        j = realWay.size()-1;
        out += "], \"way\":[";
        for (GEdge g : realWay) {
            out += "{\"source\":\"" + g.getFrom() + "\" , \"target\":\"" + g.getTo() + "\", \"type\":\"0\"}";
            if (i++ < j) {
                out += ",";
            }
        }

        //add close nodes to the Map
        i = 0;
        j = edgeList.size()-1;
        out += "], \"edges\":[";
        for (GEdge g : edgeList) {
            out += "{\"source\":\"" + g.getFrom() + "\" , \"target\":\"" + g.getTo() + "\", \"type\":\"1\"}";
            if (i++ < j) {
                out += ",";
            }
        }


        out += "]}";
        return out;
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
        x += "(" + outArr[0] + ")\nAnd " + finish.getEntf() + "Units long";
        return x;
    }

}