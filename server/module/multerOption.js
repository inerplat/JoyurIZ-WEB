const path = require('path');
const multer = require('multer')
const uuid4 = require('uuid4')
const uuid = () => {
    const tokens = uuid4().split('-')
    return tokens[2] + tokens[1] + tokens[0] + tokens[3] + tokens[4]
}
const multerOption = multer({
    storage: multer.diskStorage({
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
    }),
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
})
module.exports = multerOption
