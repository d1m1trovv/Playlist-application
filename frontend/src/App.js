import React, { useState, useEffect } from "react";
import {BrowserRouter as Router, Switch, Route, Link, Redirect} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthenticationService from "./app/services/AuthenticationService";

import Login from "./app/components/Login";
import Register from "./app/components/Registration";
import Users from "./app/components/Users";
import User from "./app/components/User";
import Playlist from "./app/components/Playlist";
import Playlists from "./app/components/Playlists";
import AddPlaylist from "./app/components/AddPlaylist";
import AddSong from "./app/components/AddSong";
import AddSongsToPlaylist from "./app/components/AddSongsToPlaylist";
import Song from "./app/components/Song";
import DeleteSongsFromPlaylist from "./app/components/DeleteSongsFromPlaylist";
import Songs from "./app/components/Songs";
import AdminDashboard from "./app/components/AdminDashboard";
import UserDashboard from "./app/components/UserDashboard";
import HomeDashboard from "./app/components/HomeDashboard";
import Profile from "./app/components/Profile";
import UserPlaylists from "./app/components/UserPlaylists";


const App = () => {

  const [showUsers, setShowUsers] = useState("");
  const [activeUser, setActiveUser] = useState(undefined);
  const [isCurrentUserAdmin, setIsCurrentUserAdmin] = useState(false);

  useEffect(() => {
    const activeUser = AuthenticationService.getCurrentToken();
    setActiveUser(activeUser);
    setIsCurrentUserAdmin(AuthenticationService.isUserAdmin());
    console.log(isCurrentUserAdmin);
  }, []);

  return (
      <Router>
          <div>
              {activeUser ? (
                  <div>
                    {isCurrentUserAdmin ? (
                        <AdminDashboard/>
                    ):(
                        <UserDashboard/>
                    )}
                  </div>
              ) : (
                    <HomeDashboard/>
              )}

          <div className="container-fluid">
            <Switch>
              <Route exact path="/api/login" component={Login} />
              <Route exact path="/api/register" component={Register} />
              <Route exact path="/api/admin/users" component={Users} />
              <Route exact path="/api/user/playlists" component={Playlists} />
              <Route exact path="/api/admin/songs" component={Songs} />
              <Route path="/api/profile" component={Profile} />
              <Route path="/api/user/playlists/:id" component={Playlist} />
              <Route path="/api/admin/users/:id" component={User} />
              <Route path="/api/admin/addPlaylist" component={AddPlaylist}/>
              <Route path="/api/admin/songs/addSong" component={AddSong}/>
              <Route path="/api/user/subscriptions" component={UserPlaylists}/>
              {/*<Route path="/api/admin/playlists/:id" component={PlaylistSongs}/>*/}
              <Route path="/api/admin/addSongsToPlaylist/:id" component={AddSongsToPlaylist}/>
              <Route path="/api/admin/deleteSongsFromPlaylist/:id" component={DeleteSongsFromPlaylist}/>
            </Switch>
          </div>
        </div>
      </Router>
  );
};

export default App;