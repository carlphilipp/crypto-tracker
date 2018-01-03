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
import {validateGivenUser} from '../service/UserService';

class Validate extends Component {

    constructor() {
        super();
        this.state = {
          status: null
        };
        this.updateStatus = this.updateStatus.bind(this);
    }

    updateStatus(status) {
      this.setState({status: status});
    }

    componentDidMount() {
      const key = this.props.location.query.key;
      const userId = this.props.location.query.userId;
      validateGivenUser(userId, key)
        .then(() => { this.updateStatus(true); })
        .catch(err => { this.updateStatus(false); });
    }

    render() {
        return (
          <div>
            <br/>
            <br/>
            <h3 className="text-center">Account validation: {(this.state.status)? 'Success' : 'Failed'}</h3><br/>
            <div className="text-center">{(this.state.status)? <Link to="/" color="success">Log In!</Link> :  <Link to="/" color="info">Go back home</Link>}</div>
          </div>
        );
    }
}
export default Validate;
