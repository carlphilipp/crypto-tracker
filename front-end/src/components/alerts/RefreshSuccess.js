/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import React, {Component} from 'react';
import { Badge } from 'reactstrap';

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
        <h5><Badge color="success" isOpen={this.state.visible}>Done!</Badge></h5>
      );
  }
}

export default RefreshSuccess;
