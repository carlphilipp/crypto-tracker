import React, {Component} from 'react';
import {getOneUser, refreshTickers} from '../utils/ApiClient';
import {getUserId, getAccessToken} from '../service/AuthService';
import {Table, Card, CardText, CardBody, CardTitle, CardSubtitle, Button, Row, Col} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'
import RefreshSuccess from './RefreshSuccess';
import {delay} from '../utils/Utils';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {
          user: [],
          refreshFadeIn: false
        };
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
                  <Button size="lg" color="info" onClick={this.refreshTickers.bind(this)}>Refresh</Button>
                  <RefreshSuccess fadeIn={this.state.refreshFadeIn}/>
                  <hr/>
                  <div>
                    <Row>
                      <Col sm="6">
                        <Card>
                          <CardBody>
                            <CardTitle>Portfolio</CardTitle>
                            <CardSubtitle></CardSubtitle>
                            <CardText>
                            Total value: <FormattedNumber value={user.value} style="currency" currency="USD"/><br/>
                            Performance: <FormattedNumber value={user.gain} style="currency" currency="USD"/> (<FormattedNumber value={user.gainPercentage} style="percent"/>)
                            </CardText>
                          </CardBody>
                        </Card>
                      </Col>
                    </Row>
                  </div>
                  <hr/>
                  {table}
              </div>
            </IntlProvider>
        );
    }
}

export default User;
