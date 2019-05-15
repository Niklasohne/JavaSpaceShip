import algo.Graph;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonStuff.JsonParser;

import java.io.IOException;

public class Main {

    //URL der JSON datei
    final static private String pathJSON = "https://www.get-in-it.de/imgs/it/codingCompetition/graph/generatedGraph.json";
    static private ObjectMapper mapper = new ObjectMapper();

    static private String standart_start = "Erde"; //standart_start
    static private String standart_ziel = "b3-r7-r4nd7"; //standart_ziel
    //static private String standart_ziel = "node_875"; //standart_ziel



    //Testversion , returns the fastest way from Erde to b3-r7-r4nd7
    public static void main(String[] args) throws IOException {

        long t1 = System.currentTimeMillis();
        JsonParser jp = new JsonParser(pathJSON, standart_start, standart_ziel);
        long t2 = System.currentTimeMillis();
        System.out.println("[Downloading Json File] :" + (float)(t2-t1)/1000 + "sec");


        t1 = System.currentTimeMillis();
        Graph g = jp.parseToGraph();
        t2 = System.currentTimeMillis();
        System.out.println("[Parsing Json to Graph Modell] :" +(float)(t2-t1)/1000 + "sec");

        //just for testing how much faster an termination after finding Finish is
        Graph g2 = jp.parseToGraph();


        //Graph kartographieren komplett
        //t1 = System.currentTimeMillis();
        //g2.kartograph();
        //t2 = System.currentTimeMillis();
        //System.out.println("[kartograph Graph] :" + (float)(t2-t1)/1000 + "sec");

        //Graph durchsuchen bis ziel gefunden
        t1 = System.currentTimeMillis();
        g.findWay();
        t2 = System.currentTimeMillis();
        System.out.println("[find fastest way from \""+standart_start+"\" to \"" + standart_ziel+ "\"] :" + (float)(t2-t1)/1000 + "sec");


        //kürzester weg
        System.out.println("\n\n" + g.wegToString());
    }
}

//a