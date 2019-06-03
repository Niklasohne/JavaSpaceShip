package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class httpServer {

    public static String index =
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Navi</title>\n" +
            "</head>\n" +
            "\n" +
            "<style>\n" +
            "\n" +
            "    #interface{\n" +
            "        width: 15%;\n" +
            "        height: 100%;\n" +
            "        position: absolute;\n" +
            "        top: 0px;\n" +
            "        left: 0%;\n" +
            "    }\n" +
            "\n" +
            "    #cy {\n" +
            "        width: 85%;\n" +
            "        height: 100%;\n" +
            "        position: absolute;\n" +
            "        top: 0px;\n" +
            "        left: 15%;\n" +
            "    }\n" +
            "</style>\n" +
            "\n" +
            "<body>\n" +
            "<div id =\"interface\">\n" +
            "    <table>\n" +
            "        <tr>\n" +
            "            <th scope=\"col\">Start</th>\n" +
            "            <th scope=\"col\"><input type=\"text\"> </th>\n" +
            "        </tr>\n" +
            "        <tr>\n" +
            "            <th scope=\"col\">Ziel</th>\n" +
            "            <th scope=\"col\"><input type=\"text\"> </th>\n" +
            "        </tr>\n" +
            "    </table>\n" +
            "    <button type=\"button\" id=\"routewählen\" >Route suchen</button>\n" +
            "    <button type=\"button\" id=\"univers\" >Zeige das ganze Universum</button>\n" +
            "</div>\n" +
            "<div id=\"cy\"></div>\n" +
            "<script src=\"https://unpkg.com/cytoscape/dist/cytoscape.min.js\"></script>\n" +
            "<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>\n" +
            "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/bluebird/3.5.0/bluebird.js\"></script>\n" +
            "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/fetch/2.0.3/fetch.min.js\"></script>\n" +
            "<script src=\"https://unpkg.com/webcola/WebCola/cola.min.js\"></script>\n" +
            "\n" +
            "\n" +
            "<script src=\"cytoscape-cola.js\"></script>\n" +
            "<script src=\"graphvis.js\"></script>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = index;
            t.getResponseHeaders().add("Content-Type" ,"text/html");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();

            //os.write("HTTP/1.1 200 OK \n".getBytes());
            //os.write("Content-Type: text/html".getBytes());
            os.write(response.getBytes());
            os.close();
        }
    }

}

