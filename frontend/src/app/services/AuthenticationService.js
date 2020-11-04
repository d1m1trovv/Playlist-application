import axios from "axios";
import Constants from "../constants/Constants";

const register = (email, username, password, confirmPassword) => {
    return axios.post(Constants.BASE_URL + "register", {
        email,
        username,
        password,
        confirmPassword
    });
};

const login = (username, password) => {
    return axios
        .post(Constants.BASE_URL + "login", {
            username,
            password,
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