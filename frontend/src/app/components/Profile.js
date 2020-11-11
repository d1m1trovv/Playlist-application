import React, {useEffect, useState} from "react";
import RequestsService from "../services/RequestsService";

const Profile = () => {

    const initialState = {
        username: "",
        email: ""
    }

    const [currentUser, setCurrentUser] = useState(initialState);
    const [isEditable, setIsEditable] = useState(false);

    const getUser = () => {
        RequestsService.getUser("profile")
            .then(response => {
                setCurrentUser(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleInputChange = event => {
        const {name, value} = event.target;
        setCurrentUser({...currentUser, [name]: value});
    };

    const onEditButtonClickTrue = () => {
        setIsEditable(true);
    }

    useEffect(() => {
        getUser();
    }, []);

    return (
        <div className="playlistSec">
            {currentUser ? (
                <div className="container-fluid">
                    {!isEditable ? (
                        <div className="playlistHeader">
                            <header>
                                <div className="overlay">
                                    <h1>{currentUser.email}</h1>
                                    <h1>{currentUser.username}</h1>
                                    <br/>
                                    <div className="btn btn-one">
                                        <span onClick={onEditButtonClickTrue}>EDIT PLAYLIST</span>
                                    </div>
                                </div>
                            </header>

                        </div>
                    ) : (
                        <div className="playlistHeader">
                            <header>
                                <div className="overlay">
                                    <p>
                                        <label htmlFor="email">Email</label>
                                        <input
                                            type="text"
                                            id="email"
                                            name="email"
                                            value={currentUser.email}
                                            onChange={handleInputChange}
                                        /></p>
                                    <p><label htmlFor="username">Username</label>
                                        <input
                                            type="text"
                                            id="username"
                                            name="username"
                                            value={currentUser.username}
                                            onChange={handleInputChange}
                                        /></p>
                                    <br/>
                                    <button>SAVE PLAYLIST</button>
                                </div>
                            </header>
                        </div>
                    )}
                </div>
            ):null}
        </div>
    )
}
export default Profile;
