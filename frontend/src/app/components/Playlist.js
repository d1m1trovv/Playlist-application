import React, { useState, useEffect } from "react";
import RequestsService from "../services/RequestsService";

const Playlist = props => {
    const initialPlaylistState = {
        id: null,
        title: "",
        author: "",
        genre: "",
        subFee: ""
    };
    const [currentPlaylist, setCurrentPlaylist] = useState(initialPlaylistState);
    const [message, setMessage] = useState("");

    const getPlaylistById = id => {
        RequestsService.getPlaylistById(id)
            .then(response => {
                setCurrentPlaylist(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    useEffect(() => {
        getPlaylistById(props.match.params.id);
    }, [props.match.params.id]);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentPlaylist({ ...currentPlaylist, [name]: value });
    };


    const updatePlaylist = () => {
        RequestsService.updatePlaylist(currentPlaylist.id, currentPlaylist)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/playlists");
                setMessage("The user was updated successfully!");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const deletePlaylist = () => {
        RequestsService.deleteUser(currentPlaylist.id)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/playlists");
            })
            .catch(e => {
                console.log(e);
            });
    };

    return (
        <div>
            {currentPlaylist ? (
                <div className="edit-form">
                    <h4>Playlist</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="title">Title</label>
                            <input
                                type="text"
                                className="form-control"
                                id="title"
                                name="title"
                                value={currentPlaylist.title}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Username</label>
                            <input
                                type="text"
                                className="form-control"
                                id="author"
                                name="author"
                                value={currentPlaylist.author}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Genre</label>
                            <input
                                type="text"
                                className="form-control"
                                id="genre"
                                name="genre"
                                value={currentPlaylist.genre}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Subscription Fee</label>
                            <input
                                type="text"
                                className="form-control"
                                id="subFee"
                                name="subFee"
                                value={currentPlaylist.subFee}
                                onChange={handleInputChange}
                            />
                        </div>
                    </form>

                    <button className="badge badge-danger mr-2" onClick={deletePlaylist}>
                        Delete
                    </button>

                    <button
                        type="submit"
                        className="badge badge-success"
                        onClick={updatePlaylist}
                    >
                        Update
                    </button>
                    <p>{message}</p>
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