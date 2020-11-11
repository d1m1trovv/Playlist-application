import {Link} from "react-router-dom";
import React from "react";
import AuthenticationService from "../services/AuthenticationService";

const UserDashboard = () => {

    const logOut = () => {
        AuthenticationService.logout();
    };

    return (
        <div className="navbar navbar-expand-sm navbar-light">
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
                    <Link to={"/api/profile"} className="nav-link">
                        Profile
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={"/api/user/subscriptions"} className="nav-link">
                        Subscriptions
                    </Link>
                </li>
            </div>
            <div className="navbar-nav ml-auto">
                <li className="nav-item">
                    <a href="/" className="nav-link" onClick={logOut}>
                        LogOut
                    </a>
                </li>
            </div>
        </div>
    )
}
export default UserDashboard;