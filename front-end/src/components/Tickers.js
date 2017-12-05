import React, {Component} from 'react';
import {Table} from 'reactstrap';
import {getAllTickers} from '../utils/ApiClient';
import {formatDate} from '../utils/DateUtils';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'

class Tickers extends Component {

    constructor() {
        super();
        this.state = {tickers: []};
    }

    getTickers() {
        getAllTickers().then((tickers) => {
            this.setState({tickers});
        });
    }

    componentDidMount() {
        this.getTickers();
    }

    update() {
        this.props.onUpdate('user')
    }

    render() {
        const {tickers} = this.state;
        const red = 'red'
        const green = 'green'
        return (
            <IntlProvider locale="en">
              <div>
                  <h3 className="text-center">Tickers</h3>
                  <hr/>

                  <Table hover>
                      <thead>
                      <tr>
                          <th>Name</th>
                          <th>Price</th>
                          <th>1h</th>
                          <th>24h</th>
                          <th>7d</th>
                          <th>Last updated</th>
                      </tr>
                      </thead>
                      <tbody>
                      {tickers.map((ticker, index) => (
                          <tr key={index}>
                              <th scope="row">{ticker.currencyName1}</th>
                              <td><FormattedNumber value={ticker.price} style="currency" currency="USD"/></td>
                              <td><font color={(ticker.percentChange1h > 0) ? green : red}><FormattedNumber value={ticker.percentChange1h} minimumFractionDigits="2" maximumFractionDigits="2" style="percent"/></font></td>
                              <td><font color={(ticker.percentChange24h > 0) ? green : red}><FormattedNumber value={ticker.percentChange24h} minimumFractionDigits="2" maximumFractionDigits="2" style="percent"/></font></td>
                              <td><font color={(ticker.percentChange7d > 0) ? green : red}><FormattedNumber value={ticker.percentChange7d} minimumFractionDigits="2" maximumFractionDigits="2" style="percent"/></font></td>
                              <td>{formatDate(ticker.lastUpdated)}</td>
                          </tr>
                      ))}
                      </tbody>
                  </Table>
                  {/*<Footer />*/}
              </div>
            </IntlProvider>
        );
    }
}

export default Tickers;
