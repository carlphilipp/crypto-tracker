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
import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback} from 'reactstrap';
import {loginUser} from '../../service/UserService';
import {storeToken} from '../../service/AuthService';
import {delay} from '../../utils/Utils';
import AlertFailure from '../alerts/AlertFailure';

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            emailValid: null,
            password: null,
            passwordValid: null,
            failure: false,
            refresh: false,
            formValid: false,
        };
        this.toggle = this.toggle.bind(this);
        this.loginUser = this.loginUser.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal,
            failure: false
        });
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({
          [name]: value,
          failure: false
        }, () => this.validate(name, value));
    }

    validate(name, value) {
      switch(name) {
        case "email":
          this.setState({emailValid: value != null && value !== ''}, () => this.validateForm());
          break;
        case "password":
          this.setState({passwordValid: this.state.password != null && this.state.password !== ''}, () => this.validateForm());
          break;
        default:
          break;
      }
    }

    validateForm() {
      this.setState({formValid: this.state.emailValid && this.state.passwordValid})
    }

    onLogin() { this.props.onLogin() }

    loginUser() {
        loginUser(this.state.email, this.state.password)
            .then((token) => {
                this.toggle()
                // TODO should not manipualte user token here
                storeToken(token);
                // FIXME: Should not have to use a timer
                delay(300).then(() => {this.onLogin()});
            })
            .catch((error) => {
              console.log("Error: " + error)
              this.setState({failure:true})
            })
    }

    render() {
        return (
            <div>
                <Button color="success"  onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Login</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="email">Email</Label>
                                <Input type="email" name="email" id="email" onChange={evt => this.handleUserInput(evt)} placeholder="your email" autoFocus="true"/>
                                <FormFeedback>Invalid email</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input  type="password" name="password" id="password"  onChange={evt => this.handleUserInput(evt)} placeholder="your password"/>
                                  <FormFeedback>The password can not be empty</FormFeedback>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    {(this.state.failure) ? <AlertFailure display="No login/password match" color="danger"/> : ''}
                    <ModalFooter>
                        <Button color="success" onClick={this.loginUser} disabled={!this.state.formValid}>Login</Button>{' '}
                        <Button color="secondary" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default Login;
