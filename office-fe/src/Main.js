import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Nav from "./components/fix/Nav";
import Footer from "./components/fix/Footer";
import FileUpload from "./pages/FileUpload";

const Main = () => {
    return (
        <BrowserRouter>
            <Nav />
            
            <div style={{display: "flex"}}>
                <Routes>
                    <Route path="/upload/excel"  element={<FileUpload />} />
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
    )
}

export default Main;