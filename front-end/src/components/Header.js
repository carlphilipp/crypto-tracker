import React, {Component} from 'react';
import {Link} from 'react-router';
import {isLoggedIn} from '../service/AuthService';
import Login from './modals/Login'
import SignUp from './modals/SignUp'
import { Button, Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem } from 'reactstrap';
import '../App.css';

class Header extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isOpen: false
        };
        this.logout = this.logout.bind(this);
        this.toggle = this.toggle.bind(this);
    }

    toggle() { this.setState({ isOpen: !this.state.isOpen }); }

    onLogin(userId) {
        this.setState({});
        this.user();
    }

    onRegister(status) { this.props.onRegister(status); }

    home() { this.props.onUpdate('home'); }

    user() { this.props.onUpdate('user'); }

    performance() { this.props.onUpdate('performance'); }

    // TODO create account page
    account() { this.props.onUpdate('user'); }

    logout() { this.props.onLogout(); }

    render() {
        return (
            <div>
                <Navbar color="faded" light expand="md">
                    <NavbarBrand href="#" onClick={this.home.bind(this)}>crypto tracker</NavbarBrand>
                    <NavbarToggler onClick={this.toggle} />
                    <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="ml-auto" navbar>
                            <NavItem className="navbar-menu">
                              {(isLoggedIn()) ? <Link to="#" onClick={this.home.bind(this)}>Market</Link>: ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem className="navbar-menu">
                              {(isLoggedIn()) ? <Link to="#" onClick={this.user.bind(this)}>Portfolio</Link> : ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem className="navbar-menu">
                              {(isLoggedIn()) ? <Link to="#" onClick={this.performance.bind(this)}>Performance</Link> : ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem className="navbar-menu">
                              {(isLoggedIn()) ? <Link to="#" onClick={this.user.bind(this)}>Account</Link> : ''}
                            </NavItem>
                            <NavItem>
                                {(!isLoggedIn()) ? <Login onLogin={this.onLogin.bind(this)} buttonLabel="Login"/> : ''}
                            </NavItem>
                            &nbsp;&nbsp;&nbsp;
                            <NavItem>
                                  {(!isLoggedIn()) ? <SignUp buttonLabel="Sign Up" onRegister={this.onRegister.bind(this)}/> : ''}
                            </NavItem>
                            <NavItem>
                                  {(isLoggedIn()) ? <Button size="lg" color="success" onClick={this.logout}>Log out</Button> : ''}
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        );
    }
}

export default Header;
