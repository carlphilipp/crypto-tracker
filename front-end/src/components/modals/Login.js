import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback} from 'reactstrap';
import {loginUser} from '../../service/UserService';
import {storeToken} from '../../service/AuthService';
import {delay} from '../../utils/Utils';
import LoginFailure from '../alerts/LoginFailure';

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
            modal: !this.state.modal
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
                <Button color="success" size="lg" onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Login</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="email">Email</Label>
                                <Input size="lg" type="email" name="email" id="email" onChange={evt => this.handleUserInput(evt)} placeholder="your email" autoFocus="true"/>
                                <FormFeedback>Invalid email</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input size="lg" type="password" name="password" id="password"  onChange={evt => this.handleUserInput(evt)} placeholder="your password"/>
                                  <FormFeedback>The password can not be empty</FormFeedback>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="success" size="lg" onClick={this.loginUser} disabled={!this.state.formValid}>Login</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default Login;
