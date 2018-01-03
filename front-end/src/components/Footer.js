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

class Footer extends Component {
  render() {
    return (
      <div>
        <br/>
        <hr/>
        <h6 className="text-center">crypto tracker © <a href="http://www.apache.org/licenses/LICENSE-2.0">Copyright</a> 2018</h6>
      </div>
    );
  }
}

export default Footer;
