import React, {Component} from 'react';
import { Fade, Badge } from 'reactstrap';

class RefreshSuccess extends Component {
  render() {
      return (
        <Fade in={this.props.fadeIn} tag="h4" className="mt-3">
          <Badge color="success">Done!</Badge>
        </Fade>
      );
  }
}

export default RefreshSuccess;
