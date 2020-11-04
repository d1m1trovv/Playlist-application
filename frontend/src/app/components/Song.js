import React, { useState, useEffect } from "react";
import RequestsService from "../services/RequestsService";
import {Link} from "react-router-dom";

const Song = props => {
    const initialSongState = {
        id: null,
        name: "",
        author: "",
        duration: ""
    };
    const [currentSong, setCurrentSong] = useState(initialSongState);
    const [message, setMessage] = useState("");

    const getSongById = id => {
        RequestsService.findPlaylistById(id)
            .then(response => {
                setCurrentSong(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    useEffect(() => {
        getSongById(props.match.params.id);
    }, [props.match.params.id]);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentSong({ ...currentSong, [name]: value });
    };


    const updateSong = () => {
        RequestsService.updateSong(currentSong.id, currentSong)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/songs");
                setMessage("The user was updated successfully!");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const deleteSong = () => {
        RequestsService.deleteSong(currentSong.id)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/songs");
            })
            .catch(e => {
                console.log(e);
            });
    };

    return (
        <div>
            {currentSong ? (
                <div className="edit-form">
                    <h4>Song</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="title">Name</label>
                            <input
                                type="text"
                                className="form-control"
                                id="name"
                                name="name"
                                value={currentSong.name}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="author">Author</label>
                            <input
                                type="text"
                                className="form-control"
                                id="author"
                                name="author"
                                value={currentSong.author}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="duration">Duration</label>
                            <input
                                type="text"
                                className="form-control"
                                id="duration"
                                name="duration"
                                value={currentSong.duration}
                                onChange={handleInputChange}
                            />
                        </div>
                    </form>

                    <button className="badge badge-danger mr-2" onClick={deleteSong}>
                        Delete
                    </button>

                    <button
                        type="submit"
                        className="badge badge-success"
                        onClick={updateSong}
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

export default Song;