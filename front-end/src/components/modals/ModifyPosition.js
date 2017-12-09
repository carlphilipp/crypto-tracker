import React from 'react';
import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback} from 'reactstrap';
import {updatePosition} from '../../utils/ApiClient';
import {getAccessToken} from '../../service/AuthService';
import LoginFailure from '../alerts/LoginFailure';

class ModifyPosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            quantity: null,
            quantityValid: null,
            unitCostPrice: null,
            unitCostPriceValid: null,
            failure: false,
            formValid: false,
        };
        this.toggle = this.toggle.bind(this);
        this.modifyPosition = this.modifyPosition.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        }, () => {
          if(this.state.modal === false) {
            this.setState({
              quantity: this.props.position.quantity,
              quantityValid: null,
              unitCostPrice: this.props.position.unitCostPrice,
              unitCostPriceValid: null,
              formValid: false,
            });
          }
        });
    }

    componentDidMount() {
      this.setState({
          quantity: this.props.position.quantity,
          unitCostPrice: this.props.position.unitCostPrice,
          quantityValid: true,
          unitCostPriceValid: true
      }, () => this.validateForm());
    }

    /* FIXME: code duplicated with AddPosition. Should be able to do that in a parent */
    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({
          [name]: value,
          failure: false
        }, () => this.validate(name, value))
    }

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

    onLogin() { this.props.onLogin() }

    modifyPosition() {
        updatePosition(getAccessToken(), this.props.user.id, this.props.position.id, this.props.position.currency1.code, this.state.quantity, this.state.unitCostPrice)
            .then(() => {
                this.toggle();
                this.props.onUpdateOrDelete(this.props.index);
            })
            .catch((error) => {
              console.log("Error: " + error);
              this.setState({failure:true});
            })
    }

    render() {
        return (
            <div>
                <Button color="secondary" size="lg" onClick={this.toggle}>Modify</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Modify</ModalHeader>
                    <ModalBody>
                      <FormGroup>
                          <Label for="ticker">Ticker</Label><br />
                          {this.props.position.currency1.code}
                      </FormGroup>
                      <FormGroup>
                          <Label for="quantity">Quantity</Label>
                          <Input size="lg" type="text" name="quantity" id="quantity" onBlur={evt => this.handleUserInput(evt)} defaultValue={this.state.quantity} valid={this.state.quantityValid} autoFocus="true"/>
                          <FormFeedback>Must be a valid number</FormFeedback>
                      </FormGroup>
                      <FormGroup>
                          <Label for="unitCostPrice">Unit Cost Price</Label>
                          <Input size="lg" type="text" name="unitCostPrice" id="unitCostPrice" onBlur={evt => this.handleUserInput(evt)} defaultValue={this.state.unitCostPrice} valid={this.state.unitCostPriceValid}/>
                          <FormFeedback>Must be a valid number</FormFeedback>
                      </FormGroup>
                    </ModalBody>
                    {/* FIXME: create a failure state*/}
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="success" size="lg" onClick={this.modifyPosition} disabled={!this.state.formValid}>Modify</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default ModifyPosition;
