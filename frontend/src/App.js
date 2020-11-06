import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthenticationService from "./app/services/AuthenticationService";

import Login from "./app/components/Login";
import Register from "./app/components/Registration";
import Home from "./app/components/Homepage";
import Users from "./app/components/Users";
import User from "./app/components/User";
import Playlist from "./app/components/Playlist";
import Playlists from "./app/components/Playlists";
import AddPlaylist from "./app/components/AddPlaylist";
import AddSong from "./app/components/AddSong";
import Songs from "./app/components/Songs";
import Song from "./app/components/Song";
import PlaylistSongs from "./app/components/PlaylistSongs";


const App = () => {

  const [showUsers, setShowUsers] = useState("");
  const [activeUser, setActiveUser] = useState(undefined);

  useEffect(() => {
    const activeUser = AuthenticationService.getCurrentToken();
    setActiveUser(activeUser);
  }, []);

  const logOut = () => {
    AuthenticationService.logout();
  };

  return (
      <Router>
        <div>
          <nav className="navbar navbar-expand-sm navbar-light">
            <div className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to={"/api/home"} className="nav-link">
                  Home
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/api/admin/users"} className="nav-link">
                  Users
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/api/admin/playlists"} className="nav-link">
                  Playlists
                </Link>
              </li>
              <li className="nav-item">
                <Link to={"/api/admin/addSong"} className="nav-link">
                  Songs
                </Link>
              </li>
                </div>

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
              <Route exact path="/api/admin/playlists" component={Playlists} />
              <Route exact path="/api/admin/songs" component={PlaylistSongs} />
              <Route path="/api/admin/playlists/:id" component={Playlist} />
              <Route path="/api/admin/songs/:id" component={Song} />
              <Route path="/api/admin/users/:id" component={User} />
              <Route path="/api/admin/addPlaylist" component={AddPlaylist}/>
              <Route exact path="/api/admin/addSong" component={AddSong}/>
              <Route path="/api/admin/playlists/:id" component={PlaylistSongs}/>
              <Route path="/api/admin/addSongsToPlaylist/:id" component={Songs}/>
            </Switch>
          </div>
        </div>
      </Router>
  );
};

export default App;