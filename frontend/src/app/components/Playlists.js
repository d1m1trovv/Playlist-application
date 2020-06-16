import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import EditIcon from "@material-ui/icons/Edit";

const Playlists = () => {
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
                <div className="col-md-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by title"
                            value={currentPlaylistTitle}
                            onChange={handleChangeTitle}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={getPlaylistByTitle}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <h1>Playlists</h1>
                <div className="tbl-header">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <thead>
                        <tr>
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
                            <tr>
                                <td>{playlist.title}</td>
                                <td>{playlist.author}</td>
                                <td>{playlist.genre}</td>
                                <td>{playlist.subFee}</td>
                                <td><EditIcon
                                    onClick={() => setEditablePlaylist(playlist, index)}
                                    key={index}
                                ><Link>
                                </Link></EditIcon></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <div className="info">
                        {currentPlaylist ? (
                            <div>
                                <h4>Playlist</h4>
                                <div>
                                    <label>
                                        <strong>Title:</strong>
                                    </label>{" "}
                                    {currentPlaylist.title}
                                </div>
                                <div>
                                    <label>
                                        <strong>Description:</strong>
                                    </label>{" "}
                                    {currentPlaylist.author}
                                </div>
                                <div>
                                    <label>
                                        <strong>Description:</strong>
                                    </label>{" "}
                                    {currentPlaylist.genre}
                                </div>
                                <div>
                                    <label>
                                        <strong>Description:</strong>
                                    </label>{" "}
                                    {currentPlaylist.subFee}
                                </div>

                                <Link
                                    to={"/api/admin/playlists/" + currentPlaylist.id}
                                    className="badge badge-warning"
                                >
                                    OPTIONS
                                </Link>
                            </div>
                        ) : (
                            <div>
                                <br />
                                <p>Please click on a User...</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </section>
    )

}
export default Playlists;