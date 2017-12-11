import React, {Component} from 'react';
import {IntlProvider}  from 'react-intl'
import {getOneUser} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Button} from 'reactstrap';

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

    getUser(accessToken, userId) {
        getOneUser(accessToken, userId).then((user) => { this.setState({user: user}); })
        .catch((error) => {
          if(error.response.status === 401 && error.response.data.error_description.includes("expired")){
            console.log("Token expired, logging out...")
            this.logout()
          } else {
            console.log("Unhandled error: " + error)
          }
        })
    }

    componentDidMount() { this.getUser(getAccessToken(), getUserId()); }

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
