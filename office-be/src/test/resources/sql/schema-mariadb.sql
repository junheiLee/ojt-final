SET foreign_key_checks=0;

drop table tcategory;
drop table tLink;
drop table tPartner;
drop table tPartnerProduct;
drop table tStandardProduct;
drop table tStatistic;

SET foreign_key_checks = 1;

-- 협력사
CREATE TABLE tPartner (
                          sPartnerCode VARCHAR(50) NOT NULL,
                          sPartnerName VARCHAR(300) NOT NULL
) DEFAULT CHARSET=utf8;

-- 협력사 PK
ALTER TABLE tPartner
    ADD CONSTRAINT PK_tPartner -- 협력사 기본키
        PRIMARY KEY (
                     sPartnerCode -- 협력사코드
            );


-- 카테고리
CREATE TABLE tCategory (
                           nCategoryCode INTEGER NOT NULL,
                           sCategoryName VARCHAR(300) NOT NULL
) DEFAULT CHARSET=utf8;

-- 카테고리 PK
ALTER TABLE tCategory
    ADD CONSTRAINT PK_tCategory -- 카테고리 기본키
        PRIMARY KEY (
                     nCategoryCode -- 대분류코드
            );


-- 기준상품
CREATE TABLE tStandardProduct (
                                  nProductCode INTEGER NOT NULL,
                                  nCategoryCode INTEGER NOT NULL,
                                  sProductName VARCHAR(300) NOT NULL,
                                  sBundleCondition VARCHAR(300),
                                  sDescription VARCHAR(5000),
                                  nPcLowestPrice INTEGER NULL DEFAULT 0,
                                  nMobileLowestPrice INTEGER NULL DEFAULT 0,
                                  nLowestPrice INTEGER NULL DEFAULT 0,
                                  nAveragePrice INTEGER NULL DEFAULT 0,
                                  nPartnerCount INTEGER NULL DEFAULT 0,
                                  dtManufactureDate DATE NOT NULL,
                                  sImageUrl VARCHAR(300),
                                  sOrigin VARCHAR(300),
                                  sOriginUrl VARCHAR(300)
) DEFAULT CHARSET=utf8;

-- 기준상품 PK
ALTER TABLE tStandardProduct
    ADD CONSTRAINT PK_tStandardProduct -- 기준상품 기본키
        PRIMARY KEY (
                     nProductCode  -- 상품코드
            );

-- 기준상품 FK
ALTER TABLE tStandardProduct
    ADD CONSTRAINT FK_tCategory_TO_tStandardProduct -- 카테고리 -> 기준상품
        FOREIGN KEY (
                     nCategoryCode -- 대분류코드
            )
            REFERENCES tCategory ( -- 카테고리
                                  nCategoryCode -- 대분류코드
                );

-- 협력사상품
CREATE TABLE tPartnerProduct (
                                 sPartnerProductCode VARCHAR(50) NOT NULL,
                                 sPartnerCode VARCHAR(50) NOT NULL,
                                 nCategoryCode INTEGER NOT NULL,
                                 sPartnerProductName VARCHAR(300) NOT NULL,
                                 nPcPrice INTEGER NOT NULL,
                                 nMobilePrice INTEGER NOT NULL,
                                 dtCreatedAt DATE DEFAULT NOW(),
                                 sUrl VARCHAR(300) NOT NULL,
                                 sImageUrl VARCHAR(300) NOT NULL,
                                 bIsLinked TINYINT(1) NULL DEFAULT 0
) DEFAULT CHARSET=utf8;

-- 협력사상품 PK
ALTER TABLE tPartnerProduct
    ADD CONSTRAINT PK_tPartnerProduct -- 협력사상품 기본키
        PRIMARY KEY (
                     sPartnerProductCode, -- 협력사상품코드
                     sPartnerCode        -- 협력사코드
            );

-- 협력사상품 FK
ALTER TABLE tPartnerProduct
    ADD CONSTRAINT FK_tPartner_TO_tPartnerProduct -- 협력사 -> 협력사상품
        FOREIGN KEY (
                     sPartnerCode -- 협력사코드
            )
            REFERENCES tPartner ( -- 협력사
                                 sPartnerCode -- 협력사코드
                );

-- 협력사상품 FK
ALTER TABLE tPartnerProduct
    ADD CONSTRAINT FK_tCategory_TO_tPartnerProduct -- 카테고리 -> 협력사상품
        FOREIGN KEY (
                     nCategoryCode -- 대분류코드
            )
            REFERENCES tCategory ( -- 카테고리
                                  nCategoryCode -- 대분류코드
                );


-- 링크
CREATE TABLE tLink (
                       nLinkSeq INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       nProductCode INTEGER NOT NULL,
                       sPartnerProductCode VARCHAR(50) NOT NULL,
                       sPartnerCode VARCHAR(50) NOT NULL
) DEFAULT CHARSET=utf8;

-- 링크 FK
ALTER TABLE tLink
    ADD CONSTRAINT FK_tStandardProduct_TO_tLink -- 기준상품 -> 링크
        FOREIGN KEY (
                     nProductCode  -- 상품코드
            )
            REFERENCES tStandardProduct ( -- 기준상품
                                         nProductCode  -- 상품코드
                );

-- 링크 FK
ALTER TABLE tLink
    ADD CONSTRAINT FK_tPartnerProduct_TO_tLink -- 협력사상품 -> 링크
        FOREIGN KEY (
                     sPartnerProductCode, -- 협력사상품코드
                     sPartnerCode        -- 협력사코드
            )
            REFERENCES tPartnerProduct ( -- 협력사상품
                                        sPartnerProductCode, -- 협력사상품코드
                                        sPartnerCode        -- 협력사코드
                );


-- 통계
CREATE TABLE tStatistic (
                            nStatisticSeq INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            nCategoryCode INTEGER NOT NULL,
                            nStandardProductCount INTEGER NOT NULL,
                            nLinkedProductCount INTEGER NOT NULL,
                            nUnlinkedProductCount INTEGER NOT NULL,
                            dtCreatedBy DATE NOT NULL
) DEFAULT CHARSET=utf8;

-- 통계 FK
ALTER TABLE tStatistic
    ADD CONSTRAINT FK_tCategory_TO_tStatistic -- 카테고리 -> 통계
        FOREIGN KEY (
                     nCategoryCode -- 대분류코드
            )
            REFERENCES tCategory ( -- 카테고리
                                  nCategoryCode -- 대분류코드
                );