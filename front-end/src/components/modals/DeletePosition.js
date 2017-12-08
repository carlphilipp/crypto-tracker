import React from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader, Form, FormGroup, Label, InputGroup, InputGroupAddon, Input} from 'reactstrap';
import {deletePosition} from '../../utils/ApiClient';
import {FormattedNumber}  from 'react-intl'
import LoginFailure from '../LoginFailure';
import {getUserId, getAccessToken} from '../../service/AuthService';

class DeletePosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            failure: false,
            price: null,
        };
        this.toggle = this.toggle.bind(this);
        this.delete = this.delete.bind(this);
    }

    toggle() { this.setState({ modal: !this.state.modal }); }

    updatePrice(evt) {
      switch(evt.target.id) {
        case "radio1":
          this.setState({ price: this.refs.value.value });
          break;
        case "radio2":
          this.setState({ price: this.props.position.value });
          break;
        case "value":
          if(this.refs.radio1.checked === true) {
            this.setState({ price: this.refs.value.value });
          }
          break;
        default:
          console.log("Error while updating price")
      }
    }

    delete() {
        const accessToken = getAccessToken();
        const userId = getUserId();
        deletePosition(accessToken, userId, this.props.position.id, this.state.price)
            .then((token) => {
                this.toggle()
                this.props.onUpdateOrDelete(this.props.index)
            })
            .catch((error) => {
              // TODO handle failure state
              console.log("Error: " + error)
              this.setState({failure:true})
            })
    }

    render() {
        return (
            <div>
                <Button color="danger" size="lg" onClick={this.toggle}>Delete</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Delete</ModalHeader>
                    <ModalBody>
                      <Form>
                        <FormGroup>
                          <Label for="currency">Currency</Label><br />
                          {this.props.position.currency1.currencyName}
                        </FormGroup>
                        <FormGroup>
                          <Label for="currency">Quantity</Label><br />
                          {this.props.position.quantity}
                        </FormGroup>
                        <FormGroup>
                          <Label for="value">Value</Label>
                          <FormGroup check>
                              <input type="radio" name="radio1" id="radio1" defaultChecked ref="radio1" onClick={(evt) => this.updatePrice(evt)}/>{' $'}<input size="lg" type="text" ref="value" name="value" id="value" onBlur={(evt) => this.updatePrice(evt)}/>
                          </FormGroup>
                          <FormGroup check>
                              <input type="radio" name="radio1" id="radio2" ref="radio2" onClick={(evt) => this.updatePrice(evt)}/>{' '}<FormattedNumber value={this.props.position.value} style={`currency`} currency="USD"/> (Market value)
                          </FormGroup>
                        </FormGroup>
                      </Form>
                    </ModalBody>
                      {/* FIXME: create a state failure*/}
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="danger" size="lg" onClick={this.delete}>Delete</Button>
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default DeletePosition;
