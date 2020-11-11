import React from "react";
import {Link} from "react-router-dom";

const HomeDashboard = () => {
    return(
        <div className="navbar navbar-expand-sm navbar-light">
            <div className="navbar-nav ml-auto">
                <li className="nav-item">
                    <Link to={"/api/login"} className="nav-link">
                        Login
                    </Link>
                </li>
                <li className="nav-item">
                    <Link to={"/api/register"} className="nav-link">
                        Sign up
                    </Link>
                </li>
            </div>
        </div>
    )
}
export default HomeDashboard;