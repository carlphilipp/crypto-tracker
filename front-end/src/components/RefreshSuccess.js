import React, {Component} from 'react';
import { Alert } from 'reactstrap';

class RefreshSuccess extends Component {
  constructor(props) {
    super(props);
    this.state = {visible: true};
  }

  onDismiss() {
    this.setState({ visible: false });
  }

  render() {
      return (
        <Alert color="success" isOpen={this.state.visible}>
          Done!
        </Alert>
      );
  }
}

export default RefreshSuccess;
