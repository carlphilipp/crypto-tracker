import React, {Component} from 'react';
import {Link} from 'react-router';
import {isLoggedIn, logout} from '../utils/AuthService';
import Login from './Login'
import SignUp from './SignUp'
import { Button, Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, NavLink } from 'reactstrap';
import '../App.css';

class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userId: null,
            isOpen: false
        };
        this.handler = this.handler.bind(this);
        this.logout = this.logout.bind(this);
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
      this.setState({
        isOpen: !this.state.isOpen
      });
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
            <div>
                <Navbar color="faded" light expand="md">
                    <NavbarBrand href="#" onClick={this.home.bind(this)} className="mx-auto">crypto tracker</NavbarBrand>
                    <NavbarToggler onClick={this.toggle} />
                    <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="ml-auto" navbar>
                            <NavItem>
                              {(isLoggedIn()) ? <Link to="#" onClick={this.home.bind(this)}>Tickers</Link>: ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem>
                              {(isLoggedIn()) ? <Link to="#" onClick={this.user.bind(this)}>Account</Link> : ''}
                            </NavItem>
                            <NavItem>
                                {(!isLoggedIn()) ? <Login handler={this.handler} buttonLabel="Login"/> : ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem>
                                  {(!isLoggedIn()) ? <SignUp buttonLabel="Sign Up" onRegister={this.onRegister.bind(this)}/> : ''}
                            </NavItem>
                            <NavItem>
                                  {(isLoggedIn()) ? <Button size="lg" className="danger" onClick={this.logout}>Log out</Button> : ''}
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>

        );
    }
}

export default Header;
