/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    account() { this.props.onUpdate('account'); }

    logout() { this.props.onLogout(); }

    render() {
        return (
            <div>
                <Navbar color="faded" light expand="md">
                    <NavbarBrand href="#" onClick={this.home.bind(this)}>crypto tracker</NavbarBrand>
                    <NavbarToggler onClick={this.toggle} />
                    <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="ml-auto" navbar>
                            {(isLoggedIn()) ?
                              <NavItem className="navbar-menu">
                                <Link to="#" activeStyle={{color:'#53acff'}} onClick={this.home.bind(this)}>Market</Link>
                              </NavItem>
                            : ''}
                            {(isLoggedIn()) ?
                              <NavItem className="navbar-menu">
                                 <Link to="#" activeStyle={{color:'#53acff'}} onClick={this.user.bind(this)}>Portfolio</Link>
                              </NavItem>
                            : ''}
                            {(isLoggedIn()) ?
                              <NavItem className="navbar-menu">
                                 <Link to="#" activeStyle={{color:'#53acff'}} onClick={this.performance.bind(this)}>Performance</Link>
                              </NavItem>
                            : ''}
                            {(isLoggedIn()) ?
                              <NavItem className="navbar-menu">
                                 <Link to="#" activeStyle={{color:'#53acff'}} onClick={this.account.bind(this)}>Account</Link>
                              </NavItem>
                            : ''}
                            {(!isLoggedIn()) ?
                              <NavItem>
                                <Login onLogin={this.onLogin.bind(this)} buttonLabel="Login"/>
                                {/*<NavLink href="#" active>Link1</NavLink>*/}
                              </NavItem>
                             : ''}

                            {(!isLoggedIn()) ?
                              <NavItem>
                                  <SignUp buttonLabel="Sign Up" onRegister={this.onRegister.bind(this)}/>
                                  {/*<NavLink href="#" active>Link2</NavLink>*/}
                              </NavItem>
                            : ''}
                            {(isLoggedIn()) ?
                              <NavItem>
                                     <Button color="success" onClick={this.logout}>Log out</Button>
                                     {/*<NavLink href="#" active>Link3</NavLink>*/}
                              </NavItem>
                            : ''}
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        );
    }
}

export default Header;
