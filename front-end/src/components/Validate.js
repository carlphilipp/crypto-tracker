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
