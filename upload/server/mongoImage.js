const mongoose = require('mongoose');

// Define Schemes
const imageSchema = new mongoose.Schema({
  hash: { type: String, required: true, unique: true, index:true },
  top: { type: Number, required: true },
  bottom: { type: Number, required: true },
  left: { type: Number, required: true },
  right: { type: Number, required: true },
  path: { type: String, required: true},
  predictions:{type:String, required:true}
},
{
  timestamps: true
});

// Create Model & Export
module.exports = mongoose.model('Image', imageSchema);