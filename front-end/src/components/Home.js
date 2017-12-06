import React, {Component} from 'react';
import Header from './Header';
import Tickers from './Tickers';
import User from './User';
import SignUpSuccess from './SignUpSuccess'
import SignUpFailure from './SignUpFailure'
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
            registerSuccess: false,
            registerFailure: false,
        };
    }

    componentDidMount() {
      getAllTickers().then((tickers) => {
          this.setState({tickers: tickers});
      });
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

    render() {
        const {page} = this.state;
        return (
            <div>
                <Header onUpdate={this.onUpdate.bind(this)} onRegister={this.onRegister.bind(this)} onLogout={this.onLogout.bind(this)}/>
                {(this.state.registerSuccess) ? <SignUpSuccess/> : ''}
                {(this.state.registerFailure) ? <SignUpFailure/> : ''}
                {
                    (page === 'home')
                        ? <Tickers tickers={this.state.tickers}/>
                        : <User onLogout={this.onLogout.bind(this)} tickers={this.state.tickers}/>
                }
                {/* TODO add Footer*/}
            </div>
        );
    }
}

export default Home;
