/**
 * Copyright 2017 Carl-Philipp Harmant
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
import {getCurrentUserShareValue} from '../service/UserService';
import SimpleLineChart from './charts/ShareValueChart'
import {Table, Button} from 'reactstrap';
import {FormattedNumber, IntlProvider}  from 'react-intl'

var dateFormat = require('dateformat');

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          shareValues: [],
          refreshFadeIn: false,
          showAll: false
        };
    }

    updateUserInState(user) { this.setState({user: user}); }

    logout() { this.props.onLogout(); }

    getAllShareValue(accessToken, userId) {
        getCurrentUserShareValue().then((result) => {
          const shareValues = result.map(shareValue => {return {
            date: dateFormat(new Date(shareValue.timestamp), "mm-dd-yyyy"),
            shareValue: shareValue.shareValue,
            shareQuantity: shareValue.shareQuantity,
            portfolioValue: shareValue.portfolioValue,
          }})
          this.setState({shareValues: shareValues});
        })
    }

    showAll() {
      this.setState({showAll: !this.state.showAll});
    }

    componentDidMount() { this.getAllShareValue(); }

    render() {
        const shareValues = this.state.shareValues
        return (
              <IntlProvider locale="en">
                <div>
                    <h3 className="text-center">Share Value</h3>
                    <hr/>
                     <SimpleLineChart shareValues={this.state.shareValues}/>
                    <hr />
                    <Table hover>
                        <thead>
                          <tr>
                              <th>Date</th>
                              <th className="text-right">Share Value</th>
                              <th className="text-right">Share Quantity</th>
                              <th className="text-right">Portfolio Value</th>
                          </tr>
                        </thead>
                        <tbody>
                        {
                          // FIXME: do not shallow copy the array (perf)
                          shareValues.slice(0).reverse().map((shareValue, index) =>
                            <tr key={index} className={(index < 9 || this.state.showAll) ? "" : "hidden"}>
                              <th>
                                {shareValue.date} ({index})
                              </th>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.shareValue} maximumFractionDigits={2} style={`decimal`}/></td>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.shareQuantity} maximumFractionDigits={2} style={`decimal`}/></td>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.portfolioValue} minimumFractionDigits={2} style={`currency`} currency="USD"/></td>
                            </tr>
                          )
                        }
                        </tbody>
                    </Table>
                    <div className="text-center">
                      <Button color="primary" size="lg" onClick={this.showAll.bind(this)}>Show / Hide All</Button>
                    </div>
                </div>
              </IntlProvider>
        );
    }
}

export default User;
