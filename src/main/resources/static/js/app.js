//定义地图服务URL


let  mapboxUrl = 'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw';


let mapVersion = 'Map data &copy'

// 定义两个图层，影像图层和街道图层（这里是有了mapbox地图服务）
var satellite = L.tileLayer(mapboxUrl, { id: 'mapbox.satellite',attribution: mapVersion});
// var  streets = L.tileLayer(mapboxUrl, { id: 'mapbox.streets'});   //街道图有问题：样式库不再支持

let map = L.map('map',{
    center: [25,116.2],
    zoom: 6,
    layers: [satellite]
});

// 通过layer control来实现图层切换UI
// https://leafletjs.com/examples/layers-control/
let baselayer = {
    "影像图": satellite,
    // "街道图": streets
}
let transButton = L.control.layers(baselayer).addTo(map);
let land = pugongyingTyphon[0]["land"][0];
// console.log([land]);

// 定义台风图标
var typhoonIcon = L.icon({
    iconUrl: '../img/typhoon.png',
    iconSize: [28, 28],
    iconAnchor: [10, 10]
});
// L.marker([13.80, 143.30], {icon: typhoonIcon}).addTo(map)
// .bindPopup( "<b  />" + pugongyingTyphon[0]["name"] + '<br> 纬度：' + land['lat'] + '<br>经度：' +land['lng'] );


function datahandler() {
    // 获取台风数组对象包含xy
    var lineTyphoon = [];
    var forecast = pugongyingTyphon[0]['points'];
    // console.table(forecast);
    for (let i = 0; i < forecast.length;i++)
    {
        // 获取数组对象
        let p = forecast[i];
        // 数组传值
        lineTyphoon.push([Number (p['lat']),Number(p['lng'])]);
    }
    // 返回值
    return (lineTyphoon);
}

let resultPoint = datahandler();
// console.table(resultPoint)

let lineLayer;
let pointLayer;
function animateDraw(){
    var length = resultPoint.length;

    var drawPoint = [];

    var count =0;

    var timer = setInterval(function () {
            if (count <= length) {
                drawPoint.push(resultPoint[count]);
                count++;

                if (lineLayer && count !== length){
                    map.removeLayer(lineLayer);
                    lineLayer = null;
                }
                lineLayer = L.polyline(drawPoint,{ color: 'blue'}).addTo(map);
                pointLayer =  L.marker(drawPoint[count-1], {icon: typhoonIcon}).addTo(map)
            }else {
                clearInterval(timer);
            }


        }
        ,100);
    // animateDraw();

}
// animateDraw();
// 一次性绘制曲线
// polylineDraw = L.polyline(resultPoint,{ color: 'blue'}).addTo(map);
//
// map.fitBounds(polylineDraw.getBounds());