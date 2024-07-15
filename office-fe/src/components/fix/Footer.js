import './Footer.css'
import React from "react";

const Footer = () => {

    return (
        <>
            <div className="footer__wrap">
			<div className="footer__inner">
				<div className="footer__info">
					<div className="footer__about">
						<span className="address">주소 (우) 08510 서울특별시 금천구 벚꽃로 298, 17층(가산동, 대륭포스트타워6차)</span>
					s	<span className="txt">대표: 이건수</span><br /><span className="txt">사업자번호: 117-81-40065</span>
						<span className="txt">통신판매업: 제2004-서울양천-00918호</span>
						<span className="txt">부가통신사업: 제003081호<a href="https://www.ftc.go.kr/bizCommPop.do?wrkr_no=1178140065" className="btn_footer" target="_blank">사업자정보확인</a></span>
					</div>
					<div className="footer__law">
						<strong className="title">콘텐츠산업진흥법</strong><span className="summary">콘텐츠산업 진흥법에 의한 콘텐츠 보호</span>
						<a href="#" role="button" className="btn_footer" id="content_see_more">자세히보기</a>
						<div className="layer__basic" id="footer_law_layer" role="dialog" aria-label="콘텐츠산업진흥법에 의한 표시">
							<div className="layer__basic__wrap">
								<div className="layer__basic__header"><strong className="layer__basic__title">콘텐츠산업진흥법에 의한 표시</strong></div>
								<div className="layer__basic__cont">
									<div className="layer__footer-law">
										<div className="layer__footer-law__detail">
											<dl className="row">
												<dt className="title">콘텐츠의 명칭</dt>
												<dd className="cont">상품콘텐츠(정보) 및 기사, 이벤트 정보</dd>
											</dl>
											<dl className="row">
												<dt className="title">콘텐츠의 제작 및 표시 연월일</dt>
												<dd className="cont">개별 표기된 제작일 또는 갱신일</dd>
											</dl>
											<dl className="row">
												<dt className="title">콘텐츠의 제작자</dt>
												<dd className="cont">(주)커넥트웨이브<br />전화: 1688 - 2470 (유료) / 팩스: 1688-2451<br />서울특별시 양천구 목동동로 233-1 드림타워 501호</dd>
											</dl>
											<dl className="row">
												<dt className="title">콘텐츠의 이용조건</dt>
												<dd className="cont">이용약관 및 서비스 안내 참조<br />(동의 없이 무단복제 및 가공을 금함)<a href="https://www.danawa.com/info/agreement.html" target="_blank" className="btn_footer">이용약관 보기</a></dd>
											</dl>
										</div>
										<div className="layer__footer-law__noti">
											<p className="text">(주)커넥트웨이브 홈페이지 내의 모든 콘텐츠는 『콘텐츠산업 진흥법』에 따라
												<br /> 제작일 또는 그 갱신일로부터 5년간 보호 됩니다.
											</p>
										</div>
									</div>
								</div>
								<button type="button" className="layer__basic__close">
									<span className="blind">닫기</span>
								</button>
							</div>
						</div>
						<a href="https://help.danawa.com/bizCenter/index.php?depth1=11" className="btn_footer" target="_blank">콘텐츠이용안내</a>
					</div>
					<div className="footer__point">
						(주)커넥트웨이브는 상품판매와 직접적인 관련이 없으며, 모든 상거래의 책임은 구매자와 판매자에게 있습니다.<br />
						이에 대해 (주)커넥트웨이브는 일체의 책임을 지지 않습니다.<br />
						본사에 등록된 모든 광고와 저작권 및 법적책임은 자료제공사 (또는 글쓴이)에게 있으므로 본사는 광고에 대한 책임을 지지 않습니다.
					</div>
					<address className="footer__copy">Copyright © <strong>connectwave</strong> Co., Ltd. All Rights Reserved.</address>
					<div className="footer__kolsa">
						<a target="_blank" href="https://www.kolsa.or.kr">
							<span className="blind">KOLSA 한국온라인 쇼핑협회</span>
						</a>
					</div>
				</div>
				<div className="footer__cs">
					<strong className="title">다나와 고객센터</strong>
					<div className="service">
						<a href="https://help.danawa.com" target="_blank" className="link link--home">고객센터 홈</a>
						<a href="https://help.danawa.com/serviceHelpList.php" target="_blank" className="link link--help">서비스도움말</a>
					</div>
					<ul className="info">
						<li className="item">전화: 1688-2470 (유료)</li>
						<li className="item">팩스: 1688-2451</li>
						<li className="item">이메일: cs@cowave.kr</li>
					</ul>
					
				</div>
			</div>
		</div>
        </>
    )
}

export default Footer;