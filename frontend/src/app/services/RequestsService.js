import authHeader from "./AuthenticationHeader";
import axios from 'axios';
import Constants from "../constants/Constants";

const getUsers = () => {
    return axios.get(Constants.BASE_URL + "admin/users", { headers: authHeader() });
};

const addUser = data => {
    return axios.post(Constants.BASE_URL + "admin/addPlaylist", data, { headers: authHeader() });
}

const findByEmail = email => {
    return axios.get(Constants.BASE_URL + `admin/users?email=${email}`, {headers: authHeader()});
}

const findUserById = id => {
    return axios.get(Constants.BASE_URL + `admin/users/${id}`, {headers: authHeader()});
}

const updateUser = (id, data) => {
    return axios.put(Constants.BASE_URL + `admin/users/${id}`, data, {headers: authHeader()});
}

const deleteUser = (id) => {
    return axios.delete(Constants.BASE_URL + `admin/users/${id}`, {headers: authHeader()});
}

const getPlaylists = () => {
    return axios.get(Constants.BASE_URL + `admin/playlists`, {headers: authHeader()});
}
const getPlaylistByTitle = (title) => {
    return axios.get(Constants.BASE_URL + `admin/playlists/${title}`, {headers: authHeader()});
}

const findPlaylistById = id => {
    return axios.get(Constants.BASE_URL + `admin/playlists/${id}`, {headers: authHeader()});
}

const updatePlaylist = (id, data) => {
    return axios.put(Constants.BASE_URL + `admin/playlists/${id}`, data, {headers: authHeader()});
}

const deletePlaylist = (id) => {
    return axios.delete(Constants.BASE_URL + `admin/playlists/${id}`, {headers: authHeader()});
}

const getSongById = (id, songId) => {
    return axios.get(Constants.BASE_URL + `admin/addSongsToPlaylist/${id}?songId=${songId}`,
        {headers: authHeader()})
}

const addPlaylist = (title, author, genre, subFee) => {
    return axios.post(Constants.BASE_URL + `admin/addPlaylist`,
        {title, author, genre, subFee}, {headers: authHeader()})
}

const addSongToPlaylist = (name, author, duration) => {
    return axios.post(Constants.BASE_URL + `admin/addSong`,
        {name, author, duration}, {headers: authHeader()})
}

const getPlaylistSongs = id => {
    return axios.get(Constants.BASE_URL + `admin/playlists/${id}`, {headers: authHeader()})
}

const getSongsToAdd = id => {
    return axios.get(Constants.BASE_URL + `admin/addSongsToPlaylist/${id}`, {headers: authHeader()})
}

const addSongToPlaylistBySongId = (id, song) => {
    return axios.put(Constants.BASE_URL + `admin/addSongsToPlaylist/${id}`, song, {headers: authHeader()})
}
export default {
    addSongToPlaylistBySongId,
    getSongsToAdd,
    getPlaylistSongs,
    addSongToPlaylist,
    addPlaylist,
    getUsers,
    findByEmail,
    findUserById,
    updateUser,
    deleteUser,
    getPlaylists,
    findPlaylistById,
    updatePlaylist,
    deletePlaylist
};