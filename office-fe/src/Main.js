import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Nav from "./components/fix/Nav";
import Footer from "./components/fix/Footer";

const Main = () => {
    return (
        <BrowserRouter>
            <Nav />
            
            <div style={{display: "flex"}}>
                <Routes>
                </Routes>
            </div>
            <Footer />
        </BrowserRouter>
    )
}

export default Main;