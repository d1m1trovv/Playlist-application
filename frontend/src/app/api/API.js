import Constants from '../constants/Constants';
import axios from 'axios';

const API = {
    get(url, success, error) {
        return this.request({
            method: "GET",
            url,
            success,
            error
        })
    },
    post(url, data, success, error) {
        return this.request({
            method: "POST",
            url,
            data,
            success,
            error
        })
    },
    put(url, data, success, error) {
        return this.request({
            method: "PUT",
            url,
            data,
            success,
            error
        })
    },
    delete(url, success, error) {
        return this.request({
            method: "DELETE",
            url,
            success,
            error
        })
    },
    request: (options = {}) => {
        if (!options.url) {
            alert('URL is not set!');
            return;
        }

        //{name: 'Gosho'}, {age: 21}
        //Object.assign({name: 'Gosho'}, {age: 21}) -> {name: 'Gosho', age: 21}
        let data = Object.assign(
            {
                method: 'GET',
                baseURL: Constants.BASE_URL
            },
            options
        );

        if (data.params === undefined) {
            data.params = {};
        }
    }
}

//Cookies.get('api_token')
/* const api_token = 'hardcoded_token';
 if(api_token)
 {
     //data.params
     //{name: 'Gosho', age: 21} -> {name: 'Gosho', age: 21, api_token: 'hardcoded_token'}
     data.params = Object.assign(data.params, {api_token: api_token});
 }

 if(api_token)
 {
     data.headers = {
         Authorization: 'Bearer token'
     }
 }
*/