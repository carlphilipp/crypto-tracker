import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {createUser} from '../utils/api';
import SignUpSuccess from './SignUpSuccess'

class SignUp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null,
            password: null,
        };
        this.toggle = this.toggle.bind(this);
        this.create = this.create.bind(this);
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
                <Button color="primary" onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Login</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="exampleEmail">Email</Label>
                                <Input type="email" name="email" onChange={evt => this.updateEmail(evt)} id="exampleEmail" placeholder="your email"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="examplePassword">Password</Label>
                                <Input type="password" name="password" onChange={evt => this.updatePassword(evt)} id="examplePassword" placeholder="your password"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="examplePassword">Password</Label>
                                <Input type="password" name="password" id="examplePassword" placeholder="your password"/>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={this.create}>Sign Up!</Button>{' '}
                        <Button color="secondary" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default SignUp;
