const FormData = require('form-data');
const axios = require('axios')
const fs = require('fs')
const axiosRequest = async (filePath)=>{
  var newFile = fs.createReadStream(filePath);
  const formData = new FormData();
  formData.append("image", newFile, newFile.name);
  try{
    var response = await axios.create({headers: formData.getHeaders()}).post("http://joyuriz-api:5000/predict", formData)
    return response.data
  } catch(e){
    console.log("[ERROR|axiosRequest] ", e) 
    return error
  }
}
module.exports = axiosRequest