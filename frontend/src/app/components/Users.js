import React, { useState, useEffect } from "react";
import {Link} from "react-router-dom";

import RequestsService from "../services/RequestsService";
import EditIcon from "@material-ui/icons/Edit";

const Users = () => {
    const [users, setUsers] = useState([]);
    const [currentUser, setCurrentUser] = useState(null);
    const [currentUserIndex, setCurrentUserIndex] = useState(-1);
    const [currentUserEmail, setCurrentUserEmail] = useState("");

    useEffect(() => {
        RequestsService.getUsers().then(
            (response) => {
                setUsers(response.data);
                console.log(response.data);
            },
            (error) => {
                const _users =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setUsers(_users);
            }
        );
    }, [])

    const setEditableUser = (user, index) => {
        setCurrentUser(user);
        setCurrentUserIndex(index);
    }

    const getUserByEmail = () => {
        RequestsService.findByEmail(currentUserEmail)
            .then(response => {
                setUsers(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleChangeEmail = (e) => {
        const currentUserEmail = e.target.value;
        setCurrentUserEmail(currentUserEmail);
    }

    return (
        <section className="mainSec">
        <div>
            <div className="col-md-8">
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search by title"
                        value={currentUserEmail}
                        onChange={handleChangeEmail}
                    />
                    <div className="input-group-append">
                        <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={getUserByEmail}
                        >
                            Search
                        </button>
                    </div>
                </div>
            </div>
            <h1>Playlists</h1>
                <div className="tbl-header">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <thead>
                        <tr>
                            <th>Email</th>
                            <th>Username</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div className="tbl-content">
                    <table cellPadding="0" cellSpacing="0" border="0">
                        <tbody>
                            {users &&
                            users.map((user, index) => (
                                <tr>
                                    <td>{user.email}</td>
                                    <td>{user.username}</td>
                                    <td><EditIcon
                                        onClick={() => setEditableUser(user, index)}
                                        key={index}
                                    ><Link>
                                    </Link></EditIcon></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
            <div className="info">
                {currentUser ? (
                    <div>
                        <h4>Tutorial</h4>
                        <div>
                            <label>
                                <strong>Title:</strong>
                            </label>{" "}
                            {currentUser.email}
                        </div>
                        <div>
                            <label>
                                <strong>Description:</strong>
                            </label>{" "}
                            {currentUser.username}
                        </div>

                        <Link
                            to={"/api/admin/users/" + currentUser.id}
                            className="badge badge-warning"
                        >
                            OPTIONS
                        </Link>
                    </div>
                ) : (
                    <div>
                        <br />
                        <p>Please click on a User...</p>
                    </div>
                )}
            </div>
            </div>
        </div>
        </section>
    )

}
export default Users;