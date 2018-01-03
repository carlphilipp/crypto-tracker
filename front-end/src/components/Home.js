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
import Header from './Header';
import Tickers from './Tickers';
import User from './User';
import Performance from './Performance';
import Account from './Account';
import Footer from './Footer';
import CouldNotLoad from './alerts/CouldNotLoad'
import SignUpSuccess from './alerts/SignUpSuccess'
import SignUpFailure from './alerts/SignUpFailure'
import {getCurrentPage, saveCurrentPage} from '../service/PageService';
import {logout} from '../service/AuthService';
import {removePage} from '../service/PageService';
import {getCurrentTickers} from '../service/TickerService';

class Home extends Component {

    constructor() {
        super();
        this.state = {
            tickers: [],
            page: getCurrentPage(),
            loadFailure: false,
            registerSuccess: false,
            registerFailure: false,
        };
    }

    updateTickers() {
      getCurrentTickers().then((tickers) => {
          this.setState({tickers: tickers});
      })
      .catch((error) => {
        this.setState({loadFailure: true})
        console.error(error)
      })
    }

    componentDidMount() { this.updateTickers(); }

    onUpdate(page) {
        this.setState({page: page})
        saveCurrentPage(page)
        if (page === 'home') {
          this.updateTickers();
        }
    }

    onLogout() {
      this.onUpdate('home')
      logout();
      removePage();
    }

    onRegister(status) {
        this.setState({
            registerSuccess: status,
            registerFailure: !status
        })
    }

    loadPage() {
      switch(this.state.page) {
        case 'user':
          return <User onLogout={this.onLogout.bind(this)} tickers={this.state.tickers}/>;
        case 'performance':
          return <Performance onLogout={this.onLogout.bind(this)} tickers={this.state.tickers}/>;
        case 'account':
            return <Account onLogout={this.onLogout.bind(this)}/>;
        default:
          return <Tickers tickers={this.state.tickers}/>;
        }
    }

    render() {
        return (
            <div>
                <Header onUpdate={this.onUpdate.bind(this)} onRegister={this.onRegister.bind(this)} onLogout={this.onLogout.bind(this)}/>
                {(this.state.registerSuccess) ? <SignUpSuccess/> : ''}
                {(this.state.registerFailure) ? <SignUpFailure/> : ''}
                {(this.state.loadFailure) ? <CouldNotLoad/> : ''}
                {
                  this.loadPage()
                }
                <Footer/>
            </div>
        );
    }
}

export default Home;
