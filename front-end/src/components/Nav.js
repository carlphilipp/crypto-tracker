import React, {Component} from 'react';
import {Link} from 'react-router';
import {isLoggedIn, login, logout} from '../utils/AuthService';
import Login from './Login'
import SignUp from './SignUp'
import '../App.css';

class Nav extends Component {

    render() {
        return (
            <nav className="navbar navbar-default">
                <div className="navbar-header">
                    <Link className="navbar-brand" to="/">Home</Link>
                </div>
                <ul className="nav navbar-nav">
                    <li>
                        <Link to="/">Tickers</Link>
                    </li>
                    <li>
                        {(isLoggedIn()) ? <Link to="/account">Account</Link> : ''}
                    </li>
                </ul>
                <ul className="nav navbar-nav navbar-right">
                {<Login buttonLabel="Login"/>}
                <button className="btn btn-danger log" onClick={() => logout()}>Log out </button>

                {<SignUp buttonLabel="Sign Up"/>}
                </ul>
            </nav>
        );
    }
}

export default Nav;
