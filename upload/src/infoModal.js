import React from 'react';
import Button from '@material-ui/core/Button';
import Modal from 'react-modal';
import './upload.css';
import ReactGA from 'react-ga';
const trackingId = 'UA-148371899-1'
ReactGA.initialize(trackingId);
const customStyles = {
    content : {
      top                   : '50%',
      left                  : '50%',
      right                 : 'auto',
      bottom                : 'auto',
      marginRight           : '-50%',
      transform             : 'translate(-50%, -50%)',
      padding               : '20px'
    },
    overlay : {
        backgroundColor: 'rgba(0, 0, 0, 0.5)'
    }
};
Modal.setAppElement('#root')
export default function AnimatedModal(props) {
    const [modalIsOpen,setIsOpen] = React.useState(true);
    function closeModal(){
      setIsOpen(false);
      ReactGA.event({category: 'infoModal', action: 'closeModal'});
    }
    return (
        <div className="initModal">
            <Modal
                closeTimeoutMS={500}
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                style={customStyles}
                contentLabel="Example Modal"
                shouldCloseOnOverlayClick={false}
                shouldCloseOnEsc={false}
                >
                <div>
                    <div style={{textAlign:'center'}}><h1><span>조유리즈 판별기</span></h1></div>
                    <ul>
                        <li><div>본 서비스는 '김채원', '조유리', '최예나' 중 <strong>가장 닮은</strong> 사람을 알려주는 웹 페이지 입니다.</div></li>
                        <li><div>업로드한 모든 사진은 <strong>딥러닝 모델을 학습</strong> 하는데 사용될 수 있습니다.</div></li>
                        <li><div>첨부파일의 최대 용량은 <strong>10MB</strong>로 제한되어 있습니다.</div></li>
                        <li><div>파일 확장자는 <strong>jpg, png, gif</strong>만 가능하며, <strong>gif</strong>파일은 첫번째 프레임을 분석에 사용합니다.</div></li>
                        <li><div><strong>얼굴이 1개</strong>인 사진만 분석을 시도합니다.</div></li>
                        <li><div>기타 문의사항은 <a href="mailto:inerplat@gmail.com">inerplat@gmail.com</a>으로 문의 바랍니다.</div></li>
                    </ul>
                </div>
                <Button style={{width:'100%'}} id="chaewon" variant="contained" onClick={e=>closeModal()}>시작하기</Button>
            </Modal>
        </div>
    );
}