import axios from 'axios'; 
import ImageUploader from "react-images-upload";
import React,{Component} from 'react'; 
  
class App extends Component {
    constructor(props) {
    super(props);
    this.state = { predictions:[] };
    this.onDrop = this.onDrop.bind(this);
  }
  onDrop(pictureFiles, pictureDataURLs) {

    var canvas = document.getElementById("myCanvas");
            var ctx = canvas.getContext("2d"); 

    console.log(this)
    if(pictureFiles.length > 0){
      const formData = new FormData();
      formData.append( 
        "image", 
        pictureFiles[0],
        pictureFiles[0].name
      );
      axios.post("http://localhost:8080/", formData)
      .then(function (response) {
        console.log(response)

        var img = document.getElementById("preview");
        canvas.width  = img.width;
        canvas.height = img.height;
        console.log(img.width, img.height);
        var scale = Math.max(canvas.width / img.width, canvas.height / img.height);
        var x = (canvas.width / 2) - (img.width / 2) * scale;
        var y = (canvas.height / 2) - (img.height / 2) * scale;

        ctx.lineWidth = "4";
        ctx.strokeStyle = "red";
        var {top , bottom, left, right} = response.data
        console.log(top , bottom, left, right)
        ctx.rect(left*scale, top*scale, (right-left)*scale, (bottom-top)*scale);
        ctx.drawImage(img, x, y, img.width * scale, img.height * scale);
        ctx.stroke();
        this.setState({
          predictions: [response.data.predictions]
        })
      })
      .catch(function (error) { 
        console.log(error);
      });
      console.log("onDrop",pictureFiles)
    }
  }
  onFileUpload = () => { 
    
  }; 
  render() {
    console.log(typeof(this.state.predictions))
   /* if (this.state.pictures.length > 0) {
      let renderItems = this.state.pictures.map(function(item, i) {
        return <li key={i}>{item.title}</li>
      });
    }*/
    return (
      <div>
        <ImageUploader
          withIcon={true}
          buttonText="Choose images"
          onChange={this.onDrop}
          imgExtension={[".jpg", ".gif", ".png", ".gif"]}
          maxFileSize={10242880}
          singleImage={true}
          withPreview={true}
        />

      </div>
    );
  }
}
  
  export default App; 