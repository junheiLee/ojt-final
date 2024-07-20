import axios from "axios";

export const createStandardProduct = async (formData) => {

    const formDataToSend = new FormData();
    formDataToSend.append('image', formData.imageFile);
    formDataToSend.append('createDto', JSON.stringify({
      name: formData.name,
      categoryCode: formData.categoryCode,
      manufactureDate: formData.manufactureDate,
      description: formData.description,
      imageOrigin: formData.imageOrigin,
      imageOriginUrl: formData.imageOriginUrl
    }));

    try {
        const response = await fetch('/standard-products', {
          method: 'POST',
          body: formDataToSend
          // 필요시 헤더 추가
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