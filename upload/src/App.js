import axios from 'axios'; 
import React,{Component} from 'react';
import { css } from "@emotion/core";
import PropagateLoader from "react-spinners/PropagateLoader";
import './upload.css';
import AnimatedModal from "./userTrain";
const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;

class App extends Component {
    constructor(props) {
    super(props);
    this.state = { 
      predictions:[],
      loading: false,
      fileName: ''
    };
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
    this.setState({
      predictions:[],
      loading: true,
      fileName: ''
    })
    var ctx = canvas.getContext("2d"); 
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    if(pictureFiles.length > 0){
      const formData = new FormData();
      formData.append( 
        "image", 
        pictureFiles[0],
        pictureFiles[0].name
      );
      console.log(pictureFiles[0].name)
      var imagePost = async () =>{
        try{
          return await axios.post("http://localhost:8080/imageUpload", formData)
        } catch(error){
          console.log(error)
        }
      }
      var response = await imagePost()
      var img = document.getElementById("preview");
      canvas.width  = img.width;
      canvas.height = img.height;
      var scale = Math.max(canvas.width / img.width, canvas.height / img.height);
      var x = (canvas.width / 2) - (img.width / 2) * scale;
      var y = (canvas.height / 2) - (img.height / 2) * scale;
      if(response.data.success === true){
        ctx.lineWidth = "4";
        ctx.strokeStyle = "lightgreen";
        var {top , bottom, left, right} = response.data
        ctx.rect(left*scale, top*scale, (right-left)*scale, (bottom-top)*scale);
        ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
        ctx.stroke();
        this.setState({
          predictions: [((text)=>{
            switch(text){
              case 'Yuri':
                return '조유리'
              case 'Yena':
                return '최예나'
              case 'Chaewon':
                return '김채원'
            }
          })(response.data.predictions)
        ],
          loading : false,
          fileName: response.data.path
        })
      }
      else{
        ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
        this.setState({
          predictions: ["fail to find"],
          loading : false,
          fileName: response.data.path
        })
      }
    }
  }
  onFileUpload = () => { 
    
  }; 
  render() {
    return (
      <div>
        <img id="preview" style={{display:'none'}}/>
        
        <div className="upload">
            <div className="upload-files">
              <header>
              <p>
              <span className="up">조유리즈</span>
              <span className="load">판별기</span>
              </p>
            </header>
            <div className="body" id="drop" style={{height:'100%'}}>
            
            <div><input type="file" accept="image/*" onChange={this.onDrop} /></div>
            
            <div style={{textAlign:'center', margin:'10px',position:'relative'}}>
              
              <canvas id="myCanvas" style={{position:'relative',width:'70%'}}></canvas>
              <div style={{position: 'absolute', top:'50%', left:'50%'}}>
                  <PropagateLoader
                    css={override}
                    size={25}
                    color={"#FF509F"}
                    loading={this.state.loading}
                  />
              </div>

            </div>

            </div>
            <div style={{textAlign:'center'}}>{this.state.predictions[0]}</div>
            
            </div>
            <div style={{textAlign:'center'}}>
            <AnimatedModal fileName={this.state.fileName}/>
            </div>
        </div>

      </div>
    );
  }
}
  
  export default App; 