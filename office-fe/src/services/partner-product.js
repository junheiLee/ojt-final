import axios from 'axios';

// Create a new Partner Product
export const createPartnerProduct = async (productData) => {
  try {
    const response = await axios.post(`/partner-products`, productData);
    return { data: response.data, error: null };
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to create product';
    const status = error.response?.status;
    return { data: null, error: message, status };
  }
};

// Update an existing Partner Product
export const updatePartnerProduct = async (productData) => {
  try {
    const response = await axios.patch(`/partner-products`, productData);
    return { data: response.data, error: null };
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to update product';
    return { data: null, error: message };
  }
};


// import React, { useState } from 'react';
// import axios from 'axios';
// import ConfirmModal from '../components/ConfirmModal';


// export const createPartnerProd = async (productData) => {

//   try {

//     const response = await axios.post(`/partner-products`, productData);
//     return { data: response.data, error: null };

//   } catch (error) {

//     if(error.response.data.code == `DUPLICATE_IDENTIFIER`) {
//       updatePartnerProd(productData);
//     } else {

//       const message = error.response.data.message;
//       return { data: null, error: message};

//     }
//   }
// };

