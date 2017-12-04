import React, {Component} from 'react';
import { Button, Fade, Badge } from 'reactstrap';

class RefreshSuccess extends React.Component {

  constructor(props) {
      super(props);
  }

  render() {
      return (
        <Fade in={this.props.fadeIn} tag="h4" className="mt-3">
          <Badge color="success">Done!</Badge>
        </Fade>
      );
  }
}

export default RefreshSuccess;
