import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import AddIcon from "@material-ui/icons/Add";

const AddSongsToPlaylist = (props) => {

    const initialSongState = {
        id: null,
        name: "",
        author: "",
        duration: ""
    };

    const [songs, setSongs] = useState([]);
    const [currentSong, setCurrentSong] = useState(initialSongState);
    const [currentSongIndex, setCurrentSongIndex] = useState(-1);
    const [currentPlaylistTitle, setCurrentPlaylistTitle] = useState("");
    const [showPopUp, setShowPopUp] = useState(false);

    useEffect(() => {
        RequestsService.getSongsToAdd(props.match.params.id).then(
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

    const showPopUpOnClick = () =>{
        setShowPopUp(true);
    }

    const putSongInPlaylist = (song) => {
        RequestsService.addSongToPlaylistBySongId(props.match.params.id, song)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/playlists/" + props.match.params.id);
                console.log(song);
            })
            .catch(e => {
                console.log(e);
                console.log("Current song: " + currentSong);
            });
    }

    const setEditableSong = (song, index) => {
        setCurrentSong(song);
        setCurrentSongIndex(index);
        showPopUpOnClick();
    }

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
                            >
                                Search
                            </button>
                        </div>
                    </div>
                </div>
                <h1>SELECT SONGS TO ADD..</h1>
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
                            <tr
                                onClick={() => setEditableSong(song, index)}
                                key={index}
                            >
                                <td>{song.name}</td>
                                <td>{song.author}</td>
                                <td>{song.duration}</td>
                                <td>{showPopUp && currentSongIndex === index ?
                                <AddIcon
                                    onClick={() => putSongInPlaylist(song)}
                                />
                                    : null
                                }</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <div className="butn btn-one">
                <span>CREATE NEW PLAYLIST</span>
            </div>
        </section>
    )

}
export default AddSongsToPlaylist;