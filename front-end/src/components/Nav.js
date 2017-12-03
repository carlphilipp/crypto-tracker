import React, {Component} from 'react';
import {Link} from 'react-router';
import {isLoggedIn, logout} from '../utils/AuthService';
import Login from './Login'
import SignUp from './SignUp'
import '../App.css';

class Nav extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userId: null,
        };
        this.handler = this.handler.bind(this);
        this.logout = this.logout.bind(this);
    }

    handler(userId) {
        console.log("Handle with user id " + userId);
        this.setState({
            userId: userId
        });
    }

    onRegister(status) {
        console.log("register in nav");
        this.props.onRegister(status);
    }

    home() {
        this.props.onUpdate('home');
    }

    user() {
        this.props.onUpdate('user');
    }

    logout() {
        logout();
        this.home();
    }

    render() {
        return (
            <nav className="navbar navbar-default">
                <div className="navbar-header">
                    <Link className="navbar-brand" to="#" onClick={this.home.bind(this)}>Home</Link>
                </div>
                <ul className="nav navbar-nav">
                    <li>
                        <Link to="#" onClick={this.home.bind(this)}>Tickers</Link>
                    </li>
                    <li>
                        {(isLoggedIn()) ? <Link to="#" onClick={this.user.bind(this)}>Account</Link> : ''}
                    </li>
                </ul>
                <ul className="nav navbar-nav navbar-right">
                    {(!isLoggedIn()) ? <Login handler={this.handler} buttonLabel="Login"/> : ''}
                    {(isLoggedIn()) ? <button className="btn btn-danger log" onClick={this.logout}>Log out </button> : ''}
                    {(!isLoggedIn()) ? <SignUp buttonLabel="Sign Up" onRegister={this.onRegister.bind(this)}/> : ''}
                </ul>
            </nav>
        );
    }
}

export default Nav;
