import React from 'react';
import ReactDOM from 'react-dom';
import {browserHistory, Route, Router} from 'react-router';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.css';
import '../src/index.css';

const NotFound = () => (<h1>404.. This page is not found!</h1>);

const Root = () => {
    return (
        <div className="container">
            <Router history={browserHistory}>
                <Route path="/" component={Home}/>
                <Route path="*" component={NotFound}/>
            </Router>
        </div>
    )
};

ReactDOM.render(<Root/>, document.getElementById('root'));
