import React, {useEffect, useRef, useState} from "react";
import AuthenticationService from "../services/AuthenticationService";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import RequestsService from "../services/RequestsService";

const isRequired = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const AddSong = (props) => {

    const initialPlaylistState = {
        id: null,
        name: "",
        author: "",
        duration: "",
    };

    const form = useRef();
    const checkBtn = useRef();

    const [currentPlaylist, setCurrentPlaylist] = useState(initialPlaylistState);
    const [name, setName] = useState("");
    const [author, setAuthor] = useState("");
    const [duration, setDuration] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const handleNameChange = (e) => {
        const name = e.target.value;
        setName(name);
    };

    const handleAuthorChange = (e) => {
        const author = e.target.value;
        setAuthor(author);
    };

    const handleDurationChange = (e) => {
        const duration = e.target.value;
        setDuration(duration);
    };

    const getCurrentPlaylistById = id => {
        RequestsService.findPlaylistById(id)
            .then(response => {
                setCurrentPlaylist(response.data);
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    };

    const handleAddSong = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();
        if (checkBtn.current.context._errors.length === 0) {
            RequestsService.addSongToPlaylist(name, author, duration).then(
                (response) => {
                    setMessage(response.data.message);
                    props.history.push("/api/admin/playlists");
                    setSuccessful(true);
                },
                (error) => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    setMessage(resMessage);
                    setSuccessful(false);
                }
            );
        }
    };

    return (
        <div className="col-md-12">
            <div className="card card-container">
                <img
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    alt="profile-img"
                    className="profile-img-card"
                />

                <Form onSubmit={handleAddSong} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="name">Name</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="name"
                                    value={name}
                                    onChange={handleNameChange}
                                    validations={[isRequired]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="author">Author</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="author"
                                    value={author}
                                    onChange={handleAuthorChange}
                                    validations={[isRequired]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="duration">Duration</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="duration"
                                    value={duration}
                                    onChange={handleDurationChange}
                                    validations={[isRequired]}
                                />
                            </div>

                            <div className="form-group">
                                <button className="btn btn-primary btn-block">Add Song</button>
                            </div>
                        </div>
                    )}

                    {message && (
                        <div className="form-group">
                            <div
                                className={
                                    successful ? "alert alert-success" : "alert alert-danger"
                                }
                                role="alert"
                            >
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            </div>
        </div>
    );
};

export default AddSong;