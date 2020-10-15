var fetch = require('node-fetch');
var express = require('express');
var router = express.Router();
var fs = require('fs');

var ppath ="/Users/apple/Desktop/mt/usc/571/hw8/backendjs/public";

var gglCrednkey = "";
var forecastKey = "";
var picSearchId = "";

//  Cross-siteHTTPrequest
router.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods", "POST,GET");
    res.header("X-Powered-By", '3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
});


//  http://localhost:3000/apis/get_location?street=1246 W 30th St&city=Los Angeles&state=CA
router.get('/get_location', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://maps.googleapis.com/maps/api/geocode/json?address=${q.street},${q.city},${q.state}&key=` + gglCrednkey;
    // res.send(express.static('public/resourceJson/getlocation.json'));

    // fs.readFile('../public/resourceJson/getlocation.json', function(err, data){
    var file =ppath+"/resourceJson/getlocation.json";
   
    res.sendFile(file);
    // var file = '../public/resourceJson/getimage.json';

    // res.send(file);
    console.log("get location");
})

//  http://localhost:3000/apis/get_weather?lat=37.8267&lng=-122.4233
router.get('/get_weather', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://api.darksky.net/forecast/` + forecastKey + `/${q.lat},${q.lng}`;
    // fetch(url)
    //     .then(res => res.json())
    //     .then(json => res.send(json));
     var file =ppath+"/resourceJson/getweather.json";
   
    res.sendFile(file);
    console.log("get weather");
})

//  http://localhost:3000/apis/get_detail?lat=37.8267&lng=-122.4233&time=1572570337
router.get('/get_detail', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://api.darksky.net/forecast/` + forecastKey + `/${q.lat},${q.lng},${q.time}`;
    // fetch(url)
    //     .then(res => res.json())
    //     .then(json => res.send(json));
     var file =ppath+"/resourceJson/getdetail.json";
   
    res.sendFile(file);
    console.log("get detail");
})

//  http://localhost:3000/apis/get_image?state=California
router.get('/get_image', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://www.googleapis.com/customsearch/v1?q=c${q.state}%20State%20Seal&cx=` + picSearchId + `&imgSize=huge&imgType=news&num=1&searchType=image&key=` + gglCrednkey;
    // fetch(url)
    //   .then(res => res.json())
    //   .then(json => res.send(json));
     var file =ppath+"/resourceJson/getimage.json";
   
    res.sendFile(file);
    console.log("get image");
})

//  http://localhost:3000/apis/get_autocomplete?input=Los
router.get('/get_autocomplete', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${q.input}&types=(cities)&language=en&key=` + gglCrednkey;
    // fetch(url)
    //     .then(res => res.json())
    //     .then(json => res.send(json));
     var file =ppath+"/resourceJson/getautocomplete.json";
   
    res.sendFile(file);
    console.log("get auto complete");
})

//  http://localhost:3000/apis/get_scenery_photo?city=Losangeles
router.get('/get_scenery_photo', function(req, res) {
    const q = req.query;
    console.log(q);
    var url = `https://www.googleapis.com/customsearch/v1?q=${q.city}&cx=` + picSearchId + `&imgSize=huge&imgType=news&num=8&searchType=image&key=` + gglCrednkey;
    // fetch(url)
    //   .then(res => res.json())
    //   .then(json => res.send(json));
     var file =ppath+"/resourceJson/getsceneryphoto.json";
   
    res.sendFile(file);
    console.log("get image");
})


module.exports = router;