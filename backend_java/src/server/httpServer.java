package server;

import algo.Graph;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jsonStuff.JsonParser;

import java.io.*;
import java.net.InetSocketAddress;

public class httpServer {

    final static private String pathJSON = "https://www.get-in-it.de/imgs/it/codingCompetition/graph/generatedGraph.json";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            System.out.println("Request from ["+t.getRemoteAddress().getAddress().toString()+"] : " + t.getRequestURI().getQuery());
            String[] param = t.getRequestURI().getQuery().split("&");
            //daksksad
            JsonParser jp = new JsonParser(pathJSON, param[0], param[1]);
            Graph g = jp.parseToGraph();
            g.findWay();


            String way = g.getWegUndUmfeld();
            System.out.println(way);
            byte [] response = way.getBytes();
            t.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST");
            t.getResponseHeaders().set("Access-Control-Allow-Headers", "Origin,Content-Type");
            t.getResponseHeaders().set("Content-Type","text");
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}

