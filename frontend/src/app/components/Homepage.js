import React from "react";
import AuthenticationService from "../services/AuthenticationService";

const Profile = () => {
    const currentUser = AuthenticationService.getCurrentUser();

    return (
        <div className="container">
            <header className="jumbotron">
                <h3>
                    <strong>{currentUser.username}</strong> Profile
                </h3>
            </header>
            <p>
                <strong>Id:</strong> {currentUser.id}
            </p>
            <p>
                <strong>Email:</strong> {currentUser.email}
            </p>
        </div>
    );
};

export default Profile;