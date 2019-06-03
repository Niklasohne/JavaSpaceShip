//render Univers at the beginning
univers();


//Click listener
document.querySelector('#univers').addEventListener('click', univers);
document.querySelector('#routewählen').addEventListener('click', loader);

var way_json;

function loader() {
    var xmlreq = new XMLHttpRequest();
    xmlreq.responseType = 'text';
    xmlreq.open("GET","http://134.255.252.242:8888/test?"+document.getElementById('startIN').value+"&" +document.getElementById('endIN').value,true);
    //xmlreq.open("GET", "http://127.0.0.1:8888/test?"+document.getElementById('startIN').value+"&" +document.getElementById('endIN').value, true);
    xmlreq.onload = function () {
        way_json = JSON.parse(xmlreq.responseText);
        way();
    };
    xmlreq.send("");
}


//render way
function way() {
    var s = fetch('style.cycss').then(toText);
    Promise.all([s]).then(initWay);
}
function initWay(then) {
    var style = then[0];
    var cy = cytoscape({
            container: document.getElementById('cy'),
            layout: {
                name: 'cola'
            },
            style: style
        })
    ;

    for (var x in way_json.nodes){
        cy.add({

            data: {
                id: way_json.nodes[x].label,
                type:way_json.nodes[x].type
            }
        })
    }

    for (var x in way_json.edges){
        cy.add({
            group: 'edges',
            data: {
                source: way_json.edges[x].source,
                target: way_json.edges[x].target,
                type: false
            }
        })
    }
    for (var x in way_json.way){
        cy.add({
            group: 'edges',
            data: {
                source: way_json.way[x].source,
                target: way_json.way[x].target,
                type: true
            }
        })
    }
    console.log(cy);
    whenDoneCola(cy)
}

//render universum
function univers() {
    var cy = cytoscape({
            container: document.getElementById('cy'),
            layout: {
                name: 'circle'
            },
            style: [
                {
                    selector: 'node',
                    style: {
                        shape: 'circle',
                        'background-color': 'blue',
                    }
                }]

        })
    ;

    jQuery.getJSON('univers.json', function (data) {
        $(data.nodes).each(function (index, value) {
            cy.add({
                data: {id: index}
            })
        });

        $(data.edges).each(function (index, value) {
            cy.add({
                group: 'edges',
                data: {
                    source: value.source,
                    target: value.target,
                    type: false
                }
            })
        });
        console.log(data);
        whenDoneCircle(cy)
    });
}



//draw graf as Circle
function whenDoneCircle(cy) {
    cy.layout({name: 'circle'}).run();
}


//draw graf as cola
//function whenDoneCola(cy) {
//    cy.layout({name: 'cola'}).run();
//}

//draw graf as cola
function whenDoneCola(cy) {
    cy.layout({name: 'cola'}).run();
}


var toText = function (obj) {
    return obj.text();
};