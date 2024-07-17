import React from 'react';
import Modal from 'react-modal';

Modal.setAppElement('#root');

const customStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      backgroundColor: '#f0f0f0',
      width: '300px',
      padding: '20px',
      borderRadius: '10px',
      textAlign: 'center',
    },
    overlay: {
      backgroundColor: 'rgba(0, 0, 0, 0.5)',
    },
  };


  const ConfirmModal = ({ isOpen, onRequestClose, onConfirm, message }) => {
    return (
      <Modal
        isOpen={isOpen}
        onRequestClose={onRequestClose}
        contentLabel="Confirm Modal"
        style={customStyles}
      >
        <p style={{ whiteSpace: 'pre-line' }}>{message}</p>
        <div>
          <button onClick={onConfirm} style={{ marginRight: '10px' }}>Yes</button>
          <button onClick={onRequestClose}>No</button>
        </div>
      </Modal>
    );
  };
  
  export default ConfirmModal;