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
import {createNewUser} from '../../service/UserService';

class SignUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            emailValid: null,
            password: null,
            password2: null,
            passwordValid: null,
            formValid: false,
        };
        this.toggle = this.toggle.bind(this);
        this.create = this.create.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        }, () => {
          if(this.state.modal === false) {
            this.setState({
              email: null,
              emailValid: null,
              password: null,
              password2: null,
              passwordValid: null,
              formValid: false,
            });
          }
        });
    }

    handleUserInput(e) {
      const name = e.target.name;
      const value = e.target.value;
      this.setState({[name]: value}, () => this.validate(name, value))
    }

    validate(name, value) {
      switch(name) {
        case "email":
          this.setState({emailValid: value.includes('@')}, () => this.validateForm());
          break;
        case "password":
        case "password2":
          if(this.state.password != null && this.state.password2 != null) {
              this.setState({passwordValid: this.state.password === this.state.password2}, () => this.validateForm());
          }
          break;
        default:
          break;
      }
    }

    validateForm() {
      this.setState({formValid: this.state.emailValid && this.state.passwordValid})
    }

    onRegister(status) {
        this.props.onRegister(status)
    }

    create() {
        createNewUser(this.state.email, this.state.password)
            .then(this.toggle())
            .then(() => {
                this.onRegister(true);
            })
            .catch(error => {
                console.log(error);
                this.onRegister(false);
            })
    }

    render() {
        return (
            <div>
                <Button color="primary"  onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Sign Up</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="email">Email</Label>
                                <Input  type="email" name="email" onBlur={evt => this.handleUserInput(evt)} id="email" placeholder="your email" autoFocus="true" valid={this.state.emailValid}/>
                                <FormFeedback>Invalid email</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input  type="password" name="password" onBlur={evt => this.handleUserInput(evt)} id="password" placeholder="your password" valid={this.state.passwordValid}/>
                                <FormFeedback>Password do not match</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password2">Retype Password</Label>
                                <Input  type="password" name="password2" onBlur={evt => this.handleUserInput(evt)} id="password2" placeholder="your password" valid={this.state.passwordValid}/>
                                <FormFeedback>Password do not match</FormFeedback>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary"  onClick={this.create} disabled={!this.state.formValid}>Sign Up!</Button>{' '}
                        <Button color="secondary"  onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default SignUp;
