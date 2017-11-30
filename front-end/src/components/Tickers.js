import React, { Component } from 'react';
import { Link } from 'react-router';
import Nav from './Nav';
import { getAllTickers } from '../utils/api';
import { isLoggedIn } from '../utils/AuthService';

class Tickers extends Component {

  constructor() {
    super()
    this.state = { tickers: [] };
  }

  getTickers() {
    getAllTickers().then((tickers) => {
      this.setState({ tickers });
    });
  }

  componentDidMount() {
    this.getTickers();
  }

  render() {

    const { tickers }  = this.state;

    return (
      <div>
        <Nav />
        <h3 className="text-center">Tickers</h3>
        <hr/>

        { tickers.map((ticker, index) => (
              <div className="col-sm-6" key={index}>
                <div className="panel panel-primary">
                  <div className="panel-heading">
                    <h3 className="panel-title"> <span className="btn">#{ ticker.id }</span></h3>
                  </div>
                  <div className="panel-body">
                    <p> { ticker.price } </p>
                    <p> { ticker.exchange } </p>
                    <p> { ticker.percentChange1h } </p>
                    <p> { ticker.percentChange24h } </p>
                    <p> { ticker.percentChange7d } </p>
                    <p> { ticker.lastUpdated } </p>
                  </div>
                </div>
              </div>
          ))}

        <div className="col-sm-12">
          { isLoggedIn() ?
            <div className="jumbotron text-center">
              <h2>View your account</h2>
              <Link className="btn btn-lg btn-success" to='/account'>Account</Link>
            </div> : <div className="jumbotron text-center"><h2>Get Access to your account by logging in</h2></div>
          }
        </div>
      </div>
    );
  }
}

export default Tickers;
