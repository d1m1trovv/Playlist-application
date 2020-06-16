import styled from "styled-components";
import React from "react";


const Constants = {

    BASE_URL: "http://localhost:8080/api/",

    BODY: styled.div`
    
    ul {
    position: fixed;
    background-color: #1C2C43;
    width: 100%;
    float: left;
    margin: 0 0 3em 0;
    padding: 0;
    list-style: none; }
    
    ul li {
    float: left; }
    
    ul li a:hover{
    background-color: #fff; }

    ul li a {
    display: block;
    padding: 13px 16px;
    text-decoration: none;
    font-weight: bold;
    color: #498CD7; }
    `
}
export default Constants;