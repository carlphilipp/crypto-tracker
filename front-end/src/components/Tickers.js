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
                    <th className="Col1">Name</th>
                    <th className="text-right Col2">Price</th>
                    <th className="text-right Col3">Market Cap</th>
                    <th className="text-right Col4">Volume 24h</th>
                    <th className="text-right Col5">Change 1h</th>
                    <th className="text-right Col6">Change 24h</th>
                    <th className="text-right Col7">Change 7d</th>
                    <th className="text-right Col8">Last updated</th>
                </tr>
                </thead>
                <tbody>
                {tickers.map((ticker, index) => (
                    <tr key={index}>
                        <th scope="row" className="Col1">{ticker.currency1.currencyName}</th>
                        <td className="text-right Col2">
                          <FormattedNumber value={ticker.price} style={`currency`} currency="USD" minimumFractionDigits={getMinimumFractionDigits(ticker.price)}/>
                        </td>
                        <td className="text-right Col3">
                          {(ticker.marketCap === 0) ? '?' : <FormattedNumber value={ticker.marketCap} style={`currency`} currency="USD" minimumFractionDigits={0}/>}
                        </td>
                        <td className="text-right Col4">
                          <FormattedNumber value={ticker.volume24h} style={`currency`} currency="USD" minimumFractionDigits={0}/>
                        </td>
                        <td className="text-right Col5">
                          <font color={(ticker.percentChange1h > 0) ? green : red}>
                            {(ticker.percentChange1h > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange1h} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                          </font>
                        </td>
                        <td className="text-right Col6">
                          <font color={(ticker.percentChange24h > 0) ? green : red}>
                            {(ticker.percentChange24h > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange24h} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                          </font>
                        </td>
                        <td className="text-right Col7">
                          <font color={(ticker.percentChange7d > 0) ? green : red}>
                            {(ticker.percentChange7d > 0) ? '+' : ''}
                            <FormattedNumber value={ticker.percentChange7d} style={`percent`} minimumFractionDigits={2} maximumFractionDigits={2}/>
                            </font>
                        </td>
                        <td className="text-right Col8">
                          <FormattedTime value={new Date(ticker.lastUpdated * 1000)}/></td>
                    </tr>
                ))}
                </tbody>
            </Table>;
        }

        return (
            <IntlProvider locale="en">
              <div>
                  <h5 className="text-center">Market</h5>
                  {table}
              </div>
            </IntlProvider>
        );
    }
}

export default Tickers;
