import React from 'react';
import Button from '../components/Button';

const PartnerProductCreate = () => {

    const data = {
        code: "FE13DD123",
        partnerCode: "ED901",
        categoryCode: 1304,
        name: "test",
        pcPrice: 11000,
        mobilePrice: 12000,
        url: "naver.com",
        imageUrl: "github.com"
      };

  const partnerOptions = [
    { value: 'ED901', label: 'Partner 1' },
    { value: 'ED902', label: 'Partner 2' },
    { value: 'ED903', label: 'Partner 3' },
  ];

  const categoryOptions = Array.from({ length: 50 }, (_, i) => ({
    value: 1300 + i,
    label: `Category ${1300 + i}`,
  }));

  return (
    <div style={{height: "50%", margin: "1rem auto"}}>
        <table width="900px" border="1" cellPadding="10" cellSpacing="0">
        <tbody>
            <tr>
            <td width="30%">협력사상품코드</td>
            <td>
                <input
                type="text"
                name="code"
                style={{outline:"solid black thin"}}
                />
            </td>
            </tr>
            <tr>
            <td>협력사코드</td>
            <td>
                <select
                name="partnerCode"
                style={{width: '20%'}}
                >
                {partnerOptions.map(option => (
                    <option key={option.value} value={option.value}>
                    {option.label}
                    </option>
                ))}
                </select>
            </td>
            </tr>
            <tr>
            <td>협력사상품명</td>
            <td>
                <input
                type="text"
                name="name"
                style={{outline:"solid black thin", width: "90%"}}
                />
            </td>
            </tr>
            <tr>
            <td>PC 가격</td>
            <td>
                <input
                type="number"
                name="pcPrice"
                style={{outline:"solid black thin"}}
                />
            </td>
            </tr>
            <tr>
            <td>모바일 가격</td>
            <td>
                <input
                type="number"
                name="mobilePrice"
                style={{outline:"solid black thin"}}
                />
            </td>
            </tr>
            <tr>
            <td>카테고리</td>
            <td>
                <select
                size={5}
                name="categoryCode"
                defaultValue={data.categoryCode}
                style={{ width: '60%', overflowY: 'auto', maxHeight: '100px' }}
                >
                {categoryOptions.map(option => (
                    <option key={option.value} value={option.value}>
                    {option.label}
                    </option>
                ))}
                </select>
            </td>
            </tr>

            <tr>
            <td>협력사상품 URL</td>
            <td>
                <input
                type="text"
                name="url"
                style={{outline:"solid black thin", width: "90%"}}
                />
            </td>
            </tr>
            <tr>
            <td>상품이미지 URL</td>
            <td>
                <input
                type="text"
                name="imageUrl"
                style={{outline:"solid black thin", width: "90%"}}
                />
            </td>
            </tr>
        </tbody>
        </table>
        <div style={{textAlign:"center"}}>
            <Button type="submit" >제출</Button>
            <Button type="button" onClick={() => console.log('Cancel clicked')}>취소</Button>

        </div>
        
    </div>
  );
};

export default PartnerProductCreate;
