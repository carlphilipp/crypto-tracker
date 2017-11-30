import React from 'react';
import ReactDOM from 'react-dom';
import Tickers from './components/Tickers';
import User from './components/User';
import { Router, Route, browserHistory } from 'react-router';

const Root = () => {
  return (
    <div className="container">
      <Router history={browserHistory}>
        <Route path="/" component={Tickers}/>
        <Route path="/account" component={User}/>
      </Router>
    </div>
  )
}

ReactDOM.render(<Root />, document.getElementById('root'));
