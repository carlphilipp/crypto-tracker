import React, {Component} from 'react';
import Header from './Header';
import Tickers from './Tickers';
import User from './User';
import SignUpSuccess from './SignUpSuccess'
import SignUpFailure from './SignUpFailure'

class Home extends Component {

    constructor() {
        super();
        this.state = {
            page: 'home',
            userId: null,
            registerSuccess: false,
            registerFailure: false,
        };
    }

    onUpdate(page) {
        console.log("on update " + page);
        this.setState({page: page})
    }

    onLogin(userId) {
        this.setState({userId: userId})
    }

    onLogout() {
      this.setState({userId: null})
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
                <Header onUpdate={this.onUpdate.bind(this)} onRegister={this.onRegister.bind(this)} onLogin={this.onLogin.bind(this)} onLogout={this.onLogout.bind(this)}/>
                {(this.state.registerSuccess) ? <SignUpSuccess/> : ''}
                {(this.state.registerFailure) ? <SignUpFailure/> : ''}
                {
                    (page === 'home')
                        ? <Tickers/>
                        : <User userId={this.state.userId}/>
                }
                {/* TODO add Footer*/}
            </div>
        );
    }
}

export default Home;
