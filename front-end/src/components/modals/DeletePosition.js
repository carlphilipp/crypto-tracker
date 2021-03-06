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
import {Button, Modal, ModalBody, ModalFooter, ModalHeader, Form, FormGroup, Label, FormText, Input, FormFeedback} from 'reactstrap';
import {FormattedNumber}  from 'react-intl'
import AlertFailure from '../alerts/AlertFailure';
import {deletePositionFromCurrentUser} from '../../service/UserService';

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
        deletePositionFromCurrentUser(this.props.position.id, this.state.price)
            .then((token) => {
                this.toggle()
                this.props.onUpdateOrDelete(this.props.index)
            })
            .catch((error) => {
              console.log("Error: " + error)
              this.setState({failure:true})
            })
    }

    render() {
        return (
            <div>
                <Button color="danger"  onClick={this.toggle}>Delete</Button>
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
                          <Input  placeholder={0.0} onBlur={(evt) => this.updatePrice(evt)}  valid={this.state.priceValid}/>
                          <FormFeedback>Must be a valid number</FormFeedback>
                          <FormText>Current market value: <FormattedNumber value={this.props.position.value} style={`currency`} currency="USD"/></FormText>
                          <FormText>Original value: <FormattedNumber value={this.props.position.originalValue} style={`currency`} currency="USD"/></FormText>
                        </FormGroup>
                      </Form>
                    </ModalBody>
                    {(this.state.failure) ? <AlertFailure display="Sorry, something went wrong" color="danger"/> : ''}
                    <ModalFooter>
                        <Button color="danger"  onClick={this.delete} disabled={!this.state.formValid}>Delete</Button>
                        <Button color="secondary"  onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default DeletePosition;
