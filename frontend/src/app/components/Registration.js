import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import isEmail from "validator/lib/isEmail";


import AuthenticationService from "../services/AuthenticationService";

const isRequired = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const isEmailValid = (value) => {
    if (!isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                This is not a valid email.
            </div>
        );
    }
};

const isUsernameValid = (value) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

const isValidPassword = (value) => {
    if (value.length < 6 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

const Register = (props) => {
    const form = useRef();
    const checkBtn = useRef();

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [pass, setPass] = useState("");
    const [confirmPass, setConfirmPass] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const handleUsernameChange = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const handleEmailChange = (e) => {
        const email = e.target.value;
        setEmail(email);
    };

    const handlePasswordChange = (e) => {
        const password = e.target.value;
        setPass(password);
    };

    const handleConfirmPasswordChange = (e) => {
        const confirmPassword = e.target.value;
        setConfirmPass(confirmPassword);
    };

    const handleRegister = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        Form.current.validateAll();

        if (CheckButton.current.context._errors.length === 0) {
            AuthenticationService.register(username, email, pass, confirmPass).then(
                (response) => {
                    setMessage(response.data.message);
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

                <Form onSubmit={handleRegister} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="username">Email</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="username"
                                    value={email}
                                    onChange={handleEmailChange}
                                    validations={[isRequired, isEmailValid]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="email">Username</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="email"
                                    value={username}
                                    onChange={handleUsernameChange}
                                    validations={[isRequired, isUsernameValid]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Password</label>
                                <Input
                                    type="password"
                                    className="form-control"
                                    name="password"
                                    value={pass}
                                    onChange={handlePasswordChange}
                                    validations={[isRequired, isValidPassword]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Confirm password</label>
                                <Input
                                    type="password"
                                    className="form-control"
                                    name="confirmPassword"
                                    value={confirmPass}
                                    onChange={handleConfirmPasswordChange}
                                    validations={[isRequired, isValidPassword]}
                                />
                            </div>

                            <div className="form-group">
                                <button className="btn btn-primary btn-block">Sign Up</button>
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

export default Register;