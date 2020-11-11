import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import PlayCircleFilled from "@material-ui/icons/PlayCircleFilled";


const Songs = (props) => {
    const [songs, setSongs] = useState([]);
    const [currentPlaylist, setCurrentPlaylist] = useState(null);
    const [currentPlaylistIndex, setCurrentPlaylistIndex] = useState(-1);
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

    const setEditablePlaylist = (playlist, index) => {
        setCurrentPlaylist(playlist);
        setCurrentPlaylistIndex(index);
        props.history.push("/api/admin/playlists/" + playlist.id)
        console.log("Playlist: " + playlist.title);
    }

    const getPlaylistByTitle = () => {
        RequestsService.findByEmail(currentPlaylistTitle)
            .then(response => {
                setSongs(response.data);
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

    const handleCreateButtonClick = () => {
        props.history.push("/api/admin/songs/addSong");
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
                            <tr
                                onClick={() => setEditablePlaylist(song, index)}
                                key={index}
                            >
                                <td>{index + 1}</td>
                                <td>{song.name}</td>
                                <td>{song.author}</td>
                                <td>{song.duration}</td>
                                <td><PlayCircleFilled/></td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <div
                className="butn btn-one"
                onClick={handleCreateButtonClick}
            >
                <span>CREATE NEW SONG</span>
            </div>
        </section>
    )

}
export default Songs;