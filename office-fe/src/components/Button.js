import React from 'react';
import './Button.css';

const Button = ({ type, onClick, children }) => {
  return (
    <button className='btn' type={type} onClick={onClick}>
      {children}
    </button>
  );
};

export default Button;