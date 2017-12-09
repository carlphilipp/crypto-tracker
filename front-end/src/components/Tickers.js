import React, {Component} from 'react';
import {Table} from 'reactstrap';
import {FormattedNumber, FormattedTime, IntlProvider}  from 'react-intl'
import {getMinimumFractionDigits} from '../utils/Utils';

class Tickers extends Component {

    update() {
        this.props.onUpdate('user')
    }

    render() {
        const tickers = this.props.tickers;
        const red = 'red'
        const green = 'green'
        let table = null;
        if(tickers.length > 0) {
          table =
          <Table hover>
                <thead>
                <tr>
                    <th>Name</th>
                    <th className="text-right">Price</th>
                    <th className="text-right">Market Cap</th>
                    <th className="text-right">Volume 24h</th>
                    <th className="text-right">Change 1h</th>
                    <th className="text-right">Change 24h</th>
                    <th className="text-right">Change 7d</th>
                    <th className="text-right">Last updated</th>
                </tr>
                </thead>
                <tbody>
                {tickers.map((ticker, index) => (
                    <tr key={index}>
                        <th scope="row">{ticker.currency1.currencyName}</th>
                        <td className="text-right">
                          <FormattedNumber value={ticker.price} style={`currency`} currency="USD" minimumFractionDigits={getMinimumFractionDigits(ticker.price)}/>
                        </td>
                        <td className="text-right">
                          <FormattedNumber value={ticker.marketCap} style={`currency`} currency="USD" minimumFractionDigits={0}/>
                        </td>
                        <td className="text-right">
                          <FormattedNumber value={ticker.volume24h} style={`currency`} currency="USD" minimumFractionDigits={0}/>
                        </td>
                        <td className="text-right">
                          <font color={(ticker.percentChange1h > 0) ? green : red}>
                            {(ticker.percentChange1h > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange1h} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                          </font>
                        </td>
                        <td className="text-right">
                          <font color={(ticker.percentChange24h > 0) ? green : red}>
                            {(ticker.percentChange24h > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange24h} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                          </font>
                        </td>
                        <td className="text-right">
                          <font color={(ticker.percentChange7d > 0) ? green : red}>
                            {(ticker.percentChange7d > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange7d} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                            </font>
                        </td>
                        <td className="text-right">
                          <FormattedTime value={new Date(ticker.lastUpdated * 1000)}/></td>
                    </tr>
                ))}
                </tbody>
            </Table>;
        }

        return (
            <IntlProvider locale="en">
              <div>
                  <h3 className="text-center">Market</h3>
                  <hr/>
                  {table}
                  {/*<Footer />*/}
              </div>
            </IntlProvider>
        );
    }
}

export default Tickers;
