import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import he from 'he';
import Button from '../components/Button';
import { editStandardProduct, getProductByCode } from '../services/standard-product';

const StandardProdEdit = ({ categories, partners }) => {

  const [formData, setFormData] = useState({
    code: '',
    name: '',
    categoryCode: '',
    imageOrigin: '',
    imageOriginUrl: '',
    imageFile: null,
    imageUrl: '',
    manufactureDate: '',
    description: ''
  });

  const navigate = useNavigate();
  const { code } = useParams();
  const [selectedPartner, setSelectedPartner] = useState('');

  useEffect(() => {
    const fetchProduct = async () => {
      const {data: productData} = await getProductByCode(code);
      if(productData) {
        console.log(productData);
        setFormData({
            code: code || '',
            name: productData.name || '',
            categoryCode: productData.categoryCode || '',
            imageOrigin: productData.imageOrigin || '',
            imageOriginUrl: productData.imageOriginUrl || '',
            imageUrl: parseImagePath(productData.imageUrl) || '',
            manufactureDate: productData.manufactureDate || '',
            description: productData.description || ''
        });
        if (productData.imageOrigin && partners.some(partner => he.decode(partner.name) === productData.imageOrigin)) {
            setSelectedPartner(productData.imageOrigin);
        }
      } else {
        alert(`상품 코드 ${code}에 해당하는 상품이 없습니다.`)
      }

    };
    fetchProduct();
  }, [code, partners]);

  const parseImagePath = (fullPath) => {
    const relativePath = fullPath.split('public')[1];
    return relativePath.replace(/\\/g, '/');
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    setFormData({
      ...formData,
      imageFile: e.target.files[0],
    });
  };

  const handlePartnerChange = (e) => {
    const { value } = e.target;
    setSelectedPartner(value);
    setFormData({
      ...formData,
      imageOrigin: value,
    });
  };

  const handleRadioChange = (e) => {
    const { value } = e.target;
    setFormData({
      ...formData,
      imageOrigin: value === 'partner' ? selectedPartner : value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    //const response = await createStandardProduct(formData);
    //console.log(response);
  };


  const handleDelete = async () => {
    
  };

  return (
    <form onSubmit={handleSubmit} encType='multipart/form-data'>
      <div style={{ height: '50%', margin: '1rem auto' }}>
        <table width="900px" border="1" cellPadding="10" cellSpacing="0">
          <tbody>
            <tr>
              <td>상품코드</td>
              <td>
                <input
                  type="text"
                  style={{ width: '100%', color: 'red', fontWeight: 'bolder' }}
                  value={formData.code}
                  readOnly
                />
              </td>
            </tr>
            <tr>
              <td>상품명</td>
              <td>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  style={{ outline: 'solid black thin', width: '100%' }}
                />
              </td>
            </tr>
            <tr>
              <td>카테고리</td>
              <td>
                <select
                  name="categoryCode"
                  value={formData.categoryCode}
                  onChange={handleChange}
                  style={{ width: '100%' }}
                  size="5"
                >
                  {categories.map((category) => (
                    <option key={category.code} value={category.code}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </td>
            </tr>
            <tr>
              <td>상품 이미지</td>
              <td>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  <label>
                    <input
                      type="radio"
                      name="imageOrigin"
                      value="없음"
                      checked={formData.imageOrigin === '없음'}
                      onChange={handleRadioChange}
                    />{' '}
                    없음
                  </label>
                  <label style={{ marginLeft: '10px' }}>
                    <input
                      type="radio"
                      name="imageOrigin"
                      value="제조사 제공"
                      checked={formData.imageOrigin === '제조사 제공'}
                      onChange={handleRadioChange}
                    />{' '}
                    제조사 제공
                  </label>
                  <label style={{ marginLeft: '10px' }}>
                    <input
                      type="radio"
                      name="imageOrigin"
                      value="다나와 제작"
                      checked={formData.imageOrigin === '다나와 제작'}
                      onChange={handleRadioChange}
                    />{' '}
                    다나와 제작
                  </label>
                  <label
                    style={{
                      marginLeft: '10px',
                      display: 'flex',
                      alignItems: 'center',
                    }}
                  >
                    <input
                      type="radio"
                      name="imageOrigin"
                      value="partner"
                      checked={formData.imageOrigin === selectedPartner}
                      onChange={handleRadioChange}
                    />{' '}
                    협력사 선택
                    <select
                      name="partnerSelection"
                      value={selectedPartner}
                      onChange={handlePartnerChange}
                      style={{ marginLeft: '10px' }}
                    >
                      <option value="">협력사를 선택하세요</option>
                      {partners.map((partner) => (
                        <option
                          key={partner.code}
                          value={he.decode(partner.name)}
                        >
                          {he.decode(partner.name)}
                        </option>
                      ))}
                    </select>
                  </label>
                </div>
                {formData.imageUrl && (
                  <div style={{ marginTop: '10px' }}>
                    <img
                      src={formData.imageUrl}
                      alt="Product"
                      style={{ width: '80px', height: '80px' }}
                    />
                  </div>
                )}
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '10px',
                  }}
                >
                  <input
                    type="file"
                    onChange={handleFileChange}
                    style={{ marginRight: '10px' }}
                  />
                  <input
                    type="text"
                    name="imageOriginUrl"
                    value={formData.imageOriginUrl}
                    onChange={handleChange}
                    placeholder="URL"
                    style={{ flex: 1 }}
                  />
                </div>
              </td>
            </tr>
            <tr>
              <td>제조일자</td>
              <td>
                <input
                  type="text"
                  name="manufactureDate"
                  value={formData.manufactureDate}
                  onChange={handleChange}
                  style={{ outline: 'solid black thin', width: '10%' }}
                />
                <span style={{color: 'gray', fontSize: '0.8rem'}}>
                     &nbsp; 예&#41; 2019년 11월 -&gt; 201911
                </span>
              </td>
            </tr>
            <tr>
              <td>설명 추가</td>
              <td>
                <input
                  type="text"
                  name="description"
                  value={formData.description}
                  onChange={handleChange}
                  style={{ outline: 'solid black thin', width: '100%' }}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
            <Button type="submit">수정</Button>
            <Button type="button" onClick={handleDelete}>삭제</Button>
            <Button type="button" onClick={() => navigate('/link')}>취소</Button>
      </div>
    </form>
  );
};

export default StandardProdEdit;
