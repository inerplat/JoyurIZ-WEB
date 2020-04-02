import React from 'react';
import { IconButton, Button } from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
import Modal from 'react-modal';
import './upload.css';
import axios from 'axios'; 
const customStyles = {
    content : {
      top                   : '50%',
      left                  : '50%',
      right                 : 'auto',
      bottom                : 'auto',
      marginRight           : '-50%',
      width: '35%',
      transform             : 'translate(-50%, -50%)',
      padding               : '30px'
    },
    overlay : {
        backgroundColor: 'rgba(0, 0, 0, 0.5)'
    }
};
Modal.setAppElement('#root')
export default function AnimatedModal(props) {
    const [incorrectModalOpen,incorrectModalSet] = React.useState(false);
    const [modalIsOpen2,setIsOpen2] = React.useState(false);
    async function click(name){
        console.log(props)
        console.log(name)
        var result = await imagePost(name)
        console.log(result)
        incorrectModalSet(false);
    }
    async function imagePost(who){
        if(props.fileName !==''){
            console.log(props)
            try{
                return await axios.post("http://localhost:8080/userTrain", {
                    'fileName':       props.fileName,
                    'userTrain':      who,
                    'hash':           props.hash
                })
            } catch(error){
            console.log(error)
            }
        }
        else{
            console.log('당신은,,정말,,나쁜,,사람이예요')
        }
    }
    return (
        <div>
            <Button variant="contained" style={{backgroundColor:'lightGreen', margin:'5px'}} onClick={e=>setIsOpen2(true)}>정답입니다</Button>
            <Modal
                closeTimeoutMS={500}
                isOpen={modalIsOpen2}
                onRequestClose={e=>setIsOpen2(false)}
                style={customStyles}
                contentLabel="Example Modal"
                >
                
                <div>
                    <h2>정답을 알려주세요</h2>
                    <p>
                        hi
                    </p>
                </div>
            </Modal>
            <Button variant="contained" style={{margin:'5px'}} color="secondary" onClick={e=>incorrectModalSet(true)}>
                틀렸어요 ㅜ.ㅜ
            </Button>
            <Modal
                closeTimeoutMS={500}
                isOpen={incorrectModalOpen}
                onRequestClose={e=>incorrectModalSet(false)}
                style={customStyles}
                contentLabel="Example Modal"
                >
                    <div style={{float:'right'}}><IconButton style={{position:'absolute',left:'85%', top:'5%'}}>
   <CloseIcon/>
</IconButton></div>
                <div>
                    <h2>정답을 알려주세요</h2>
                    <p>
                        지금 입력하신 답변은 추후에 반영됩니다.
                    </p>
                    <div>
                    <Button variant="contained" onClick={e=>click('Chaewon')} style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#cee5d5'}}>김채원</Button>
                    <Button variant="contained" onClick={e=>click('Yuri')} style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#f3aa51'}}>조유리</Button>
                    <Button variant="contained" onClick={e=>click('Yaena')} style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#fcf695'}}>최예나</Button>
                    </div>
                </div>
            </Modal>
        </div>
    );
}