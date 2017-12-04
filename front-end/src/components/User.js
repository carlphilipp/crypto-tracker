import React, {Component} from 'react';
import {getOneUser, refreshTickers} from '../utils/api';
import {getUserId, getAccessToken} from '../utils/AuthService';
import {Table, Button} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {user: []};
    }

    getUser(accessToken, userId) {
        getOneUser(accessToken, userId).then((user) => {
            this.setState({user});
        });
    }

    componentDidMount() {
        this.getUser(getAccessToken(), getUserId());
    }

    refreshTickers() {
      refreshTickers()
        .then(() => this.getUser(getAccessToken(), getUserId()))
    }

    render() {
        const {user} = this.state;
        let table = null;
        if(user.positions != null){
          table = <Table hover>
              <thead>
              <tr>
                  <th>Currency</th>
                  <th>Quantity</th>
                  <th>Value</th>
                  <th>Original Value</th>
                  <th>Gain</th>
                  <th>Gain Percentage</th>
                  <th>Last Updated</th>
              </tr>
              </thead>
              <tbody>{
                    user.positions.map((position, index) => (
                        <tr key={index}>
                            <th scope="row">{position.currency1}</th>
                            <td>{position.quantity}</td>
                            <td><FormattedNumber value={position.value} style="currency" currency="USD"/></td>
                            <td><FormattedNumber value={position.originalValue} style="currency" currency="USD"/></td>
                            <td><FormattedNumber value={position.gain} style="currency" currency="USD"/></td>
                            <td><FormattedNumber value={position.gainPercentage} style="percent"/></td>
                            <td><FormattedTime value={new Date(position.lastUpdated * 1000)}/></td>
                        </tr>))
              }</tbody>
          </Table>;
        }
        return (
            <IntlProvider locale="en">
              <div>
                  <h3 className="text-center">{user.email}</h3>
                  <Button size="lg" className="success" onClick={this.refreshTickers.bind(this)}>Refresh</Button>
                  <hr/>
                  {table}
              </div>
            </IntlProvider>
        );
    }
}

export default User;
