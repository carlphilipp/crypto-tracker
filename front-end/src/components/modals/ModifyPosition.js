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
import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader, FormFeedback, Collapse, Table} from 'reactstrap';
import {updateOnePosition, addFeePositionToCurrentUser} from '../../service/UserService';
import AlertFailure from '../alerts/AlertFailure';

class ModifyPosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
        };
        this.toggle = this.toggle.bind(this);
        this.modifyPosition = this.modifyPosition.bind(this);
    }

    init() {
      this.setState({
        manualMod: false,
        manualQuantity: this.props.position.quantity,
        manualQuantityValid: null,
        manualUnitCostPrice: this.props.position.unitCostPrice,
        manualUnitCostPriceValid: null,

        smartMod: false,
        smartAddQuantity: 0,
        smartQuantityValid: null,
        smartAddUnitCostPrice: 0,
        smartAddUnitCostPriceValid: null,
        smartNewQuantity: this.props.position.quantity,
        smartNewUnitCostPrice: this.props.position.unitCostPrice,

        feeMod: false,
        feeAmount: 0.0,
        feeAmountValid: null,
        feeNewQuantity: this.props.position.quantity,

        failure: false,
        formValid: false,
      });
    }

    toggle() {
        this.init()
        this.setState({ modal: !this.state.modal });
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
        case "manualQuantity":
          this.setState({manualQuantityValid: !isNaN(value)}, () => this.validateForm());
          break;
        case "manualUnitCostPrice":
          this.setState({manualUnitCostPriceValid: !isNaN(value)}, () => this.validateForm());
          break;
        default:
          break;
      }
    }

    validateForm() {
      this.setState({
        formValid: (this.state.manualQuantityValid && this.state.manualUnitCostPriceValid)
                || (this.state.smartQuantityValid && this.state.smartAddUnitCostPriceValid)
                || this.state.feeAmountValid
      });
    }

    onLogin() { this.props.onLogin() }

    modifyPosition() {
        // FIXME this is pretty dirty, to refactor
        let newQuantity = null;
        let newUnitCostPrice = null;
        let smartAddQuantity = null;
        let smartAddUnitCostPrice = null;
        if (this.state.smartMod) {
          newQuantity = this.state.smartNewQuantity;
          newUnitCostPrice = this.state.smartNewUnitCostPrice;
          smartAddQuantity =  this.state.smartAddQuantity;
          smartAddUnitCostPrice = this.state.smartAddUnitCostPrice;
          updateOnePosition(this.props.position.id, this.props.position.currency1, this.props.position.currency2, newQuantity, newUnitCostPrice, smartAddQuantity, smartAddUnitCostPrice)
              .then(() => {
                  this.toggle();
                  this.props.onUpdateOrDelete(this.props.index);
              })
              .catch((error) => {
                console.log("Error: " + error);
                this.setState({failure:true});
              })
        } else if (this.state.manualMod) {
          newQuantity = this.state.manualQuantity;
          newUnitCostPrice = this.state.manualUnitCostPrice;
          updateOnePosition(this.props.position.id, this.props.position.currency1, this.props.position.currency2, newQuantity, newUnitCostPrice, smartAddQuantity, smartAddUnitCostPrice)
              .then(() => {
                  this.toggle();
                  this.props.onUpdateOrDelete(this.props.index);
              })
              .catch((error) => {
                console.log("Error: " + error);
                this.setState({failure:true});
              })
        } else if (this.state.feeMod) {
          newQuantity = this.state.feeNewQuantity;
          newUnitCostPrice = this.props.position.unitCostPrice;
          addFeePositionToCurrentUser(this.props.position.id, this.state.feeAmount)
          .then(() => {
              this.toggle();
              this.props.onUpdateOrDelete(this.props.index);
          })
          .catch((error) => {
            console.log("Error: " + error);
            this.setState({failure:true});
          })
        }
    }

    showHideForm(evt) {
      const name = evt.target.name;
      switch(name) {
        case 'smart':
          this.setState({ smartMod: !this.state.smartMod, manualMod: false, feeMod: false });
          break;
        case 'manual':
          this.setState({ smartMod: false, manualMod: !this.state.manualMod, feeMod: false });
          break;
        case 'fee':
          this.setState({ smartMod: false, manualMod: false, feeMod: !this.state.feeMod });
          break;
        default:
          this.setState({ smartMod: false, manualMod: false, feeMod: false});
      }
    }

    handleUseNewValues(evt) {
        const name = evt.target.name;
        const value = evt.target.value;
        this.setState({[name]: value}, () => this.validateSmartMod(name, value));
    }

    validateSmartMod(name, value) {
      switch(name) {
        case "smartAddQuantity":
          this.setState({smartQuantityValid: !isNaN(value)}, () => this.populateNewValues());
          break;
        case "smartAddUnitCostPrice":
          this.setState({smartAddUnitCostPriceValid: !isNaN(value)}, () => this.populateNewValues());
          break;
        default:
          break;
      }
    }

    populateNewValues() {
        const newQuantity = parseFloat(this.state.manualQuantity) + parseFloat(this.state.smartAddQuantity)
        const newUnitCostPrice = (parseFloat(this.state.smartAddQuantity)) < 0
            ? parseFloat(this.state.manualUnitCostPrice)
            :(parseFloat(this.state.smartAddUnitCostPrice) * parseFloat(this.state.smartAddQuantity) + parseFloat(this.state.manualUnitCostPrice) * parseFloat(this.state.manualQuantity)) / newQuantity;
        if (!isNaN(newQuantity) && !isNaN(newUnitCostPrice)) {
          this.setState({
            smartNewQuantity: newQuantity,
            smartNewUnitCostPrice: newUnitCostPrice
          }, () => this.validateForm());
        } else {
          this.validateForm()
        }
    }

    updateNewFewQuantity(evt) {
      const value = evt.target.value;
      const feeNewQuantity = this.props.position.quantity - value
      this.setState({
        feeAmount: value,
        feeNewQuantity: (!isNaN(feeNewQuantity) ? feeNewQuantity : this.props.position.quantity ),
        feeAmountValid: !isNaN(feeNewQuantity)
      }, () => this.validateForm());
    }

    render() {
        return (
            <div>
                <Button color="secondary" size="lg" onClick={this.toggle}>Modify</Button>
                <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}>
                    <ModalHeader toggle={this.toggle}>Modify</ModalHeader>
                    <ModalBody>
                      <div className="container-fluid">
                        <div className="col-lg-4">
                            <Button outline color="primary" size="lg" name="smart" id="smart" block onClick={(evt) => this.showHideForm(evt)}>Smart</Button>
                        </div>
                        <div className="col-lg-4">
                          <Button outline color="primary" size="lg" name="manual" id="manual" block onClick={(evt) => this.showHideForm(evt)}>Manual</Button>
                        </div>
                        <div className="col-lg-4">
                          <Button outline color="primary" size="lg" name="fee" id="fee" block onClick={(evt) => this.showHideForm(evt)}>Add fee</Button>
                        </div>
                      </div>
                      <Collapse isOpen={this.state.smartMod}>
                        <hr/>
                        <h4 className="text-center">Smart change</h4>
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
                              <td className="text-right">{this.state.manualQuantity}</td>
                              <td className="text-right">${this.state.manualUnitCostPrice}</td>
                            </tr>
                          </tbody>
                        </Table>
                        <FormGroup>
                            <Label for="quantity">Add or Remove quantity</Label>
                            <Input size="lg" type="text" name="smartAddQuantity" id="smartAddQuantity" valid={this.state.smartQuantityValid} defaultValue={this.state.smartAddQuantity} onBlur={(evt) => this.handleUseNewValues(evt)}/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                        <FormGroup>
                            <Label for="unitCostPrice">Unit Cost Price</Label>
                            <Input size="lg" type="text" name="smartAddUnitCostPrice" id="smartAddUnitCostPrice" valid={this.state.smartAddUnitCostPriceValid} defaultValue={this.state.smartAddUnitCostPrice} onBlur={(evt) => this.handleUseNewValues(evt)}/>
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
                              <td className="text-right">{this.state.smartNewQuantity}</td>
                              <td className="text-right">${this.state.smartNewUnitCostPrice}</td>
                            </tr>
                          </tbody>
                        </Table>
                      </Collapse>
                      <Collapse isOpen={this.state.manualMod}>
                        <hr/>
                        <h4 className="text-center">Manual change</h4>
                        <FormGroup>
                            <Label for="ticker">Ticker</Label><br />
                            {this.props.position.currency1.code}
                        </FormGroup>
                        <FormGroup>
                            <Label for="quantity">Quantity</Label>
                            <Input size="lg" type="text" name="manualQuantity" id="manualQuantity" onBlur={evt => this.handleUserInput(evt)} defaultValue={this.state.manualQuantity} valid={this.state.manualQuantityValid} autoFocus="true"/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                        <FormGroup>
                            <Label for="unitCostPrice">Unit Cost Price</Label>
                            <Input size="lg" type="text" name="manualUnitCostPrice" id="manualUnitCostPrice" onBlur={evt => this.handleUserInput(evt)} defaultValue={this.state.manualUnitCostPrice} valid={this.state.manualUnitCostPriceValid}/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                      </Collapse>
                      <Collapse isOpen={this.state.feeMod}>
                        <hr/>
                        <h4 className="text-center">Add a fee</h4>
                        <FormGroup>
                            <Label for="ticker">Ticker</Label><br />
                            {this.props.position.currency1.code}
                        </FormGroup>
                        <FormGroup>
                            <Label for="fee">Fee amount</Label>
                            <Input size="lg" type="text" name="fee" id="fee" onBlur={evt => this.updateNewFewQuantity(evt)} defaultValue={this.state.feeAmount} valid={this.state.feeAmountValid} autoFocus="true"/>
                            <FormFeedback>Must be a valid number</FormFeedback>
                        </FormGroup>
                        <FormGroup>
                            <Label for="feeQuantity">New quantity</Label><br />
                            {this.state.feeNewQuantity}
                        </FormGroup>
                      </Collapse>
                    </ModalBody>
                    {(this.state.failure) ? <AlertFailure display="Sorry, something went wrong" color="danger"/> : ''}
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
