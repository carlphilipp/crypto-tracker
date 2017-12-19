import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback} from 'reactstrap';
import {addPositionToCurrentUser} from '../../service/UserService';
import AlertFailure from '../alerts/AlertFailure';

class AddPosition extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            currency: 'Bitcoin',
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
              currency: 'Bitcoin',
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
      console.log("Name: " + name + " value: " + value)
      if(name === "currency") {
        console.log(value.currencyName)
      }
      this.setState({[name]: value}, () => this.validate(name, value));
    }

    // TODO Check how the API react between 423.0 vs 1,000.0
    validate(name, value) {
      switch(name) {
        case "quantity":
          this.setState({quantityValid: !isNaN(value)}, () => this.validateForm());
          break;
        case "unitCostPrice":
          this.setState({unitCostPriceValid: !isNaN(value)}, () => this.validateForm());
          break;
        default:
          break;
      }
    }

    validateForm() {
      this.setState({formValid: this.state.quantityValid && this.state.unitCostPriceValid})
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
                                <Input size="lg" type="select" name="currency" id="currency" onChange={evt => this.handleUserInput(evt)} autoFocus="true">
                                {this.props.tickers.map(ticker => ticker.currency1).map((currency, index) => (<option key={index}>{currency.currencyName}</option>))}
                                </Input>
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
