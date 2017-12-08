import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback} from 'reactstrap';
import {createUser} from '../../utils/ApiClient';

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
      switch(name){
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
        console.log("register in signup")
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
                                <Input size="lg" type="email" name="email" onBlur={evt => this.handleUserInput(evt)} id="email" placeholder="your email" autoFocus="true" valid={this.state.emailValid}/>
                                <FormFeedback>Invalid email</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password">Password</Label>
                                <Input size="lg" type="password" name="password" onBlur={evt => this.handleUserInput(evt)} id="password" placeholder="your password" valid={this.state.passwordValid}/>
                                <FormFeedback>Password do not match</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="password2">Retype Password</Label>
                                <Input size="lg" type="password" name="password2" onBlur={evt => this.handleUserInput(evt)} id="password2" placeholder="your password" valid={this.state.passwordValid}/>
                                <FormFeedback>Password do not match</FormFeedback>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" size="lg" onClick={this.create} disabled={!this.state.formValid}>Sign Up!</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default SignUp;
