var fetch = require('node-fetch');
var express = require('express');
var app = express();

var gglCrednkey = "";
var forecastKey = "";
var picSearchId = "";

app.use(express.static("dist/hw8front"));

//  Cross-siteHTTPrequest
app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods", "POST,GET");
    res.header("X-Powered-By", '3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
});
//  http://localhost:3000/server/getbylocation?street=1246 W 30th St&city=Los Angeles&state=CA
app.get('/getbylocation', function(req, res) {
    const q = req.query;
    console.log(q);
    var geoData = getLocation(q.street, q.city, q.state);
    // console.log(geoData);
    geoData.then(data => senddata(data,res));
})

async function getLocation(street, city, state) {
    var url = `https://maps.googleapis.com/maps/api/geocode/json?address=` + encodeURIComponent(street) + `,` + encodeURIComponent(city) + `,` + encodeURIComponent(state) + `&key=` + gglCrednkey;
    const res = await fetch(url);
    const result = await res.json();
    // console.log(result.results[0].geometry.location);
    return result;
    // console.log("get location");
}

function senddata(data,res) {
    // console.log(data);
    var lat = data.results[0].geometry.location.lat;
    var lng = data.results[0].geometry.location.lng;
    var url = 'https://api.darksky.net/forecast/' + forecastKey +'/'+ lat+ ','+lng;
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get weather");
}

//  http://localhost:3000/server/get_location?street=1246 W 30th St&city=Los Angeles&state=CA
app.get('/get_location', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(q.street)},${encodeURIComponent(q.city)},${encodeURIComponent(q.state)}&key=` + gglCrednkey;
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get location");
})

//  http://localhost:3000/server/get_weather?lat=37.8267&lng=-122.4233
app.get('/get_weather', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://api.darksky.net/forecast/` + forecastKey + `/${q.lat},${q.lng}`;
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get weather");
})

//  http://localhost:3000/server/get_detail?lat=37.8267&lng=-122.4233&time=1572570337
app.get('/get_detail', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://api.darksky.net/forecast/` + forecastKey + `/${q.lat},${q.lng},${q.time}`;
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get detail");
})

//  http://localhost:3000/server/get_image?state=California
app.get('/get_image', function(req, res) {
    console.log("get image");
    const q = req.query;
    console.log(q);
    var url = `https://www.googleapis.com/customsearch/v1?q=${q.state}%20State%20Seal&cx=` + picSearchId + `&imgSize=huge&imgType=news&num=1&searchType=image&key=` + gglCrednkey;
    console.log("getimage link:"+url);
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get image");
})

//  http://localhost:3000/server/get_autocomplete?input=Los
app.get('/get_autocomplete', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${q.input}&types=(cities)&language=en&key=` + gglCrednkey;
    fetch(url)
        .then(res => res.json())
        .then(json => res.send(json));
    console.log("get auto complete");
})

//  http://localhost:3000/server/get_scenery_photo?city=Losangeles
app.get('/get_scenery_photo', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://www.googleapis.com/customsearch/v1?q=${encodeURIComponent(q.city)}&cx=` + picSearchId + `&imgSize=huge&imgType=news&num=8&searchType=image&key=` + gglCrednkey;
    fetch(url)
      .then(res => res.json())
      .then(json => res.send(json));

    console.log("get scenery photos"+url);
})

module.exports = app;


const PORT = process.env.PORT|| 8081
app.listen(PORT,function() {
    console.log("App started on port 8081");
});