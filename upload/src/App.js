import axios from 'axios'; 
import ImageUploader from "react-images-upload";
import React,{Component} from 'react'; 
import './upload.css';
class App extends Component {
    constructor(props) {
    super(props);
    this.state = { predictions:[] };
    this.onDrop = this.onDrop.bind(this);
  }
  async loadFile(event){
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('preview');
      output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
    return event.target.files
  }
  async onDrop(event) {
    var pictureFiles = await this.loadFile(event)
    var canvas = document.getElementById("myCanvas");
    var ctx = canvas.getContext("2d"); 

    if(pictureFiles.length > 0){
      const formData = new FormData();
      formData.append( 
        "image", 
        pictureFiles[0],
        pictureFiles[0].name
      );
      var imagePost = async () =>{
        try{
          return await axios.post("http://localhost:8080/", formData)
        } catch(error){
          console.log(error)
        }
      }
      var response = await imagePost()
      console.log(response)
      var img = document.getElementById("preview");
      canvas.width  = img.width;
      canvas.height = img.height;
      console.log(img.width, img.height);
      var scale = Math.max(canvas.width / img.width, canvas.height / img.height);
      var x = (canvas.width / 2) - (img.width / 2) * scale;
      var y = (canvas.height / 2) - (img.height / 2) * scale;

      ctx.lineWidth = "4";
      ctx.strokeStyle = "lightgreen";
      var {top , bottom, left, right} = response.data
      console.log(top , bottom, left, right)
      ctx.rect(left*scale, top*scale, (right-left)*scale, (bottom-top)*scale);
      ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
      ctx.stroke();
      this.setState({
        predictions: [response.data.predictions]
      })
      console.log("onDrop",pictureFiles)
    }
  }
  onFileUpload = () => { 
    
  }; 
  render() {
    console.log(this.state.predictions)

    return (
      <div>
        
        <img id="preview" style={{display:'none'}}/>
        


        <div class="upload">
            <div class="upload-files">
              <header>
              <p>
              <i class="fa fa-cloud-upload" aria-hidden="true"></i>
              <span class="up">조유리즈</span>
              <span class="load">판별기</span>
              </p>
            </header>
            <div class="body" id="drop">
                <input type="file" accept="image/*" onChange={this.onDrop} />           
            </div>
            <div style={{'text-align':'center'}}>
            <canvas id="myCanvas" style={{width:'70%'}}></canvas>
            </div>
            <div style={{'text-align':'center'}}>{this.state.predictions[0]}</div>
            </div>
        </div>

        
      </div>
    );
  }
}
  
  export default App; 