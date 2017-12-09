import React from 'react';
import { Alert } from 'reactstrap';

class LoginFailure extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            visible: true
        };

        this.onDismiss = this.onDismiss.bind(this);
    }

    onDismiss() {
        this.setState({ visible: false });
    }

    render() {
        return (
            <Alert color="danger" isOpen={this.state.visible}>
                No login/password match
            </Alert>
        );
    }
}

export default LoginFailure;
