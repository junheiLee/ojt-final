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
INSERT INTO tStandardProduct (nProductCode, nCategoryCode, sProductName, dtManufactureDate)
VALUES ('55', '5', 'Standard5', NOW());


--협력사 상품
INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('1', '1', '1', 'PartnerProduct1', '1', '0', 'https://test', 'https://image.test', DATE ('2024-01-01'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('2', '2', '2', 'PartnerProduct2', '2', '0', 'https://test', 'https://image.test', DATE ('2024-02-02'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('3', '3', '3', 'PartnerProduct3', '3', '0', 'https://test', 'https://image.test', DATE ('2024-03-03'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('4', '4', '4', 'PartnerProduct4', '4', '0', 'https://test', 'https://image.test', DATE ('2024-04-04'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('5', '5', '5', 'PartnerProduct5', '5', '0', 'https://test', 'https://image.test', DATE ('2024-05-05'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('1', '3', '3', 'PartnerProduct1', '31', '0', 'https://test', 'https://image.test', DATE ('2023-01-03'));

INSERT INTO tPartnerProduct (sPartnerProductCode, sPartnerCode, nCategoryCode, sPartnerProductName, nPcprice,
                             nMobilePrice, sUrl, sImageUrl, dtCreatedAt)
VALUES ('1', '5', '5', 'PartnerProduct1', '61', '0', 'https://test', 'https://image.test', DATE ('2023-01-05'));