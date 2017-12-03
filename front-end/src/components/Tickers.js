import React, {Component} from 'react';
import {Link} from 'react-router';
import Nav from './Nav';
import {Table} from 'reactstrap';
import {getAllTickers} from '../utils/api';
import {isLoggedIn} from '../utils/AuthService';

class Tickers extends Component {

    constructor() {
        super()
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

    update () {
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
                        <th>#</th>
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
                            <th scope="row">{ticker.id}</th>
                            <td>{ticker.price}</td>
                            <td>{ticker.percentChange1h}%</td>
                            <td>{ticker.percentChange24h}%</td>
                            <td>{ticker.percentChange7d}%</td>
                            <td>{ticker.lastUpdated}</td>
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
