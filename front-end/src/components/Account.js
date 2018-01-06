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
import {IntlProvider}  from 'react-intl'
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
                    <h5 className="text-center">Account</h5>
                    <hr />
                    <div className="text-center">
                      Email: {user.email} <br />
                      Currency: {(user.currency != null)? <label>{user.currency.code}</label> : '' }<br />
                      <Button>Update password</Button>
                    </div>
                </div>
              </IntlProvider>
        );
    }
}

export default Account;
