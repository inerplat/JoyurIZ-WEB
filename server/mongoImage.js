const mongoose = require('mongoose');

const imageSchema = new mongoose.Schema({
  hash:         { type: String, required: true, unique: true, index:true },
  top:          { type: Number, required: true },
  bottom:       { type: Number, required: true },
  left:         { type: Number, required: true },
  right:        { type: Number, required: true },
  path:         { type: String, required: true},
  predictions:  {type:String, required:true},
  voteChaewon:  {type:Number, required:true},
  voteYuri:     {type:Number, required:true},
  voteYena:     {type:Number, required:true},
  request:      {type:Number, required:true}
},
{
  timestamps: true
});

module.exports = mongoose.model('Image', imageSchema);