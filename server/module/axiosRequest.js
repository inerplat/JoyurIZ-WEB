const FormData = require('form-data');
const axios = require('axios')
const fs = require('fs')
const axiosRequest = async (filePath)=>{
  var newFile = fs.createReadStream(filePath);
  const formData = new FormData();
  formData.append("image", newFile, newFile.name);
  try{
    var response = await axios.create({headers: formData.getHeaders()}).post("http://localhost:5000/predict", formData)
    return response
  } catch(e){
    console.log("[ERROR|axiosRequest] ", e) 
    return e
  }
}
module.exports = axiosRequest