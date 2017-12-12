import React from 'react';
import { Alert } from 'reactstrap';

class CouldNotLoad extends React.Component {

    constructor(props) {
        super(props);
        this.state = { visible: true };
    }

    onDismiss() {
        this.setState({ visible: false });
    }

    render() {
        return (
            <Alert color="danger" isOpen={this.state.visible}>
                Could not load any data, please try again later
            </Alert>
        );
    }
}

export default CouldNotLoad;
