import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import AuthenticationService from "../services/AuthenticationService";

const PrivateRoute = ({component: Component, ...rest}) => {
    return (

        <Route {...rest} render={props => (
            AuthenticationService.isUserAdmin() ?
                <Component {...props} />
                : <Redirect to="/api/user/playlists/:id" />
        )} />
    );
};

export default PrivateRoute;