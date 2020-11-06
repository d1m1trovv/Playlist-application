import React, {useEffect, useState} from "react";
import RequestsService from "../services/RequestsService";
import EditIcon from "@material-ui/icons/Edit";
import {Link} from "react-router-dom";

const PlaylistSongs = props => {
    const [playlistsSongs, setPlaylistsSongs] = useState([]);
    const [currentPlaylist, setCurrentPlaylist] = useState(null);

    useEffect(() => {
        getPlaylistById(props.match.params.id);
        console.log(props.match.params.id);
    }, [props.match.params.id]);

    const getPlaylistSongs = id => {
        RequestsService.getPlaylistSongs(id).then(
            (response) => {
                console.log(props.match.params.id)
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
                console.log(props.match.params.id)

                setPlaylistsSongs(_playlistSongs);
            }
        );
    }

    const getPlaylistById = id => {
        RequestsService.findPlaylistById(id)
            .then(response => {
                setCurrentPlaylist(response.data);
                getPlaylistSongs(currentPlaylist.id);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

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