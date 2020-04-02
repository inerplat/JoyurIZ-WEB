import axios from 'axios'; 
import React,{Component, useMemo} from 'react';
import { css } from "@emotion/core";
import PropagateLoader from "react-spinners/PropagateLoader";
import './upload.css';
import AnimatedModal from "./userTrain";
import Dropzone from 'react-dropzone'
import Banner from 'react-js-banner';
import Button from '@material-ui/core/Button';
const override = css`
  display: block;
  margin: 0 auto;
  border-color: red;
`;
const dropzoneStyle = {
  flex: 1,
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  padding: '20px',
  borderWidth: 2,
  borderRadius: 2,
  borderColor: '#eeeeee',
  borderStyle: 'dashed',
  backgroundColor: '#fafafa',
  color: '#bdbdbd',
  outline: 'none',
  transition: 'border .24s ease-in-out',
  height: '100%'
}
class App extends Component {
    constructor(props) {
    super(props);
    this.state = { 
      predictions:    [],
      loading:        false,
      fileName:       '',
      hash:           '',
      vote:           [],
      showResult:     false,
      bannerStatus:   false
    };
    this.onDrop = this.onDrop.bind(this);
  }

  async onDrop(event) {
    console.log(event)
    var pictureFiles = event
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('preview');
      output.src = reader.result;
    };
    reader.readAsDataURL(pictureFiles[0]);
    var canvas = document.getElementById("myCanvas");
    this.setState({
      predictions:    [],
      loading:        true,
      fileName:       '',
      hash:           '',
      vote:           [],
      showResult:     false,
      bannerStatus:   false
    })
    var ctx = canvas.getContext("2d"); 
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    console.log(pictureFiles.length)
    if(pictureFiles.length > 0){
      const formData = new FormData();
      formData.append( 
        "image", 
        pictureFiles[0],
        pictureFiles[0].name
      );
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
        ctx.lineWidth = "5";
        ctx.strokeStyle = "lightgreen";
        var {top , bottom, left, right} = response.data
        ctx.rect(left*scale, top*scale, (right-left)*scale, (bottom-top)*scale);
        ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
        ctx.stroke();
            
        this.setState({
          predictions: [((text)=>{
            return ({'Yuri': '조유리', 'Yena': '최예나', 'Chaewon': '김채원'})[text]
          })(response.data.predictions)
        ],
          loading :     false,
          fileName:     response.data.path,
          hash:         response.data.hash,
          vote:         [response.data.voteChaewon, response.data.voteYuri, response.data.voteYena],
          showResult:   true
        })
      }
      else{
        ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
        this.setState({
          predictions: ["fail to find"],
          loading : false,
          fileName: response.data.path,
          hash:     response.data.hash
        })
      }
    }
  }

  render() {
    
    return (
      <div style={{height:'100%'}}>
        <Banner 
          title="응답이 제출되었습니다. "
          css={{width:'100%', color: "#000", backgroundColor: "lightGreen", fontFamily: "arial", margin:'0', position:'absolute'}} 
          showBanner={this.bannerStatus}
        />
      <div className="bodyDiv">

        <img id="preview" style={{display:'none'}}/>
        
        <div className="upload">
            <div className="upload-files">
              
          <div style={{float:'right'}}>
          <Button style={{position:'absolute',}}>test</Button></div>
              <header>
              <p>
              <span className="up">조유리즈</span>
              <span className="load">판별기</span>
              </p>
            </header>
            <div className="body" id="drop">
            

              <div style={{textAlign:'center', margin:'10px',position:'relative'}}>
                <canvas id="myCanvas" style={{position:'relative',width:'70%'}}></canvas>
                <div style={{position: 'absolute', top:'70%', left:'47%'}}>
                    <PropagateLoader
                      css={override}
                      size={25}
                      color={"#FF509F"}
                      loading={this.state.loading}
                    />
                </div>
              </div>
              
              { 
                !this.state.loading && !this.state.showResult ?(
                <div style={{width:'80%', height:'50%', marginLeft:'auto', marginRight:'auto', position:'absolute', top:'30%', left:'10%'}}>
                  <Dropzone multiple={false} onDrop={acceptedFiles => this.onDrop(acceptedFiles)}>
                    {({getRootProps, getInputProps}) => (
                      <section style={{height:'100%'}}>
                        <div {...getRootProps(this.style)} style={dropzoneStyle}>
                          <input className="dropzone" {...getInputProps({
                            type:'file',
                            accept:'image/*'
                          })} />
                          <div style={{textAlign:'center', position:'relative', top:'30%'}}>
                          <p>업로드할 파일을 드래그하거나</p> <p>박스를 <span style={{color:'lightBlue'}}>클릭</span>해주세요</p>
                          </div>
                        </div>
                      </section>
                    )}
                  </Dropzone>
                </div>
              ) : null
            }
            </div>
            { 
              this.state.showResult ?(
                <div style={{textAlign:'center'}}>분석 결과 : {this.state.predictions[0]}</div>
              ) : null
            }
            </div>
            {
              this.state.showResult ?(
                <div style={{textAlign:'center'}}>
                  <AnimatedModal fileName={this.state.fileName} hash={this.state.hash} prediction={this.state.predictions[0]}/>
                </div>
              ) : null
            }
        </div>  

      </div>
      </div>
    );
  }
}
  
export default App; 

