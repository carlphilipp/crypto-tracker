import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {login} from '../utils/api';
import {getUserId, storeToken} from '../utils/AuthService';

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            password: null,
            success: false,
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

    updateEmail(evt) {
        this.setState({
            email: evt.target.value
        });
    }

    updatePassword(evt) {
        this.setState({
            password: evt.target.value
        });
    }

    onLogin(userId) {
      console.log("login in login.js")
        this.props.onLogin(userId)
    }

    loginUser() {
        login(this.state.email, this.state.password)
            .then((token) => {
                this.toggle()
                storeToken(token);
                let userId = getUserId();
                console.log("Access token: " + userId);
                // FIXME: Should not have to use a timer
                this.later(300, userId).then(userId => {
                    this.onLogin(userId)
                });
            })
    }

    later(delay, value) {
        return new Promise(resolve => setTimeout(resolve, delay, value));
    }

    render() {
        return (
            <div>
                <Button color="secondary" size="lg" onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Login</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="exampleEmail">Email</Label>
                                <Input type="email" name="email" id="exampleEmail" onChange={evt => this.updateEmail(evt)} placeholder="your email"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="examplePassword">Password</Label>
                                <Input type="password" name="password" onChange={evt => this.updatePassword(evt)} id="examplePassword" placeholder="your password"/>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" size="lg" onClick={this.loginUser}>Login</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default Login;
