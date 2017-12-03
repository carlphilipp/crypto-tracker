import React, {Component} from 'react';
import Nav from './Nav';
import Tickers from './Tickers';
import User from './User';
import SignUpSuccess from './SignUpSuccess'
import SignUpFailure from './SignUpFailure'

class Home extends Component {

    constructor() {
        super();
        this.state = {
            page: 'home',
            registerSuccess: false,
            registerFailure: false
        };
    }

    onUpdate(page) {
        console.log("on update " + page);
        this.setState({page: page})
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
                <Nav onUpdate={this.onUpdate.bind(this)} onRegister={this.onRegister.bind(this)}/>
                {(this.state.registerSuccess) ? <SignUpSuccess/> : ''}
                {(this.state.registerFailure) ? <SignUpFailure/> : ''}
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
