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
import {addPositionToCurrentUser} from '../../service/UserService';
import AlertFailure from '../alerts/AlertFailure';

class AddPosition extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            currency: "",
            currencyValid: null,
            quantity: null,
            quantityValid: null,
            unitCostPrice: null,
            unitCostPriceValid: null,
            formValid: false,
            failure: false,
        };
        this.toggle = this.toggle.bind(this);
        this.add = this.add.bind(this);
        this.handleUserInput = this.handleUserInput.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        }, () => {
          if(this.state.modal === false) {
            this.setState({
              currency: "",
              quantity: null,
              quantityValid: null,
              unitCostPrice: null,
              unitCostPriceValid: null,
              formValid: false,
              failure: false,
            });
          }
        });
    }

    handleUserInput(evt) {
      const name = evt.target.name;
      const value = evt.target.value;
      this.setState({[name]: value}, () => this.validate(name, value));
    }

    validate(name, value) {
      switch(name) {
        case "quantity":
          this.setState({quantityValid: !isNaN(value)}, () => this.validateForm());
          break;
        case "unitCostPrice":
          this.setState({unitCostPriceValid: !isNaN(value)}, () => this.validateForm());
          break;
        case "currency":
          this.setState({currencyValid: this.state.currency !== ""}, () => this.validateForm());
          break;
        default:
          break;
      }
    }

    validateForm() {
      this.setState({formValid: this.state.quantityValid && this.state.unitCostPriceValid && this.state.currency !== ""})
    }

    add() {
        let ticker = this.props.tickers.filter(ticker => ticker.currency1.currencyName === this.state.currency)[0]
        addPositionToCurrentUser(ticker, this.state.quantity, this.state.unitCostPrice)
            .then(() => {
              this.props.onAdd()
              this.toggle()
            })
            .catch(error => {
                console.log(error);
                this.setState({failure:true})
            })
    }

    render() {
        return (
            <div>
                <Button color="primary" size="lg" onClick={this.toggle}>{this.props.buttonLabel}</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Add a position</ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup>
                                <Label for="ticker">Ticker</Label>
                                <Input size="lg" type="select" name="currency" id="currency" onChange={evt => this.handleUserInput(evt)} autoFocus="true" valid={this.state.currencyValid}>
                                <option></option>
                                {this.props.tickers
                                            .sort((a, b) => a.currency1.currencyName.toUpperCase() > b.currency1.currencyName.toUpperCase())
                                            .map(ticker => ticker.currency1)
                                            .map((currency, index) => (<option key={index} value={currency.currencyName}>{currency.currencyName} ({currency.code})</option>))}
                                </Input>
                                <FormFeedback>Must be a valid ticker</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="quantity">Quantity</Label>
                                <Input size="lg" type="text" name="quantity" id="quantity" onBlur={evt => this.handleUserInput(evt)} placeholder="1.0" valid={this.state.quantityValid}/>
                                <FormFeedback>Must be a valid number</FormFeedback>
                            </FormGroup>
                            <FormGroup>
                                <Label for="unitCostPrice">Unit Cost Price</Label>
                                <Input size="lg" type="text" name="unitCostPrice" id="unitCostPrice" onBlur={evt => this.handleUserInput(evt)} placeholder="100" valid={this.state.unitCostPriceValid}/>
                                <FormFeedback>Must be a valid number</FormFeedback>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    {(this.state.failure) ? <AlertFailure display="Sorry, something went wrong" color="danger"/> : ''}
                    <ModalFooter>
                        <Button color="primary" size="lg" onClick={this.add} disabled={!this.state.formValid}>Add</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default AddPosition;
