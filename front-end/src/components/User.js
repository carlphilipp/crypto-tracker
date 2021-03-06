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
import {getCurrentUser} from '../service/UserService';
import {refreshCurrentTickers} from '../service/TickerService';
import {Table, Button, Nav, NavItem} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'
import RefreshSuccess from './alerts/RefreshSuccess';
import AddPosition from './modals/AddPosition';
import ModifyPosition from './modals/ModifyPosition';
import DeletePosition from './modals/DeletePosition';
import {delay, getMinimumFractionDigits} from '../utils/Utils';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          refreshFadeIn: false
        };
        this.getCurrentPrice = this.getCurrentPrice.bind(this);
        this.onUpdateOrDelete = this.onUpdateOrDelete.bind(this);
        this.onAdd = this.onAdd.bind(this);
        this.logout = this.logout.bind(this);
    }

    logout() { this.props.onLogout(); }

    getCurrentUser() {
        getCurrentUser(this.logout).then((user) => { this.setState({user: user}); });
    }

    componentDidMount() { this.getCurrentUser(); }

    refreshTickers() {
      refreshCurrentTickers()
        .then(() => this.getCurrentUser())
        .then(() => {
          this.setState({refreshFadeIn: true})
          delay(3000).then(() => {this.setState({refreshFadeIn: false})});
        })
    }

    showHideSecondLine(index) {
      if(this.refs[index].className === 'show') {
        this.refs[index].className = 'hidden';
      } else {
        this.refs[index].className = 'show';
      }
    }

    getCurrentPrice(currencyCode1, currencyCode2) {
      const ticker = this.props.tickers.find(ticker => ticker.id === currencyCode1 + '-' + currencyCode2);
      return ticker === undefined ? 0 : ticker.price;
    }

    onUpdateOrDelete(index) {
      this.showHideSecondLine(index);
      this.onAdd();
    }

    onAdd() {
      this.getCurrentUser();
    }

    render() {
        const {user} = this.state;
        const red = 'red'
        const green = 'green'
        let table = null;
        if(user != null && user.positions != null) {
          table = <Table hover>
              <thead>
                <tr>
                    <th>Currency</th>
                    <th className="text-right">Quantity</th>
                    <th className="text-right">Price</th>
                    <th className="text-right">Average Cost</th>
                    <th className="text-right">Original Value</th>
                    <th className="text-right">Market Value</th>
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
                                <div className="container-fluid">
                                	<div className="row">
                                      <ModifyPosition user={user} index={index} position={position} onUpdateOrDelete={this.onUpdateOrDelete}/>
                                		<div className="col-md-1">
                                        <DeletePosition position={position} index={index} onUpdateOrDelete={this.onUpdateOrDelete}/>
                                    </div>
                                	</div>
                                </div>
                              </div>
                            </th>
                            <td className="text-right align-text-top">{position.quantity}</td>
                            <td className="text-right align-text-top"><FormattedNumber value={this.getCurrentPrice(position.currency1.code, position.currency2.code)} minimumFractionDigits={getMinimumFractionDigits(this.getCurrentPrice(position.currency1.code, position.currency2.code))} style={`currency`} currency="USD"/></td>
                            <td className="text-right align-text-top"><FormattedNumber value={position.unitCostPrice} minimumFractionDigits={getMinimumFractionDigits(position.unitCostPrice)} style={`currency`} currency="USD"/></td>
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
                    <h5 className="text-center">Portfolio</h5>
                    <Nav pills>
                      <NavItem><AddPosition buttonLabel="Add" user={user} onAdd={this.onAdd} tickers={this.props.tickers}/></NavItem>
                      <NavItem><Button color="info" onClick={this.refreshTickers.bind(this)}>Refresh</Button></NavItem>
                      <NavItem>{refresh}</NavItem>
                    </Nav>
                    <br />
                    {table}
                </div>
              </IntlProvider>
        );
    }
}

export default User;
