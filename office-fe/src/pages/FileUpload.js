import React from "react";
import { uploadFile } from "../services/upload";

const FileUpload = () => {

    const handleSubmit = async(e) => {

        e.preventDefault();
        const excelFile = document.getElementById("excelFile");
        const option = document.getElementById("option").value;

        const formData = new FormData();
        formData.append("excelFile", excelFile.files[0]);
        
        uploadFile(option, formData);
    }

    return (
      <div style={{width: "50%", height: "15rem", margin: "10rem auto"}}>

        <h4 style={{ textAlign: "left"}}># 엑셀 파일 등록 </h4>
        <form style={{ textAlign: "left", marginTop: "1rem", fontSize: "14px"}} onSubmit={ e => handleSubmit(e) }>
          
          <input type="file" 
                 required accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" 
                 name="excelFile" id="excelFile"></input> 
          <select id="option">
            <option value=""> 선택 </option>
            <option value="category"> 카테고리 </option>
            <option value="standard-products"> 기준상품 </option>
            <option value="partner-products"> 협력사상품 </option>
          </select>
          <input style={{marginLeft: "1rem"}} type="submit" value="등록" />
        </form> 
      </div>
    )
}

export default FileUpload;
  