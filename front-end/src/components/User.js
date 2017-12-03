import React, {Component} from 'react';
import {getOneUser} from '../utils/api';
import {getUserId} from '../utils/AuthService';
import {Table} from 'reactstrap';
import {FormattedNumber}  from 'react-intl'
import {IntlProvider} from 'react-intl';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {user: []};
    }

    getUser(userId) {
        getOneUser(userId).then((user) => {
            this.setState({user});
        });
    }

    componentDidMount() {
        this.getUser(getUserId());
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
                        </tr>))
              }</tbody>
          </Table>;
        }
        return (
            <IntlProvider locale="en">
              <div>
                  <h3 className="text-center">{user.id} {user.email}</h3>
                  <hr/>
                  {table}
              </div>
            </IntlProvider>
        );
    }
}

export default User;
