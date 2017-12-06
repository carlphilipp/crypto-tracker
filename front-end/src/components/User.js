import React, {Component} from 'react';
import {Link} from 'react-router';
import {getOneUser, refreshTickers} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Table, Button} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'
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
        this.getCurrentPrice = this.getCurrentPrice.bind(this);
    }

    updateUserInState(user) { this.setState({user: user}); }

    logout() { this.props.onLogout(); }

    getUser(accessToken, userId) {
        getOneUser(accessToken, userId).then((user) => {
            this.setState({user: user});
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

    componentDidMount() { this.getUser(getAccessToken(), getUserId()); }

    refreshTickers() {
      refreshTickers()
        .then(() => this.getUser(getAccessToken(), getUserId()))
        .then(() => {
          this.setState({refreshFadeIn: true})
          delay(3000).then(() => {this.setState({refreshFadeIn: false})});
        })
    }

    showHideSecondLine(index) {
      if(this.refs[index].className === 'show') {
        this.refs[index].className = "hidden";
      } else {
        this.refs[index].className = "show";
      }
    }

    getCurrentPrice(currencyCode1, currencyCode2) {
      return this.props.tickers.find(ticker => ticker.id === currencyCode1 + '-' + currencyCode2).price;
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
                    <th className="text-right">Quantity</th>
                    <th className="text-right">Price</th>
                    <th className="text-right">Average Cost</th>
                    <th className="text-right">Original Value</th>
                    <th className="text-right">Current Value</th>
                    <th className="text-center" colSpan="2">Total Return</th>
                    <th className="text-right">Last Updated</th>
                </tr>
              </thead>
              <tbody>{
                    user.positions.map((position, index) => ([
                        <tr key={index}>
                            <th>
                              <Link to="#" onClick={() => this.showHideSecondLine(index)}>{position.currency1.currencyName}</Link>
                              <div className={'hidden'} ref={index}>
                                <br />
                                <Button size="lg" color="secondary">Modify</Button>{' '}
                                <Button size="lg" color="danger">Delete</Button>
                              </div>
                            </th>
                            <td className="text-right align-text-top">{position.quantity}</td>
                            <td className="text-right align-text-top"><FormattedNumber value={this.getCurrentPrice(position.currency1.code, position.currency2.code)} style={`currency`} currency="USD"/></td>
                            <td className="text-right align-text-top"><FormattedNumber value={position.unitCostPrice} style={`currency`} currency="USD"/></td>
                            <td className="text-right align-text-top"><FormattedNumber value={position.originalValue} style={`currency`} currency="USD"/></td>
                            <td className="text-right align-text-top"><FormattedNumber value={position.value} style={`currency`} currency="USD"/></td>
                            <td className="text-right align-text-top">
                              <FormattedNumber value={position.gain} style={`currency`} currency="USD"/>
                            </td>
                            <td className="text-right align-text-top">
                              <font color={(position.gainPercentage > 0) ? green : red}>
                                {(position.gainPercentage > 0) ? '+' : ''}
                                <FormattedNumber value={position.gainPercentage} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                              </font>
                            </td>
                            <td className="text-right align-text-top"><FormattedTime value={new Date(position.lastUpdated * 1000)}/></td>
                        </tr>
                      ])
                    )
                      }
                        {
                        <tr>
                          <th scope="row" className="align-middle">Total</th>
                          <td />
                          <td />
                          <td />
                          <td className="text-right align-middle"><FormattedNumber value={user.originalValue} style={`currency`} currency="USD"/></td>
                          <td className="text-right align-middle"><FormattedNumber value={user.value} style={`currency`} currency="USD"/></td>
                          <td className="text-right align-middle">
                            <FormattedNumber value={user.gain} style={`currency`} currency="USD"/>
                          </td>
                          <td className="text-right align-middle">
                            <font color={(user.gainPercentage > 0) ? green : red}>
                              {(user.gainPercentage > 0) ? '+' : ''}
                              <FormattedNumber value={user.gainPercentage} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                            </font>
                          </td>
                          <td></td>
                        </tr>
                      }
              </tbody>
          </Table>;
        }

        let refresh = null;
        if (this.state.refreshFadeIn) {
          refresh = <RefreshSuccess fadeIn={this.state.refreshFadeIn}/>
        }

        return (
              <IntlProvider locale="en">
                <div>
                    <h3 className="text-center">{user.email}</h3>
                    <table>
                      <tbody>
                        <tr>
                          <td><AddPosition buttonLabel="Add" user={user} updateUserInState={this.updateUserInState.bind(this)} tickers={this.props.tickers}/></td>
                          <td className="pl-1"><Button size="lg" color="info" onClick={this.refreshTickers.bind(this)}>Refresh</Button></td>
                        </tr>
                        <tr>
                          <td colSpan="2" className="pt-2">{refresh}</td>
                        </tr>
                      </tbody>
                    </table>
                    {table}
                </div>
              </IntlProvider>
        );
    }
}

export default User;
