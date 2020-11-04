import React, { useState, useEffect } from "react";
import RequestsService from "../services/RequestsService";

const User = props => {
    const initialUserState = {
        id: null,
        email: "",
        username: ""
    };
    const [currentUser, setCurrentUser] = useState(initialUserState);
    const [message, setMessage] = useState("");

    const getUserById = id => {
        RequestsService.findUserById(id)
            .then(response => {
                setCurrentUser(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    useEffect(() => {
        getUserById(props.match.params.id);
    }, [props.match.params.id]);

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentUser({ ...currentUser, [name]: value });
    };


    const updateUser = () => {
       RequestsService.updateUser(currentUser.id, currentUser)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/users");
                setMessage("The user was updated successfully!");
            })
            .catch(e => {
                console.log(e);
            });
    };

    const deleteUser = () => {
        RequestsService.deleteUser(currentUser.id)
            .then(response => {
                console.log(response.data);
                props.history.push("/api/admin/users");
            })
            .catch(e => {
                console.log(e);
            });
    };

    return (
        <div>
            {currentUser ? (
                <div className="edit-form">
                    <h4>User {currentUser.email}</h4>
                    <form>
                        <div className="form-group">
                            <label htmlFor="title">Email</label>
                            <input
                                type="text"
                                className="form-control"
                                id="email"
                                name="email"
                                value={currentUser.email}
                                onChange={handleInputChange}
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="description">Username</label>
                            <input
                                type="text"
                                className="form-control"
                                id="username"
                                name="username"
                                value={currentUser.username}
                                onChange={handleInputChange}
                            />
                        </div>
                    </form>

                    <button className="badge badge-danger mr-2" onClick={deleteUser}>
                        Delete
                    </button>

                    <button
                        type="submit"
                        className="badge badge-success"
                        onClick={updateUser}
                    >
                        Update
                    </button>
                    <p>{message}</p>
                </div>
            ) : (
                <div>
                    <br />
                    <p>Please click on a User...</p>
                </div>
            )}
        </div>
    );
};

export default User;
