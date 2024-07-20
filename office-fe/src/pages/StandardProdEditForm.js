import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import he from 'he';
import Button from '../components/Button';

const StandardProdCreate = ({ categories, partners}) => {
  const [formData, setFormData] = useState({
    productCode: '',
    productName: '',
    category: '',
    imageOption: '',
    imageFile: null,
    imageUrl: '',
    manufactureDate: '',
    description: '',
    partnerSelection: ''
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleFileChange = (e) => {
    setFormData({
      ...formData,
      imageFile: e.target.files[0]
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 제출 로직 작성
    console.log(formData);
  };

  return (
    <form onSubmit={handleSubmit}>
        <div style={{ height: '50%', margin: '1rem auto' }}>

            <table width="900px" border="1" cellPadding="10" cellSpacing="0">
                <tbody>
                <tr>
                    <td>상품코드</td>
                    <td>
                    <input
                        type="text"
                        name="productCode"
                        value={formData.productCode}
                        onChange={handleChange}
                        style={{ width: '100%' }}
                    />
                    </td>
                </tr>
                <tr>
                    <td>상품명</td>
                    <td>
                    <input
                        type="text"
                        name="productName"
                        value={formData.productName}
                        onChange={handleChange}
                        style={{ outline: 'solid black thin', width: '100%' }}
                    />
                    </td>
                </tr>
                <tr>
                    <td>카테고리</td>
                    <td>
                    <select
                        name="category"
                        value={formData.category}
                        onChange={handleChange}
                        style={{ width: '100%' }}
                        size="5"
                    >
                    {
                      categories.map((category) => (
                      <option key={category.code} value={category.code}>
                        {category.name}
                      </option>
                      ))
                    }
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
                            name="imageOption"
                            value="none"
                            checked={formData.imageOption === 'none'}
                            onChange={handleChange}
                        /> 없음
                        </label>
                        <label style={{ marginLeft: '10px' }}>
                        <input
                            type="radio"
                            name="imageOption"
                            value="manufacturer"
                            checked={formData.imageOption === 'manufacturer'}
                            onChange={handleChange}
                        /> 제조사 제공
                        </label>
                        <label style={{ marginLeft: '10px' }}>
                        <input
                            type="radio"
                            name="imageOption"
                            value="custom"
                            checked={formData.imageOption === 'custom'}
                            onChange={handleChange}
                        /> 다나와 제작
                        </label>
                        <label style={{ marginLeft: '10px', display: 'flex', alignItems: 'center' }}>
                        <input
                            type="radio"
                            name="imageOption"
                            value="partner"
                            checked={formData.imageOption === 'partner'}
                            onChange={handleChange}
                        /> 협력사 선택
                        <select
                            name="partnerSelection"
                            value={formData.partnerSelection}
                            onChange={handleChange}
                            style={{ marginLeft: '10px' }}
                        >
                        {
                            partners.map((partner) => (
                            <option key={partner.code} value={partner.code}>
                                {he.decode(partner.name)}
                            </option>))
                        }
                        </select>
                        </label>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center', marginTop: '10px' }}>
                        <input
                        type="file"
                        onChange={handleFileChange}
                        style={{ marginRight: '10px' }}
                        />
                        <input
                        type="text"
                        name="imageUrl"
                        value={formData.imageUrl}
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
                        <span> 예&#41; 2019년 11월 -&gt; 201911</span>
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
            <Button type="submit">추가</Button>
            <Button type="button" onClick={() => navigate('/link')}>취소</Button>
      </div>
    </form>
  );
};

export default StandardProdCreate;
