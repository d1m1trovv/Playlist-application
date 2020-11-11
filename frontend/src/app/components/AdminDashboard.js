import {Link} from "react-router-dom";
import React from "react";
import AuthenticationService from "../services/AuthenticationService";

const AdminDashboard = () => {

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
                        <Link to={"/api/admin/users"} className="nav-link">
                            Users
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link to={"/api/admin/songs"} className="nav-link">
                            Songs
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
export default AdminDashboard;