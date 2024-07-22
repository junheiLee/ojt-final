import axios from "axios";

const errorCodeMessages = {
  "REQUIRED": "입력해야합니다.",
  "EMPTY": "공백은 입력할 수 없습니다.",
  "SIZE_MISS": "범위를 벗어난 입력입니다.",
  "SIZE_OVER_50": "50자를 넘을 수 없습니다.",
  "SIZE_OVER_300": "300자를 넘을 수 없습니다.",
  "RANGE_OVER": "공백, 음수, 범위를 초과한 숫자를 입력했는지 확인해주세요.",
  "NEGATIVE": "양수를 입력해주세요.",
  "FUTURE": "오늘 날짜보다 과거로 입력해주세요."
};

const params = {
  "code": "상품 코드",
  "name": "상품명",
  "categoryCode": "카테고리",
  "imageOrigin": "상품 이미지 제공",
  "imageOriginUrl": "상품 이미지 URL",
  "manufactureDate": "제조일자",
  "description": "설명"
}

export const createStandardProduct = async (formData) => {

    const formDataToSend = new FormData();
    formDataToSend.append('image', formData.imageFile);
    const createDto = JSON.stringify({
      name: formData.name,
      categoryCode: formData.categoryCode,
      manufactureDate: formData.manufactureDate,
      description: formData.description,
      imageOrigin: formData.imageOrigin,
      imageOriginUrl: formData.imageOriginUrl
    });
    const blob = new Blob([createDto], {type: "application/json"});

    formDataToSend.append('createDto', blob);


    try {
        const response = await fetch('/standard-products', {
          method: 'POST',
          body: formDataToSend
        });
    
        if (!response.ok) {
          throw new Error('서버 오류');
        }
    
        // 성공적으로 저장되었을 때의 처리 (예: 경로 이동 등)
      } catch (error) {
        console.error('Error:', error);
        // 에러 처리 로직 추가
      }
};

export const getProductByCode = async (code) => {
  try {
    const response = await axios.get(`/standard-products/${code}`);
    return { data: response.data, error: null };
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to fetch product';
    return { data: null, error: message };
  }
};

export const editStandardProduct = async (productData) => {
  try {
    const response = await axios.patch(`/standard-products`, productData);
    return { data: response.data, error: null };
  } catch (error) {
    const message = error.response?.data?.message || 'Failed to update product';
    return { data: null, error: message };
  }
};