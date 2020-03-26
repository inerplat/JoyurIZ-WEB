require('dotenv').config();
const request = require('request')
const fs = require('fs')
const multer = require('multer')
const express = require('express')
const app = express()
const uuid4 = require('uuid4')
const path = require('path');
const md5File = require('md5-file/promise')
const mongoose = require('mongoose')
const schema = require('./mongoImage.js') 
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

const uuid = () => {
    const tokens = uuid4().split('-')
    return tokens[2] + tokens[1] + tokens[0] + tokens[3] + tokens[4]
}

mongoose.Promise = global.Promise;
mongoose.set('useFindAndModify', true);
mongoose.connect(process.env.DB_URL, { useNewUrlParser: true,  useUnifiedTopology: true })
  .then(() => console.log('Successfully connected to mongodb'))
  .catch(e => console.error(e));


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
const Image = mongoose.model('Image', schema.imageSchema);

app.post('/' , upload.single('image'), (requset, response)=>{
  fileName = requset.file['filename']
  console.log(fileName)
  md5File('userUpload/'+fileName)
  .then(hash => {
    Image.find({hash:hash},(err,res)=>{
      console.log(res);
      if(res.length===0){
        doRequest('http://127.0.0.1:5000/predict', 5000, 'userUpload/'+fileName)
        .then(result=>{
          var imageInfo = {
            hash:         hash,
            predictions:  result["predictions"],
            top:          result["top"],
            bottom:       result["bottom"],
            left:         result["left"],
            right:        result["right"],
            path:         'userUpload/' + fileName
          }
          Image.create(imageInfo)
          response.json(imageInfo)
        })
      }
      else{
        var imageInfo = {
          hash:         res[0].hash,
          predictions:  res[0].predictions,
          top:          res[0].top,
          bottom:       res[0].bottom,
          left:         res[0].left,
          right:        res[0].right,
          path:         'userUpload/' + fileName
        }
        console.log(imageInfo)
        response.json(imageInfo)
        console.log("delete")
        fs.unlink('userUpload/'+fileName, (err) => {
          if (err) {
            console.error(err)
          }
        })
      }
    
    });
  })
})

var port = 8080
app.listen(port, () => {
    console.log(`Example app listening on port ${port}!`)
//    doRequest()
})


function doRequest(url, port, filePath) {
  return new Promise((resolve, reject)=>{
      var options = {
          method: "POST",
          url: url, 
          port: port,
          headers: {
              "Content-Type": "multipart/form-data"
          },
          formData : {
              "image" : fs.createReadStream(filePath)
          }
      };
      request.post(options, (error, res, body)=>{
        resolve(JSON.parse(body))
      })
  }); 
}
