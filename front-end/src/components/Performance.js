import React, {Component} from 'react';
import {getCurrentUserShareValue} from '../service/UserService';
import SimpleLineChart from './charts/ShareValueChart'
var dateFormat = require('dateformat');

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          shareValues: [],
          refreshFadeIn: false
        };
    }

    updateUserInState(user) { this.setState({user: user}); }

    logout() { this.props.onLogout(); }

    getAllShareValue(accessToken, userId) {
        getCurrentUserShareValue().then((shareValues) => {

          const rechartsData = shareValues.map(shareValue => {return {
            date: dateFormat(new Date(shareValue.timestamp), "mm-dd-yyyy"),
            shareValue: shareValue.shareValue,
            portfolioValue: shareValue.portfolioValue,
          }})
          this.setState({shareValues: rechartsData});
        })

        .catch((error) => {
          if(error.response.status === 401 && error.response.data.error_description.includes("expired")){
            console.log("Token expired, logging out...")
            this.logout()
          } else {
            console.log("Unhandled error: " + error)
          }
        })
    }

    componentDidMount() { this.getAllShareValue(); }

    render() {
        return (
                <div>
                    <h3 className="text-center">Share Value</h3>
                    <hr/>
                     <SimpleLineChart shareValues={this.state.shareValues}/>
                </div>
        );
    }
}

export default User;
