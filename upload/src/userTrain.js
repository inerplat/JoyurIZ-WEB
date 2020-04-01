import React from 'react';
import Button from '@material-ui/core/Button';
import Modal from 'react-modal';
import './upload.css';
const customStyles = {
    content : {
      top                   : '50%',
      left                  : '50%',
      right                 : 'auto',
      bottom                : 'auto',
      marginRight           : '-50%',
      transform             : 'translate(-50%, -50%)'
    },
    overlay : {
        backgroundColor: 'rgba(0, 0, 0, 0.5)'
    }
};
Modal.setAppElement('#root')
export default function AnimatedModal(props) {
    const [modalIsOpen,setIsOpen] = React.useState(false);
    function openModal() {
      setIsOpen(true);
    }
    function closeModal(){
      setIsOpen(false);
    }
    function clickChaewon(){
        console.log(props)
        setIsOpen(false);
    }
    return (
        <div>
            <Button variant="contained" color="secondary" onClick={openModal}>
                틀렸어요 ㅜ.ㅜ
            </Button>
            <Modal
                closeTimeoutMS={500}
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
                >
                <div>
                    <h2>정답을 알려주세요</h2>
                    <p>
                        지금 입력하신 답변은 추후에 반영됩니다.
                    </p>
                    <Button variant="contained" onClick={clickChaewon} style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#cee5d5'}}>김채원</Button>
                    <Button variant="contained" style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#f3aa51'}}>조유리</Button>
                    <Button variant="contained" style={{fontFamily:'NanumBarunGothic', margin:'5px',backgroundColor:'#fcf695'}}>최예나</Button>
                </div>
            </Modal>
        </div>
    );
}