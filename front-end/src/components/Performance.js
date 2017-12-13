import React, {Component} from 'react';
import {getCurrentUserShareValue} from '../service/UserService';
import SimpleLineChart from './charts/ShareValueChart'
import {Table} from 'reactstrap';
import {FormattedNumber, IntlProvider}  from 'react-intl'

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
                            <tr key={index}>
                              <th>
                                {shareValue.date}
                              </th>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.shareValue} maximumFractionDigits={2} style={`decimal`}/></td>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.shareQuantity} maximumFractionDigits={2} style={`decimal`}/></td>
                              <td className="text-right align-text-top"><FormattedNumber value={shareValue.portfolioValue} minimumFractionDigits={2} style={`currency`} currency="USD"/></td>
                            </tr>
                          )
                        }
                        </tbody>
                    </Table>
                </div>
              </IntlProvider>
        );
    }
}

export default User;
