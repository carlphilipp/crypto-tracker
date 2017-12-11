import React, {Component} from 'react';
import {IntlProvider}  from 'react-intl'
import {getOneUser} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Button} from 'reactstrap';
import {getCurrentUser} from '../service/UserService';

class Account extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          refreshFadeIn: false
        };
    }

    updateUserInState(user) { this.setState({user: user}); }

    logout() { this.props.onLogout(); }

    getUser() {
        getCurrentUser().then((user) => { this.setState({user: user}); })
    }

    componentDidMount() { this.getUser(); }

    render() {
        const {user} = this.state;

        // TODO add update password
        return (
              <IntlProvider locale="en">
                <div>
                    <h3 className="text-center">Account</h3>
                    <hr />
                    <div className="text-center">
                      Email: {user.email} <br />
                      Currency: {(user.currency != null)? <label>{user.currency.code}</label> : '' }<br />
                      <Button size="lg">Update password</Button>
                    </div>
                </div>
              </IntlProvider>
        );
    }
}

export default Account;
