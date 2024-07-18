import React, { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Nav from "./components/fix/Nav";
import Footer from "./components/fix/Footer";
import FileUpload from "./pages/FileUpload";
import PartnerProductCreate from "./pages/PartnerProdCreateForm";
import PartnerProductEdit from "./pages/PartnerProdEditForm";
import { getCategories } from "./services/category";

const Main = () => {

    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const { data, error } = await getCategories();
            setCategories(data);
        };

        fetchData();
    }, []);


    return (
        <BrowserRouter>
            <Nav />
            
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center',  flexDirection: 'column' }}>
                <Routes>
                    <Route path="/upload/excel"  element={<FileUpload />} />
                    <Route path="/partner-products/form" element={<PartnerProductCreate  categories = {categories} />} />
                    <Route path="/partner-products/form/:partnerCode/:prodCode" element={<PartnerProductEdit  categories = {categories} />} />
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
    )
}

export default Main;