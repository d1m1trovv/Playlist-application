import React, { useState, useEffect } from "react";
import RequestsService from "../services/RequestsService";
import {Link} from "react-router-dom";
import EditIcon from "@material-ui/icons/PlayCircleFilled";

const Playlist = props => {
    const initialPlaylistState = {
        id: null,
        title: "",
        author: "",
        genre: "",
        subFee: "",
        songs: []
    };
    const [currentPlaylist, setCurrentPlaylist] = useState(initialPlaylistState);
    const [currentPlaylistSongs, setCurrentPlaylistSongs] = useState([]);
    const [isEditable, setIsEditable] = useState(false);
    const [message, setMessage] = useState("");

    const getPlaylistById = id => {
        RequestsService.findPlaylistById(id)
            .then(response => {
                setCurrentPlaylist(response.data);
                setCurrentPlaylistSongs(response.data.songs)
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleAddButtonClick = () => {
        props.history.push("/api/admin/addSongsToPlaylist/" + currentPlaylist.id)
    }

    const getPlaylistSongs = id => {
        RequestsService.getPlaylistSongs(id).then(
            (response) => {
                console.log(props.match.params.id)
                setCurrentPlaylistSongs(response.data);
                console.log(response.data);
            },
            (error) => {
                const _playlistSongs =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                console.log(props.match.params.id)

                setCurrentPlaylistSongs(_playlistSongs);
            }
        );
    }

    useEffect(() => {
        getPlaylistById(props.match.params.id);
        console.log(props.match.params.id);
    }, [props.match.params.id]);


    const updatePlaylist = () => {
        RequestsService.updatePlaylist(currentPlaylist.id, currentPlaylist)
            .then(response => {
                console.log(response.data);
                setMessage("The Playlist was updated successfully!");
                setIsEditable(false);
                props.history.push("/api/admin/playlists");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const deletePlaylist = () => {
        RequestsService.deletePlaylist(currentPlaylist.id)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/playlists");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentPlaylist({ ...currentPlaylist, [name]: value });
    };

    const onEditButtonClickTrue = () => {
        setIsEditable(true);
    }

    const onEditButtonClickFalse = () => {
        setIsEditable(false);
    }

    return (
        <div className="playlistSec">
            {currentPlaylist ? (
                <div className="container-fluid">
                    {!isEditable ? (
                    <div className="playlistHeader">
                        <header>
                            <div className="overlay">
                                <h1>{currentPlaylist.title}</h1>
                                <h3>{currentPlaylist.author}</h3>
                                <p>{currentPlaylist.genre}</p>
                                <p>{currentPlaylist.subFee}</p>
                                <br/>
                                    <button onClick={onEditButtonClickTrue}>EDIT PLAYLIST</button>
                            </div>
                        </header>

                    </div>
                    ) : (
                        <div className="playlistHeader">
                            <header>
                                <div className="overlay">
                                    <p>
                                        <label htmlFor="title">Title</label>
                                        <input
                                        type="text"
                                        id="title"
                                        name="title"
                                        value={currentPlaylist.title}
                                        onChange={handleInputChange}
                                    /></p>
                                    <p><label htmlFor="author">Author</label>
                                        <input
                                            type="text"
                                            id="author"
                                            name="author"
                                            value={currentPlaylist.author}
                                            onChange={handleInputChange}
                                        /></p>
                                    <p><label htmlFor="genre">Genre</label>
                                        <input
                                            type="text"
                                            id="genre"
                                            name="genre"
                                            value={currentPlaylist.genre}
                                            onChange={handleInputChange}
                                        /></p>
                                    <p><label htmlFor="title">Sub Fee</label>
                                        <input
                                            type="text"
                                            id="subFee"
                                            name="subFee"
                                            value={currentPlaylist.subFee}
                                            onChange={handleInputChange}
                                        /></p>
                                    <br/>
                                    <button onClick={updatePlaylist}>SAVE PLAYLIST</button>
                                </div>
                            </header>

                        </div>
                    )}
                <div className="tbl-header">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Author</th>
                            <th>Duration</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div className="tbl-content">
                <table cellPadding="0" cellSpacing="0" border="0">
                <tbody>
                {currentPlaylistSongs &&
                currentPlaylistSongs.map((song, index) => (
                    <tr>
                        <td>{song.name}</td>
                        <td>{song.author}</td>
                        <td>{song.duration}</td>
                        <td><EditIcon
                            key={index}
                        /></td>
                    </tr>
                ))}
                </tbody>
                </table>
                </div>
                    <div
                        className="butn btn-one"
                        onClick={handleAddButtonClick}>
                        <span>ADD NEW SONG</span>
                    </div>
                </div>
            ) : (
                <div>
                    <br />
                    <p>Please click on a User...</p>
                </div>
            )}
            </div>
    );
};

export default Playlist;