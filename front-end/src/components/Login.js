import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {login} from '../utils/ApiClient';
import {storeToken} from '../service/AuthService';
import {delay} from '../utils/Utils';
import LoginFailure from './LoginFailure';

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            password: null,
            failure: false,
            refresh: false,
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
        });
    }

    onLogin() {
        this.props.onLogin()
    }

    loginUser() {
        login(this.state.email, this.state.password)
            .then((token) => {
                this.toggle()
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
                                <Label for="exampleEmail">Email</Label>
                                <Input size="lg" type="email" name="email" id="exampleEmail" onChange={evt => this.handleUserInput(evt)} placeholder="your email" autoFocus="true"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="examplePassword">Password</Label>
                                <Input size="lg" type="password" name="password" onChange={evt => this.handleUserInput(evt)} id="examplePassword" placeholder="your password"/>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="success" size="lg" onClick={this.loginUser}>Login</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default Login;
