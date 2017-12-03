import React, {Component} from 'react';
import {Table} from 'reactstrap';
import {getAllTickers} from '../utils/api';
import {formatDate} from '../utils/DateUtils';

class Tickers extends Component {

    constructor() {
        super();
        this.state = {tickers: []};
        //this.format = this.formatDate.bind(this)
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

        return (
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
                        <th>updated</th>
                    </tr>
                    </thead>
                    <tbody>
                    {tickers.map((ticker, index) => (
                        <tr key={index}>
                            <th scope="row">{ticker.currencyName1}</th>
                            <td>{ticker.price}</td>
                            <td>{ticker.percentChange1h}%</td>
                            <td>{ticker.percentChange24h}%</td>
                            <td>{ticker.percentChange7d}%</td>
                            <td>{formatDate(ticker.lastUpdated)}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
                {/*<Footer />*/}
            </div>
        );
    }
}

export default Tickers;
