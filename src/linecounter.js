

const path = __dirname

var _getAllFilesFromFolder = function(dir) {
  
      var filesystem = require("fs");
      var results = [];
  
      filesystem.readdirSync(dir, "utf8").forEach(function(file) {
  
          file = dir+'/'+file;
          var stat = filesystem.statSync(file);
  
          if (stat && stat.isDirectory()) {
              results = results.concat(_getAllFilesFromFolder(file))
          } else results.push(file);
  
      });
  
      return results;
  
  };


  
var totalLines = 0
_getAllFilesFromFolder(path).forEach(element => {
  var fs = require('fs');
  var array = fs.readFileSync(element).toString().split("\n");
  for(i in array) {
      console.log(array[i]);
      totalLines++;
  }
});

console.log(`total lines: ${totalLines}`)