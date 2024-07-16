import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Nav from "./components/fix/Nav";
import Footer from "./components/fix/Footer";
import FileUpload from "./pages/FileUpload";
import PartnerProductCreate from "./pages/PartnerProdCreateForm";

const Main = () => {
    return (
        <BrowserRouter>
            <Nav />
            
            <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center',  flexDirection: 'column' }}>
                <Routes>
                    <Route path="/upload/excel"  element={<FileUpload />} />
                    <Route path="/partner-products/create" element={<PartnerProductCreate />} />
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
    )
}

export default Main;