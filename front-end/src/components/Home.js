import React, {Component} from 'react';
import Header from './Header';
import Tickers from './Tickers';
import User from './User';
import Performance from './Performance';
import Account from './Account';
import CouldNotLoad from './alerts/CouldNotLoad'
import SignUpSuccess from './alerts/SignUpSuccess'
import SignUpFailure from './alerts/SignUpFailure'
import {getCurrentPage, saveCurrentPage} from '../service/PageService';
import {logout} from '../service/AuthService';
import {removePage} from '../service/PageService';
import {getAllTickers} from '../utils/ApiClient';

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

    componentDidMount() {
      getAllTickers().then((tickers) => {
          this.setState({tickers: tickers});
      })
      .catch((error) => {
        this.setState({loadFailure: true})
        console.error(error)
      })
    }

    onUpdate(page) {
        this.setState({page: page})
        saveCurrentPage(page)
    }

    onLogout() {
      this.onUpdate('home')
      logout();
      removePage();
      this.setState({})
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
                {/* TODO add Footer*/}
            </div>
        );
    }
}

export default Home;
