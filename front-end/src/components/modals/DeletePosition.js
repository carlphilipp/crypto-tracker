import React from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader, Form, FormGroup, Label, FormText, Input, FormFeedback} from 'reactstrap';
import {deletePosition} from '../../utils/ApiClient';
import {FormattedNumber}  from 'react-intl'
import LoginFailure from '../alerts/LoginFailure';
import {getUserId, getAccessToken} from '../../service/AuthService';

class DeletePosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            price: null,
            priceValid: null,
            failure: false,
        };
        this.toggle = this.toggle.bind(this);
        this.delete = this.delete.bind(this);
    }

    toggle() {
      this.setState({
        modal: !this.state.modal
      }, () => {
        if(this.state.modal === false) {
          this.setState({
            ticker: 'BTC',
            price: null,
            priceValid: null,
            failure: false,
          });
        }
      });
    }

    updatePrice(evt) {
      const value = evt.target.value;
      this.setState({ price: value }, () => {
        this.setState({priceValid: !isNaN(value)}, () => this.validateForm());
      });
    }

    validateForm() {
      this.setState({formValid: this.state.priceValid})
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
                          <Label for="value">Value</Label><br />
                          <Input size="lg" placeholder={0.0} onBlur={(evt) => this.updatePrice(evt)}  valid={this.state.priceValid}/>
                          <FormFeedback>Must be a valid number</FormFeedback>
                          <FormText>Current market value: <FormattedNumber value={this.props.position.value} style={`currency`} currency="USD"/></FormText>
                        </FormGroup>
                      </Form>
                    </ModalBody>
                      {/* FIXME: create a state failure*/}
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="danger" size="lg" onClick={this.delete} disabled={!this.state.formValid}>Delete</Button>
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default DeletePosition;
