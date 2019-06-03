# JavaSpaceShip

just try out: www.dies-das-ananas.eu

usable on Desktop and Smartphone (but Smartphone on it's lagging at the moment)

if server is down please contact me (niklas@schno.net)

if you want to start your own server, download the b3r7r4nd7.jar file to your server, start it with the command line. upload all files in the frontend-directory (excluded the .idea folder) to your apache Server.... Done 

## Funktion of the Backend

### httpServer.java

Opens an http Server

request your Way with request url `http://134.255.252.242:8888/test?Erde&node_874`

get the Awnser as JSON 

### Main.java, Class to test the funktions

Find the fastest way from Planet A to Planet B
An Bertrandt Coding Challange Projekt

Work in Progress, basic functionality already implemented

Fastest Way : Erde -> 810 -> 595 -> 132 -> 519 -> 71 -> 432 -> b3-r7-r4nd7
Length: 2.995688 Units

```
[Downloading Json File] :1.165sec
[Parsing Json to Graph Modell] :0.005sec
[find fastest way from "Erde" to "b3-r7-r4nd7"] :0.001sec

THE FASTEST WAY IS: 
(Erde) -> (node_810) -> (node_595) -> (node_132) -> (node_519) -> (node_71) -> (node_432) -> (b3-r7-r4nd7)
And 2.995688 Units long

Process finished with exit code 0
```


### used Lib

jackson (core/annotations/databind) for JSON parsing


## Funktion of the Frontend

Userinterface to search for the way

written in js, css, html

plott the whole univers as Graph 

plott the way as Graph

BUT(!) the length of the edges are not eqivalent to the cost of the edge

### used libs
```
<script src="https://unpkg.com/cytoscape/dist/cytoscape.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bluebird/3.5.0/bluebird.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fetch/2.0.3/fetch.min.js"></script>
<script src="https://unpkg.com/webcola/WebCola/cola.min.js"></script>
<script src="https://unpkg.com/weaverjs@1.2.0/dist/weaver.min.js"></script>
```