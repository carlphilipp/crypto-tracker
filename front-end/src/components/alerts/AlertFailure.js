import React from 'react';
import { Alert } from 'reactstrap';

class AlertFailure extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          display: props.display,
          color: props.color
        };
    }

    render() {
        return (
            <Alert color={this.state.color}>
                {this.state.display}
            </Alert>
        );
    }
}

export default AlertFailure;
