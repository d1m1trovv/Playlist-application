
import React, { useState, useEffect } from "react";
import {BrowserRouter as Router, Switch, Route, Link, Redirect} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "../styles/Styles.css"

import AuthenticationService from "../services/AuthenticationService";

import Login from "./Login";
import Register from "./Registration";
import Home from "./Homepage";
import Users from "./Users";
import User from "./User";
import Playlist from "./Playlist";
import Playlists from "./Playlists";
import AddPlaylist from "./AddPlaylist";
import AddSong from "./AddSong";
import AddSongsToPlaylist from "./AddSongsToPlaylist";
import Song from "./Song";
import PlaylistSongs from "./PlaylistSongs";
import DeleteSongsFromPlaylist from "./DeleteSongsFromPlaylist";

import PrivateRoute from "./PrivateRoute";
import Songs from "./Songs";


const Api = () => {

    const [showUsers, setShowUsers] = useState("");
    const [activeUser, setActiveUser] = useState(undefined);
    const [isCurrentUserAdmin, setIsCurrentUserAdmin] = useState(false);

    useEffect(() => {
        const activeUser = AuthenticationService.getCurrentToken();
        setActiveUser(activeUser);
        setIsCurrentUserAdmin(AuthenticationService.isUserAdmin());
        console.log(isCurrentUserAdmin);
    }, []);

    const logOut = () => {
        AuthenticationService.logout();
    };

    return (
        <Router>
            <div>
                <nav className="navbar navbar-expand-sm navbar-light">
                    {isCurrentUserAdmin ? (
                        <div className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <Link to={"/api/home"} className="nav-link">
                                    Home
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/api/user/playlists"} className="nav-link">
                                    Playlists
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/api/admin/users"} className="nav-link">
                                    Users
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/api/admin/songs"} className="nav-link">
                                    Songs
                                </Link>
                            </li>
                        </div>) : (
                        <div className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <Link to={"/api/home"} className="nav-link">
                                    Home
                                </Link>
                            </li>
                            <li className="nav-item">
                                <Link to={"/api/user/playlists"} className="nav-link">
                                    Playlists
                                </Link>
                            </li>
                        </div>
                    )}

                    {activeUser ? (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <a href="/" className="nav-link" onClick={logOut}>
                                    LogOut
                                </a>
                            </li>
                        </div>
                    ) : (
                        <div className="navbar-nav ml-auto">
                            <li className="nav-item">
                                <Link to={"/api/login"} className="nav-link">
                                    Login
                                </Link>
                            </li>

                            <li className="nav-item">
                                <Link to={"/api/register"} className="nav-link">
                                    Sign Up
                                </Link>
                            </li>
                        </div>
                    )}
                </nav>

                <div className="container-fluid">
                    <Switch>
                        <Route exact path="/api/login" component={Login} />
                        <Route exact path="/api/register" component={Register} />
                        <Route exact path="/api/admin/users" component={Users} />
                        <Route exact path="/api/user/playlists" component={Playlists} />
                        <Route exact path="/api/admin/songs" component={Songs} />
                        <Route path="/api/user/playlists/:id" component={Playlist} />
                        <Route path="/api/admin/users/:id" component={User} />
                        <Route path="/api/admin/addPlaylist" component={AddPlaylist}/>
                        <Route path="/api/admin/songs/addSong" component={AddSong}/>
                        {/*<Route path="/api/admin/playlists/:id" component={PlaylistSongs}/>*/}
                        <Route path="/api/admin/addSongsToPlaylist/:id" component={AddSongsToPlaylist}/>
                        <Route path="/api/admin/deleteSongsFromPlaylist/:id" component={DeleteSongsFromPlaylist}/>
                    </Switch>
                </div>
            </div>
        </Router>
    );
};

export default Api;