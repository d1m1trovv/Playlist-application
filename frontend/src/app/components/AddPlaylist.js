import React, {useRef, useState} from "react";
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

const AddPlaylist = (props) => {
    const form = useRef();
    const checkBtn = useRef();

    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [genre, setGenre] = useState("");
    const [subFee, setSubFee] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const handleTitleChange = (e) => {
        const title = e.target.value;
        setTitle(title);
    };

    const handleAuthorChange = (e) => {
        const author = e.target.value;
        setAuthor(author);
    };

    const handleGenreChange = (e) => {
        const genre = e.target.value;
        setGenre(genre);
    };

    const handleSubFeeChange = (e) => {
        const subFee = e.target.value;
        setSubFee(subFee);
    };

    const handleAddPlaylist = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();
        if (checkBtn.current.context._errors.length === 0) {
            RequestsService.addPlaylist(title, author, genre, subFee).then(
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

                <Form onSubmit={handleAddPlaylist} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="title">Title</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="title"
                                    value={title}
                                    onChange={handleTitleChange}
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
                                <label htmlFor="genre">Genre</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="genre"
                                    value={genre}
                                    onChange={handleGenreChange}
                                    validations={[isRequired]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="subFee">Subscription fee</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="subFee"
                                    value={subFee}
                                    onChange={handleSubFeeChange}
                                    validations={[isRequired]}
                                />
                            </div>

                            <div className="form-group">
                                <button className="btn btn-primary btn-block">Add Playlist</button>
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

export default AddPlaylist;