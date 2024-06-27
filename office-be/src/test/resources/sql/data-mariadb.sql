-- 카테고리
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('1', 'Category1');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('2', 'Category2');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('3', 'Category3');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('4', 'Category4');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('5', 'Category5');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('6', 'Category6');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('7', 'Category7');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('8', 'Category8');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('9', 'Category9');
INSERT INTO tCategory (nCategoryCode, sCategoryName)
VALUES ('10', 'Category10');

-- 협력사
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('1', 'Partner1');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('2', 'Partner2');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('3', 'Partner3');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('4', 'Partner4');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('5', 'Partner5');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('6', 'Partner6');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('7', 'Partner7');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('8', 'Partner8');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('9', 'Partner9');
INSERT INTO tPartner (sPartnerCode, sPartnerName)
VALUES ('10', 'Partner10');

-- 기준상품
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('1', '1', 'Standard1', NOW());
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('2', '2', 'Standard2', NOW());
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('3', '3', 'Standard3', NOW());
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('4', '4', 'Standard4', NOW());
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('5', '5', 'Standard5', NOW());


--협력사 상품
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('1', '1', '1', 'PartnerProduct1', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('2', '2', '2', 'PartnerProduct2', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('3', '3', '3', 'PartnerProduct3', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('4', '4', '4', 'PartnerProduct4', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('5', '5', '5', 'PartnerProduct5', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('1', '3', '3', 'PartnerProduct1-p3', '0', '0', 'https://test', 'https://image.test');
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl)
VALUES ('1', '5', '5', 'PartnerProduct1-p5', '0', '0', 'https://test', 'https://image.test');