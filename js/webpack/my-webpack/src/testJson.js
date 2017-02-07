import "babel-polyfill";
import data from "./testJson.json";


console.log("-----data",data);
global.testJsonData = data;
window.testJsonWin = data;