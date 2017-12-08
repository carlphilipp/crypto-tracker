import React, {Component} from 'react';
import {getAllShareValue, refreshTickers} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {delay} from '../utils/Utils';
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
        this.onUpdateOrDelete = this.onUpdateOrDelete.bind(this);
        this.onAdd = this.onAdd.bind(this);
    }

    updateUserInState(user) { this.setState({user: user}); }

    logout() { this.props.onLogout(); }

    getAllShareValue(accessToken, userId) {
        getAllShareValue(accessToken, userId).then((shareValues) => {

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

    componentDidMount() { this.getAllShareValue(getAccessToken(), getUserId()); }

    refreshTickers() {
      // TODO create a service to avoid accessing token and user id here
      refreshTickers()
        .then(() => this.getUser(getAccessToken(), getUserId()))
        .then(() => {
          this.setState({refreshFadeIn: true})
          delay(3000).then(() => {this.setState({refreshFadeIn: false})});
        })
    }

    onUpdateOrDelete(index) {
      this.showHideSecondLine(index);
      this.onAdd();
    }

    onAdd() {
      const accessToken = getAccessToken();
      const userId = getUserId();
      this.getUser(accessToken, userId);
    }

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
