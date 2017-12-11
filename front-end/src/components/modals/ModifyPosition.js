import React from 'react';
import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback, Collapse, Table} from 'reactstrap';
import {updateOnePosition} from '../../service/UserService';
import LoginFailure from '../alerts/LoginFailure';

class ModifyPosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            smart: false,
            manual: false,
            quantity: null,
            quantityValid: null,
            unitCostPrice: null,
            unitCostPriceValid: null,
            failure: false,
            formValid: false,


            smartAddQuantity: 0,
            smartAddUnitCostPrice: 0,


            smartNewQuantity: 0,
            smartNewUnitCostPrice: 0,
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
      // FIXME this is pretty dirty, to refactor
        let newQuantity = null;
        let newUnitCostPrice = null;
        if (this.state.smart) {
          newQuantity = this.state.smartNewQuantity;
          newUnitCostPrice = this.state.smartNewUnitCostPrice;
        } else if (this.state.manual) {
          newQuantity = this.state.quantity;
          newUnitCostPrice = this.state.unitCostPrice;
        }
        updateOnePosition(this.props.position.id, this.props.position.currency1.code, newQuantity, newUnitCostPrice)
            .then(() => {
                this.toggle();
                this.props.onUpdateOrDelete(this.props.index);
            })
            .catch((error) => {
              console.log("Error: " + error);
              this.setState({failure:true});
            })
    }

    display(evt) {
      const name = evt.target.name;
      switch(name) {
        case 'smart':
          this.setState({ smart: !this.state.smart, manual: false });
          break;
        case 'manual':
          this.setState({ smart: false, manual: !this.state.manual });
          break;
        default:
          this.setState({ smart: false, manual: false, });
      }
    }

    handleUseNewValues(evt) {
        const name = evt.target.name;
        const value = evt.target.value;
        this.setState({[name]: value}, () => this.populateNewValues());
    }

    populateNewValues() {
      const newQuantity = parseFloat(this.state.quantity) + parseFloat(this.state.smartAddQuantity)
      const newUnitCostPrice = (parseFloat(this.state.smartAddQuantity)) < 0
          ? parseFloat(this.state.unitCostPrice)
          :(parseFloat(this.state.smartAddUnitCostPrice) * parseFloat(this.state.smartAddQuantity) + parseFloat(this.state.unitCostPrice) * parseFloat(this.state.quantity)) / newQuantity
      this.setState({
        smartNewQuantity: newQuantity,
        smartNewUnitCostPrice: newUnitCostPrice
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
                        <Button outline color="primary" size="lg" name="smart" id="smart" block onClick={(evt) => this.display(evt)}>Smart</Button>
                      </FormGroup>
                      <div className="text-center">or</div>
                      <FormGroup>
                        <Button outline color="primary" size="lg" name="manual" id="manual" block onClick={(evt) => this.display(evt)}>Manual</Button>
                      </FormGroup>




                      <Collapse isOpen={this.state.smart}>
                        <h3 className="text-center">Smart change</h3>
                        <Table bordered>
                          <thead>
                            <tr>
                                <th>Currency</th>
                                <th className="text-right">Current Quantity</th>
                                <th className="text-right">Current Unit Cost Price</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td>{this.props.position.currency1.code}</td>
                              <td className="text-right">{this.state.quantity}</td>
                              <td className="text-right">${this.state.unitCostPrice}</td>
                            </tr>
                          </tbody>
                        </Table>
                        <FormGroup>
                            <Label for="quantity">Add or Remove quantity</Label>
                            <Input size="lg" type="text" name="smartAddQuantity" id="smartAddQuantity" ref="smartAddQuantity" defaultValue={this.state.smartAddQuantity} onBlur={(evt) => this.handleUseNewValues(evt)}/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                        <FormGroup>
                            <Label for="unitCostPrice">Unit Cost Price</Label>
                            <Input size="lg" type="text" name="smartAddUnitCostPrice" id="smartAddUnitCostPrice" ref="smartAddUnitCostPrice" defaultValue={this.state.smartAddUnitCostPrice} onBlur={(evt) => this.handleUseNewValues(evt)}/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                        <Table bordered>
                          <thead>
                            <tr>
                                <th>Currency</th>
                                <th className="text-right">New Quantity</th>
                                <th className="text-right">New Unit Cost Price</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td>{this.props.position.currency1.code}</td>
                              <td className="text-right"><Input size="lg" type="text" name="smartNewQuantity" id="smartNewQuantity" value={this.state.smartNewQuantity} /></td>
                              <td className="text-right"><Input size="lg" type="text" name="smartNewUnitCostPrice" id="smartNewUnitCostPrice" value={this.state.smartNewUnitCostPrice} /></td>
                            </tr>
                          </tbody>
                        </Table>
                      </Collapse>



                      <Collapse isOpen={this.state.manual}>
                        Manual change
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
                      </Collapse>
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
