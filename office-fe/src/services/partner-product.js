import axios from 'axios';


export const createPartnerProd = async (productData) => {

  try {

    const response = await axios.post(`/partner-products`, productData);
    return { data: response.data, error: null };

  } catch (error) {

    const message = error.response.data.message;
    return { data: null, error: message};

  }
};
