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
      width: '270px',
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
    async function click(name){
        await imagePost(name)
        incorrectModalSet(false);
        props.clear()
        props.banner({bannerStatus:true})
    }
    async function imagePost(who){
        if(props.fileName !==''){
            try{
                return await axios.post("https://joyuriz.shop/userTrain", {
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
        <div className="userTrain">
            <Button className="wrong" variant="contained" color="secondary" onClick={e=>incorrectModalSet(true)}>
                틀렸어요 ㅜ.ㅜ
            </Button>
            <Modal
                closeTimeoutMS={500}
                isOpen={incorrectModalOpen}
                onRequestClose={e=>incorrectModalSet(false)}
                style={customStyles}
                contentLabel="Example Modal">
                <div id="exitButton">
                    <IconButton id="exitIcon" size="small" onClick={e=>incorrectModalSet(false)}>
                        <CloseIcon/>
                    </IconButton>
                </div>
                <div className="member">
                    <h2>정답을 알려주세요</h2>
                    <p>
                        지금 입력하신 답변은 추후에 반영됩니다.
                    </p>
                    <div>
                    <Button id="chaewon" variant="contained" onClick={e=>click('Chaewon')}>김채원</Button>
                    <Button id="yuri" variant="contained" onClick={e=>click('Yuri')}>조유리</Button>
                    <Button id="yena" variant="contained" onClick={e=>click('Yena')}>최예나</Button>
                    </div>
                </div>
            </Modal>
        </div>
    );
}