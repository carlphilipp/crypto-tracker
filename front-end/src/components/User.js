import React, {Component} from 'react';
import {getOneUser, refreshTickers} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Table, Card, CardText, CardBody, CardTitle, CardSubtitle, Button, Row, Col} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider, FormattedMessage}  from 'react-intl'
import RefreshSuccess from './RefreshSuccess';
import AddPosition from './AddPosition';
import {delay} from '../utils/Utils';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          refreshFadeIn: false
        };
    }

    updateUserInState(user) {
        this.setState({user: user});
    }

    getUser(accessToken, userId) {
        getOneUser(accessToken, userId).then((user) => {
            this.setState({user: user});
        });
    }

    componentDidMount() {
        this.getUser(getAccessToken(), getUserId());
    }

    refreshTickers() {
      refreshTickers()
        .then(() => this.getUser(getAccessToken(), getUserId()))
        .then(() => {
          this.setState({refreshFadeIn: true})
          delay(3000).then(() => {this.setState({refreshFadeIn: false})});
        })
    }

    render() {
        const {user} = this.state;
        const red = 'red'
        const green = 'green'
        let table = null;
        if(user.positions != null) {
          table = <Table hover>
              <thead>
              <tr>
                  <th>Currency</th>
                  <th>Quantity</th>
                  <th>Original Value</th>
                  <th>Gain</th>
                  <th>Gain Percentage</th>
                  <th>Value</th>
                  <th>Last Updated</th>
                  <th></th>
              </tr>
              </thead>
              <tbody>{
                    user.positions.map((position, index) => (
                        <tr key={index}>
                            <th scope="row">{position.currency1}</th>
                            <td>{position.quantity}</td>
                            <td><FormattedNumber value={position.originalValue} style="currency" currency="USD"/></td>
                            <td><FormattedNumber value={position.gain} style="currency" currency="USD"/></td>
                            <td><font color={(position.gainPercentage > 0) ? green : red}><FormattedNumber value={position.gainPercentage} style="percent"/></font></td>
                            <td><FormattedNumber value={position.value} style="currency" currency="USD"/></td>
                            <td><FormattedTime value={new Date(position.lastUpdated * 1000)}/></td>
                            <td><Button size="lg" color="secondary">Update</Button></td>
                        </tr>))}
                        {
                        <tr>
                        <th scope="row">Total</th>
                        <td></td>
                        <td><FormattedNumber value={user.originalValue} style="currency" currency="USD"/></td>
                        <td><FormattedNumber value={user.gain} style="currency" currency="USD"/></td>
                        <td><font color={(user.gainPercentage > 0) ? green : red}><FormattedNumber value={user.gainPercentage} style="percent"/></font></td>
                        <td><FormattedNumber value={user.value} style="currency" currency="USD"/></td>
                        <td></td>
                        </tr>
                      }
              </tbody>
          </Table>;
        }
        return (
            <IntlProvider locale="en">
              <div>
                  <h3 className="text-center">{user.email}</h3>
                  <AddPosition buttonLabel="Add" user={user} updateUserInState={this.updateUserInState.bind(this)}/>{' '}
                  <Button size="lg" color="info" onClick={this.refreshTickers.bind(this)}>Refresh</Button>
                  <RefreshSuccess fadeIn={this.state.refreshFadeIn}/>
                  <hr/>
                  {table}
              </div>
            </IntlProvider>
        );
    }
}

export default User;
