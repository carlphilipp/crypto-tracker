import React, { Component } from 'react';
import { Link } from 'react-router';
import Nav from './Nav';
import { getOneUser } from '../utils/api';

class User extends Component {

  constructor() {
    super()
    this.state = { user: [] };
  }

  getUser() {
    getOneUser().then((user) => {
      this.setState({ user });
    });
  }

  componentDidMount() {
    this.getUser();
  }

  render() {

    const { user }  = this.state;
        return (
          <div>
            <Nav />
            <h3 className="text-center">Account</h3>
            <hr/>
            <div className="col-sm-6">
              <div className="panel panel-primary">
                <div className="panel-heading">
                  <h3 className="panel-title"> <span className="btn">#{ user.id }</span></h3>
                </div>
                <div className="panel-body">
                  <p> { user.email } </p>
                </div>
              </div>
            </div>
          </div>
        );
    }
}

export default User;
