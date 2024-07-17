import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import he from 'he';
import ConfirmModal from '../components/ConfirmModal';
import Button from '../components/Button';
import { getPartners } from '../services/partner';
import { createPartnerProduct, updatePartnerProduct } from '../services/partner-product';

const PartnerProductCreate = ({ categories }) => {
  const [partners, setPartners] = useState([]);
  const [formData, setFormData] = useState({
    code: '',
    partnerCode: '',
    categoryCode: '',
    name: '',
    pcPrice: '',
    mobilePrice: '',
    url: '',
    imageUrl: ''
  });
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [productData, setProductData] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPartners = async () => {
      const { data, error } = await getPartners();
      if (data) {
        setPartners(data);
      } else {
      }
    };

    fetchPartners();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { data, error, status } = await createPartnerProduct(formData);

    if (data) {
      alert('성공');
      navigate('/');
    } else {
      if (status === 409) { // Conflict status
        setProductData(formData);
        setModalIsOpen(true);
      } else {
        alert('Failed to create product');
      }
    }
  };

  const handleConfirmUpdate = async () => {
    setModalIsOpen(false);

    const { data, error } = await updatePartnerProduct(productData);

    if (data) {
      alert('성공');
      navigate('/');
    } else {
      alert('Failed to update product');
    }
  };

  const handleCancelUpdate = () => {
    setModalIsOpen(false);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div style={{ height: '50%', margin: '1rem auto' }}>
          <table width="900px" border="1" cellPadding="10" cellSpacing="0">
            <tbody>
              <tr>
                <td width="30%">협력사상품코드</td>
                <td>
                  <input
                    type="text"
                    name="code"
                    value={formData.code}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin' }}
                  />
                </td>
              </tr>
              <tr>
                <td>협력사코드</td>
                <td>
                  <select
                    name="partnerCode"
                    value={formData.partnerCode}
                    onChange={handleChange}
                    style={{ width: '20%' }}
                  >
                    <option>협력사 선택</option>
                    {partners.map((partner) => (
                      <option key={partner.code} value={partner.code}>
                        {he.decode(partner.name)}
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
                    value={formData.name}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin', width: '90%' }}
                  />
                </td>
              </tr>
              <tr>
                <td>PC 가격</td>
                <td>
                  <input
                    type="number"
                    name="pcPrice"
                    value={formData.pcPrice}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin' }}
                  />
                </td>
              </tr>
              <tr>
                <td>모바일 가격</td>
                <td>
                  <input
                    type="number"
                    name="mobilePrice"
                    value={formData.mobilePrice}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin' }}
                  />
                </td>
              </tr>
              <tr>
                <td>카테고리</td>
                <td>
                  <select
                    size={5}
                    name="categoryCode"
                    value={formData.categoryCode}
                    onChange={handleChange}
                    style={{ width: '60%', overflowY: 'auto', maxHeight: '100px' }}
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
                <td>협력사상품 URL</td>
                <td>
                  <input
                    type="text"
                    name="url"
                    value={formData.url}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin', width: '90%' }}
                  />
                </td>
              </tr>
              <tr>
                <td>상품이미지 URL</td>
                <td>
                  <input
                    type="text"
                    name="imageUrl"
                    value={formData.imageUrl}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin', width: '90%' }}
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div style={{ textAlign: 'center' }}>
            <Button type="submit">등록</Button>
            <Button type="button" onClick={() => console.log('Cancel clicked')}>취소</Button>
          </div>
        </div>
      </form>
      <ConfirmModal
        isOpen={modalIsOpen}
        onRequestClose={handleCancelUpdate}
        onConfirm={handleConfirmUpdate}
        message={"다음의 상품이 존재합니다.\n 수정하시겠습니까?"}
      />
    </div>
  );
};

export default PartnerProductCreate;
