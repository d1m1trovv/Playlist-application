import axios from "axios";
import Constants from "../constants/Constants";

const register = (username, email, password, confirmPassword) => {
    return axios.post(Constants.BASE_URL + "register", {
        username,
        email,
        password,
        confirmPassword
    });
};

const login = (email, pass) => {
    return axios
        .post(Constants.BASE_URL + "login", {
            email,
            pass,
        })
        .then((response) => {

            let token = response.headers['authorization']
            localStorage.setItem("token", token);
            console.log(token);

        });
};

const getCurrentToken = () => {
    return localStorage.getItem('token');
}

const logout = () => {
    localStorage.removeItem("token");
};


export default {
    register,
    login,
    logout,
    getCurrentToken,
};