require('dotenv').config();
const fs = require('fs')
const express = require('express')
const app = express()
const md5File = require('md5-file')
const mongoose = require('mongoose')
const schema = require('./mongoImage.js')
const https = require('https');
const axiosRequest = require('./module/axiosRequest.js')
const multerOption = require('./module/multerOption.js')
// const options = {
//   cert: fs.readFileSync('keys/certificate.crt'),
//   key: fs.readFileSync('keys/private.key'),
//   ca : fs.readFileSync('keys/ca.crt')
// };

// var port = 443
// https.createServer(options, app).listen(port,()=>{
//   console.log("listening on port 443")
// });

var port = 80
app.listen(port, () => {
   console.log(`Example app listening on port ${port}!`)
})

app.use(express.json());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

// mongoose.Promise = global.Promise;
// mongoose.connect(process.env.DB_URL+'predict', { useNewUrlParser: true,  useUnifiedTopology: true })
//   .then(() => console.log('Successfully connected to mongodb'))
//   .catch(e => console.error(e));


const Image = mongoose.model('Image', schema.imageSchema);
app.post('/userTrain', (request, response)=>{
  update = {}
  switch(request.body.userTrain){
    case 'Chaewon':
      update = {voteChaewon : 1}
      break
    case 'Yuri':
      update = {voteYuri : 1}
      break
    case 'Yena':
      update = {voteYena : 1}
      break
  }
  console.log(update)
  Image.findOneAndUpdate({hash:request.body.hash},{$inc: update},(err,res)=>{
    console.log(res);
  })
  response.json({test: 1})
})

app.post('/imageUpload' , multerOption.single('image'), async (requset, response)=>{
  fileName = requset.file['filename']
  console.log(fileName)
  hash = md5File.sync('userUpload/'+fileName)
  //var mongooseResult = await Image.find({hash:hash},(err,res)=>{return res})
  
  //console.log("mongooseResult : ", mongooseResult)

  if(true || mongooseResult.length===0){
    try{
      var axiosResponse = await axiosRequest('userUpload/'+fileName)
      console.log("[axiosResponse] : ", axiosResponse)
      if(axiosResponse.status == 200){
          responseData = axiosResponse.data
          var imageInfo = {
            hash:         hash,
            predictions:  responseData["predictions"],
            top:          responseData["top"],
            bottom:       responseData["bottom"],
            left:         responseData["left"],
            right:        responseData["right"]
          }
          Image.create(imageInfo)
          response.json(imageInfo)
      }
      else{
        response.json({
          hash:          hash     
        })
      }
    }catch(e){console.log("[ERROR|axiosResponse] : ",e)}
  }
  else{
    Image.update({$inc: {request : 1}},(err,res)=>{console.log(res);})
    var imageInfo = {
      success:      true,
      hash:         mongooseResult[0].hash,
      predictions:  mongooseResult[0].predictions,
      top:          mongooseResult[0].top,
      bottom:       mongooseResult[0].bottom,
      left:         mongooseResult[0].left,
      right:        mongooseResult[0].right,
      path:         fileName,
      voteChaewon:  mongooseResult[0].voteChaewon,
      voteYuri:     mongooseResult[0].voteYuri,
      voteYena:     mongooseResult[0].voteYena,
    }
    response.json(imageInfo)
    console.log("delete")
    fs.unlink('userUpload/'+fileName, (err) => {
      if (err) {
        console.error(err)
      }
    })
  }
})







