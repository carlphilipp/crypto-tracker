import React, {Component} from 'react';
import Header from './Header';
import Tickers from './Tickers';
import User from './User';
import SignUpSuccess from './SignUpSuccess'
import SignUpFailure from './SignUpFailure'
import {getCurrentPage, saveCurrentPage} from '../service/PageService';
import {logout} from '../service/AuthService';
import {removePage} from '../service/PageService';

class Home extends Component {

    constructor() {
        super();
        this.state = {
            page: getCurrentPage(),
            registerSuccess: false,
            registerFailure: false,
        };
    }

    onUpdate(page) {
        console.log("on update " + page);
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
        console.log("on register " + status);
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
                        ? <Tickers/>
                        : <User onLogout={this.onLogout.bind(this)}/>
                }
                {/* TODO add Footer*/}
            </div>
        );
    }
}

export default Home;
