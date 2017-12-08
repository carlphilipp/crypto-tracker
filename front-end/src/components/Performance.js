import React, {Component} from 'react';
import {Link} from 'react-router';
import {LineChart, XAxis, YAxis, Tooltip, CartesianGrid, Line, Legend} from 'recharts';
import {getAllShareValue, refreshTickers} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Table, Button} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'
import RefreshSuccess from './RefreshSuccess';
import AddPosition from './modals/AddPosition';
import ModifyPosition from './modals/ModifyPosition';
import DeletePosition from './modals/DeletePosition';
import {delay} from '../utils/Utils';
import SimpleLineChart from './charts/ShareValueChart'

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
        getAllShareValue(accessToken, userId).then((shareValues) => { this.setState({shareValues: shareValues}); })
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
        const {user} = this.state;
        const data = [
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B', pv: 1398, amt: 2210},
      {name: 'Page C', pv: 9800, amt: 2290},
      {name: 'Page D', pv: 3908, amt: 2000},
      {name: 'Page E', pv: 4800, amt: 2181},
      {name: 'Page F', pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},];



        return (
                <div>
                    <h3 className="text-center">Performance</h3>
                    <hr/>
                     <SimpleLineChart />
                </div>
        );
    }
}

export default User;
