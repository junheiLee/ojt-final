import axios from 'axios';

const errorCodeMessages = {
  "REQUIRED": "입력해야합니다.",
  "EMPTY": "공백은 입력할 수 없습니다.",
  "SIZE_MISS": "범위를 벗어난 입력입니다.",
  "SIZE_OVER_50": "50자를 넘을 수 없습니다.",
  "SIZE_OVER_300": "300자를 넘을 수 없습니다.",
  "RANGE_OVER": "공백, 음수, 범위를 초과한 숫자를 입력했는지 확인해주세요.",
  "NEGATIVE": "양수를 입력해주세요."
};

const params = {
  "partnerCode": "협력사코드",
  "categoryCode": "카테고리",
  "code": "협력사상품코드",
  "name": "협력사상품명",
  "pcPrice": "PC 가격",
  "mobilePrice": "모바일 가격",
  "url": "협력사상품 URL",
  "imageUrl": "상품이미지 URL"
}

export const createPartnerProduct = async (productData) => {

  try {

    const response = await axios.post(`/partner-products`, productData);
    return { data: response.data, error: null };

  } catch (error) {

    let message = "";

    if(error.response.data.code === 'INVALID'){
      const errors = error.response.data.errors;
      let list = "";

      for (const key in errors) {
        if (params[key] && errorCodeMessages[errors[key]]) {
          list += `${params[key]}: ${errorCodeMessages[errors[key]]}\n`;
        }
      }
      
      message = "유효하지 않은 입력입니다. 다음 항목을 확인해주세요.\n\n" + list;
    } else {

      message = error.response?.data?.message || 'Failed to create product';
    }

    const status = error.response?.status;
    return { data: null, error: message, status };
  }
};

export const getProductById = async (partnerCode, prodCode) => {
  try {
    const response = await axios.get(`/partner-products/${partnerCode}/${prodCode}`);
    return { data: response.data, error: null };
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to fetch product';
    return { data: null, error: message };
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

export const tryDelete = async (partnerCode, prodCode) => {
  try {
    const response = await axios.get(`/partner-products/check/link/${partnerCode}/${prodCode}`);
    return { data: response.data, error: null };
  } catch(error) {
    const message = error.response?.data?.message || '링크 여부 확인에 실패했습니다.';
    alert(message);
  }
}

export const deletePartnerProduct = async (partnerCode, prodCode) => {
  try {

    await axios.delete(`/partner-products/${partnerCode}/${prodCode}`);
    alert("삭제되었습니다.");
  } catch(error) {
    const message = error.response?.data?.message || '삭제 실패했습니다.';
    alert(message);
  }
};