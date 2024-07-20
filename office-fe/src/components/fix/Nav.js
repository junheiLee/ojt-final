import './Nav.css';
import React from "react";
import { NavLink } from 'react-router-dom';

const Nav = () => {

    return (
        <>
        <div className='nav'>
            <div className='logo' >
                <img src={`${process.env.PUBLIC_URL}/assets/danawa_logo_main_brand.png`} alt="Danawa" />
            </div>
            <div className='menu'>
                <NavLink to="/standard-products/form"><span> 기준상품등록</span></NavLink>&nbsp;| &nbsp;
                <NavLink to="/partner-products/form"><span> 협력사상품등록</span></NavLink> &nbsp;|&nbsp;
                <NavLink to="/link"><span> 상품링크</span></NavLink> &nbsp;|&nbsp;
                <NavLink to="/upload/excel"><span> 엑셀파일등록</span></NavLink> &nbsp;|&nbsp;
                <NavLink to="/9"><span> 통계</span></NavLink>
            </div>
        </div>
        </>
    )
}

export default Nav;