import React from 'react';
import ReactDOM from 'react-dom';
import {browserHistory, Route, Router} from 'react-router';
import Home from './components/Home';
import 'bootstrap/dist/css/bootstrap.css';

const Root = () => {
    return (
        <div className="container">
            <Router history={browserHistory}>
                <Route path="/" component={Home}/>
            </Router>
        </div>
    )
}

ReactDOM.render(<Root/>, document.getElementById('root'));
