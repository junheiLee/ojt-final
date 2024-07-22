import React, { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { getCategories } from "./services/category";
import { getPartners } from './services/partner';
import Nav from "./components/fix/Nav";
import Footer from "./components/fix/Footer";
import FileUpload from "./pages/FileUpload";
import PartnerProductCreate from "./pages/PartnerProdCreateForm";
import PartnerProductEdit from "./pages/PartnerProdEditForm";
import StandardProdCreate from "./pages/StandardProdCreateForm";
import StandardProdEdit from "./pages/StandardProdEditForm";

const Main = () => {

    const [categories, setCategories] = useState([]);
    const [partners, setPartners] = useState([]);

    useEffect(() => {
        const fetchCategories = async () => {
            let { data, error } = await getCategories();
            if(data == null || data.length == 0) {
                data = [{code:"", name: "카테고리 없음"}]
            }
            setCategories(data);
        };

        fetchCategories();
    }, []);

    useEffect(() => {
        const fetchPartners = async () => {
            let { data } = await getPartners();   
            if(data == null || data.length == 0) {
                data = [{code: "" , name: "협력사 없음"}]
            }
            setPartners(data);
        };
    
        fetchPartners();
    }, []);

    return (
        <BrowserRouter>
            <Nav />
            
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center',  flexDirection: 'column' }}>
                <Routes>
                    <Route path="/upload/excel"  element={<FileUpload />} />
                    <Route path="/standard-products/form" element={<StandardProdCreate categories={categories} partners={partners}/> } />
                    <Route path="/standard-products/form/:code" element={<StandardProdEdit categories={categories} partners={partners}/> } />
                    <Route path="/partner-products/form" element={<PartnerProductCreate  categories = {categories} partners={partners} />} />
                    <Route path="/partner-products/form/:partnerCode/:prodCode" element={<PartnerProductEdit  categories = {categories} partners={partners} />} />
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
    )
}

export default Main;