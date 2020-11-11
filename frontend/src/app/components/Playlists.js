import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import AuthenticationService from "../services/AuthenticationService";

const Playlists = (props) => {
    const [playlists, setPlaylists] = useState([]);
    const [currentPlaylist, setCurrentPlaylist] = useState(null);
    const [currentPlaylistIndex, setCurrentPlaylistIndex] = useState(-1);
    const [currentPlaylistTitle, setCurrentPlaylistTitle] = useState("");

    useEffect(() => {
        RequestsService.getPlaylists().then(
            (response) => {
                setPlaylists(response.data);
                console.log(response.data);
            },
            (error) => {
                const _playlists =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setPlaylists(_playlists);
            }
        );
    }, [])

    const setEditablePlaylist = (playlist, index) => {
        setCurrentPlaylist(playlist);
        setCurrentPlaylistIndex(index);
        props.history.push("/api/user/playlists/" + playlist.id)
        console.log("Playlist: " + playlist.title);
    }

    const getPlaylistByTitle = () => {
        RequestsService.findByEmail(currentPlaylistTitle)
            .then(response => {
                setPlaylists(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleChangeTitle = (e) => {
        const currentPlaylistTitle = e.target.value;
        setCurrentPlaylistTitle(currentPlaylistTitle);
    }

    return (
        <section className="mainSec">
            <div>
                <h1>Playlists</h1>
                <div className="col-md-0">
                    <div className="input-group mt-5">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by title"
                            value={currentPlaylistTitle}
                            onChange={handleChangeTitle}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-one"
                                type="button"
                                onClick={getPlaylistByTitle}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <div className="tbl-header">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>Subscribe price</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div className="tbl-content">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <tbody>
                        {playlists &&
                        playlists.map((playlist, index) => (
                            <tr
                                onClick={() => setEditablePlaylist(playlist, index)}
                                key={index}
                            >
                                <td>{index + 1}</td>
                                <td>{playlist.title}</td>
                                <td>{playlist.author}</td>
                                <td>{playlist.genre}</td>
                                <td>{playlist.subFee}</td>
                                <td><button className="btn btn-one">
                                    <span>SUBSCRIBE</span>
                                </button></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
            {AuthenticationService.isUserAdmin() ? (
            <div className="butn btn-one">
                <span>CREATE NEW PLAYLIST</span>
            </div> ) :null}
        </section>
    )

}
export default Playlists;