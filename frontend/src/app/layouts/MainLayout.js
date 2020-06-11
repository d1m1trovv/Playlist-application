import React, { Component } from 'react';
import {NavLink} from 'react-router-dom';
import Constants from "../constants/Constants";

export default class MainLayout extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <Constants.BODY>
            <section>
            <ul>
            <li><NavLink exact={true} activeStyle={{background:'#ffffff'}} to="/">Home</NavLink></li>
        <li><NavLink activeStyle={{background:'#fff'}} to="/users">Users</NavLink></li>
        <li><NavLink activeStyle={{background:'#fff'}} to="/posts">Posts</NavLink></li>
        </ul>
        </section>
        {this.props.children}
    </Constants.BODY>
    )
    }
}