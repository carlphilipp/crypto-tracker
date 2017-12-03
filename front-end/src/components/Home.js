import React, {Component} from 'react';
import {Link} from 'react-router';
import Nav from './Nav';
import Tickers from './Tickers';
import User from './User';
import {Table} from 'reactstrap';
import {getAllTickers} from '../utils/api';
import {isLoggedIn} from '../utils/AuthService';

class Home extends Component {

    constructor() {
        super()
        this.state = {
          page: 'home'
        };
    }

    onUpdate (page) {
      console.log("on update " + page)
      this.setState({ page: page })
    }

    render() {
        const {page} = this.state;
        return (
            <div>
                <Nav onUpdate={this.onUpdate.bind(this)}/>
                {
                  (page === 'home')
                  ? <Tickers/>
                  : <User/>
                }
                {/* TODO add Footer*/}
            </div>
        );
    }
}

export default Home;
