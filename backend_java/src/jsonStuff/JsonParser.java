package jsonStuff;


import algo.Graph;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.Debug;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonParser {

    private String pathJSON; //URL der JSON datei
    private String start; //start
    private String ziel; //ziel

    private JsonClass jsonClass;

    private ObjectMapper mapper = new ObjectMapper();

    //init a new Graph with own data
    public JsonParser(String pathJSON, String startNode, String zielNode) throws IOException {
        this.pathJSON = pathJSON;
        this.start = startNode;
        this.ziel = zielNode;
        jsonClass = mapper.readValue(new URL(pathJSON), JsonClass.class);
    }


    //take the JSON file , pars it to an Graph and returns the Graph
    public Graph parseToGraph() {

        return new Graph(jsonClass, start, ziel);
    }
}
