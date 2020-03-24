const request = require('request')
const fs = require('fs')
const multer = require('multer')
const express = require('express')
const app = express()
const uuid4 = require('uuid4')
const path = require('path');
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});


const uuid = () => {
    const tokens = uuid4().split('-')
    return tokens[2] + tokens[1] + tokens[0] + tokens[3] + tokens[4]
}
var storage = multer.diskStorage({
    destination : function(req, file, cb){    
      cb(null, 'userUpload/');
    },
    filename : function(req, file, cb){
      var mimeType;
      switch (file.mimetype) {
        case "image/jpeg":
          mimeType = "jpg";
        break;
        case "image/png":
          mimeType = "png";
        break;
        case "image/gif":
          mimeType = "gif";
        break;
        case "image/bmp":
          mimeType = "bmp";
        break;
        default:
          mimeType = "null";
        break;
      }
      cb(null, uuid() + "." + mimeType);
    }
  });
  
  var upload = multer({
        storage: storage,
        fileFilter: function (req, file, callback) {
            var ext = path.extname(file.originalname);
            if(ext !== '.png' && ext !== '.jpg' && ext !== '.gif' && ext !== '.jpeg') {
                return callback(new Error('Only images are allowed'))
            }
            callback(null, true)
        },
        limits:{
            fileSize: 1024 * 1024 * 10
        }
});

app.post('/' , upload.single('image'), (req, res)=>{
    type = req.file.mimetype.split('/')
})
var port = 8080
app.listen(port, () => {
    console.log(`Example app listening on port ${port}!`)
//    doRequest()
})

var files = {
    image: fs.createReadStream('Yuri562.png')
}
function doRequest(url) {
    return new Promise((resolve, reject)=>{
        request.post({ url: 'http://127.0.0.1:5000/predict', formData: files}, (error, res, body)=>{
            console.log(res)
            console.log(body)
        })
    });
    
}
