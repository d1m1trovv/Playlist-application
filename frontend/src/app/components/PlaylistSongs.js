import React, {useEffect, useState} from "react";
import RequestsService from "../services/RequestsService";
import EditIcon from "@material-ui/icons/Edit";
import {Link} from "react-router-dom";

const PlaylistSongs = (playlistId) => {
    const [playlistsSongs, setPlaylistsSongs] = useState([]);
    const [currentPlaylist, setCurrentPlaylist] = useState(null);

    useEffect(() => {
        RequestsService.getPlaylistSongs(playlistId).then(
            (response) => {
                setPlaylistsSongs(response.data);
                console.log(response.data);
            },
            (error) => {
                const _playlistSongs =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setPlaylistsSongs(_playlistSongs);
            }
        );
    }, [])

    return (
        <section className="mainSec">
            <div>
                <h1>{currentPlaylist.title} songs</h1>
                <div className="tbl-header">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Author</th>
                            <th>Duration</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div className="tbl-content">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <tbody>
                        {playlistsSongs &&
                        playlistsSongs.map((song, index) => (
                            <tr>
                                <td>{song.name}</td>
                                <td>{song.author}</td>
                                <td>{song.duration}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
            <Link
                to={"/api/admin/addPlaylist"}
                className="badge badge-success"
            >
                ADD PLAYLIST
            </Link>
        </section>
    )

}
export default PlaylistSongs;