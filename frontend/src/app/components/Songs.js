import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import EditIcon from "@material-ui/icons/Edit";

const Songs = () => {
    const [songs, setSongs] = useState([]);
    const [currentSong, setCurrentSong] = useState(null);
    const [currentSongIndex, setCurrentSongIndex] = useState(-1);
    const [currentPlaylistTitle, setCurrentPlaylistTitle] = useState("");

    useEffect(() => {
        RequestsService.getSongs().then(
            (response) => {
                setSongs(response.data);
                console.log(response.data);
            },
            (error) => {
                const _songs =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setSongs(_songs);
            }
        );
    }, [])

    const setEditableSong = (song, index) => {
        setCurrentSong(song);
        setCurrentSongIndex(index);
        console.log("Playlist: " + song.name);
    }

    const getSongByName = () => {
        RequestsService.findSongByName(currentPlaylistTitle)
            .then(response => {
                setSongs(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleChangeName = (e) => {
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
                            onChange={handleChangeName}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={getSongByName}
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <h1>Songs</h1>
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
                        {songs &&
                        songs.map((song, index) => (
                            <tr>
                                <td>{song.name}</td>
                                <td>{song.author}</td>
                                <td>{song.duration}</td>
                                <td><EditIcon
                                    onClick={() => setEditableSong(song, index)}
                                    key={index}
                                /></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                    <div className="info">
                        {currentSong ? (
                            <div>
                                <h4>Song</h4>
                                <div>
                                    <label>
                                        <strong>Name:</strong>
                                    </label>{" "}
                                    {currentSong.name}
                                </div>
                                <div>
                                    <label>
                                        <strong>Author:</strong>
                                    </label>{" "}
                                    {currentSong.author}
                                </div>
                                <div>
                                    <label>
                                        <strong>Duration:</strong>
                                    </label>{" "}
                                    {currentSong.duration}
                                </div>
                                <div>
                                    <Link
                                        to={"/api/admin/playlists/" + currentSong.id}
                                        className="badge badge-success"
                                    >
                                        EDIT PLAYLIST
                                    </Link>
                                </div>

                                <div>
                                    <Link
                                        to={"/api/admin/songs/addToPlaylist"}
                                        className="badge badge-success"
                                    >
                                        ADD TO PLAYLIST
                                    </Link>
                                </div>
                            </div>
                        ) : (
                            <div>
                                <br />
                                <p>Click on playlist</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <Link
                to={"/api/admin/addSong"}
                className="badge badge-success"
            >
                ADD SONG
            </Link>
        </section>
    )

}
export default Songs;