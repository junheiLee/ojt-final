import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import he from 'he';
import ConfirmModal from '../components/ConfirmModal';
import Button from '../components/Button';
import { updatePartnerProduct, getProductById, deletePartnerProduct, tryDelete } from '../services/partner-product';

const PartnerProductCreate = ({ categories, partners }) => {

  const [formData, setFormData] = useState({
    code: '',
    partnerCode: '',
    categoryCode: '',
    name: '',
    pcPrice: '0',
    mobilePrice: '0',
    url: '',
    imageUrl: ''
  });
  const [showConfirm, setShowConfirm] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);
  const navigate = useNavigate();
  const { partnerCode, prodCode } = useParams();

  useEffect(() => {
    const fetchInitalData = async () => {

        const { data: productData } = await getProductById(partnerCode, prodCode);
        if(productData) {
            console.log(productData);
            setFormData({
                code: prodCode || '',
                partnerCode: partnerCode || '',
                categoryCode: productData.categoryCode || '',
                name: productData.name || '',
                pcPrice: productData.pcPrice || '0',
                mobilePrice: productData.mobilePrice || '0',
                url: productData.url || '',
                imageUrl: productData.imageUrl || ''
              });
        } else {
            alert(`상품 코드: ${prodCode}, 파트너 코드: ${partnerCode}에 해당하는 상품이 없습니다.`);
        }

    };

    fetchInitalData();
  }, [partnerCode, prodCode]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { data, error } = await updatePartnerProduct(formData);

    if (data) {
        window.location.reload();
    } else {
        if(error) {
            alert(error);
        }
    }
  };


  const handleDelete = async () => {
    const { data, error } = await tryDelete(partnerCode, prodCode);

    if (error) {
      alert(error);
    } else if (data.code === 'UNLINKED') {
      await deletePartnerProduct(partnerCode, prodCode);
      navigate('/link');
    } else {
      setProductToDelete({ partnerCode, prodCode });
      setShowConfirm(true);
    }
  };

  const confirmDelete = async () => {
    if (productToDelete) {
      await deletePartnerProduct(productToDelete.partnerCode, productToDelete.prodCode);
      setProductToDelete(null);
      setShowConfirm(false);
      navigate('/link');
    }
  };


  const cancelDelete = () => {
    setProductToDelete(null);
    setShowConfirm(false);
  };


  const getPartnerNameByCode = () => {
    const partner = partners.find(p => p.code === partnerCode);
    return partner ? he.decode(partner.name) : '협력사 선택';
  };

  const getCategoryNameByCode = (code) => {
    const category = categories.find(c => c.code === code);
    return category ? category.name : '카테고리 선택';
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
                    value={prodCode}
                    onChange={handleChange}
                    style={{ outline: 'solid black thin' }}
                    disabled
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
                    disabled
                  >
                    <option value={partnerCode}>{getPartnerNameByCode()}</option>
                    {
                      partners && partners.length > 0 ? (
                      partners.map((partner) => (
                        <option key={partner.code} value={partner.code}>
                            {he.decode(partner.name)}
                        </option>
                      )))
                       : (<option>협력사 없음</option>)
                    }
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
                    <option value={formData.categoryCode}>{getCategoryNameByCode(formData.categoryCode)}</option>
                    {
                      categories && categories.length > 0 ? (
                      categories.map((category) => (
                      <option key={category.code} value={category.code}>
                        {category.name}
                      </option>
                      )))
                        : (<option>카테고리 없음</option>)
                    }
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
                    disabled
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
            <Button type="submit">수정</Button>
            <Button type="button" onClick={handleDelete}>삭제</Button>
            <Button type="button" onClick={() => navigate('/link')}>취소</Button>
          </div>
        </div>
      </form>

      <ConfirmModal
        show={showConfirm}
        onConfirm={confirmDelete}
        onCancel={cancelDelete}
      />
    </div>
  );
};

export default PartnerProductCreate;
