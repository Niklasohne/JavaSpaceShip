package jsonStuff;


/*
    Json file root just for parsing
    getter needed for deserialisation
    no serialisation needed -> no setter
*/

public class JsonClass {

    private Json_Node[] nodes;
    private Json_Edge[] edges;

    public Json_Edge[] getEdges() {
        return edges;
    }

    public Json_Node[] getNodes() {
        return nodes;
    }
}
