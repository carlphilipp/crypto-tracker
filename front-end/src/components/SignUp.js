import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {createUser} from '../utils/ApiClient';

class SignUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            password: null,
            password2: null,
        };
        this.toggle = this.toggle.bind(this);
        this.create = this.create.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value});
    }

    onRegister(status) {
        console.log("register in singup")
        this.props.onRegister(status)
    }

    create() {
        createUser(this.state.email, this.state.password)
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
                <Button color="primary" size="lg" onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Sign Up</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="email">Email</Label>
                                <Input type="email" name="email" onBlur={evt => this.handleUserInput(evt)} id="email" placeholder="your email" autoFocus="true"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input type="password" name="password" onBlur={evt => this.handleUserInput(evt)} id="password" placeholder="your password"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password2">Retype Password</Label>
                                <Input type="password" name="password2" onBlur={evt => this.handleUserInput(evt)} id="password2" placeholder="your password"/>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" size="lg" onClick={this.create}>Sign Up!</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default SignUp;
