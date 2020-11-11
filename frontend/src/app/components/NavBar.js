import {Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import AuthenticationService from "../services/AuthenticationService";

const NavBar = () => {

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
        <div>
        {isCurrentUserAdmin && activeUser ? (
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
        ) : (
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
        </div>
    )
}
export default NavBar;