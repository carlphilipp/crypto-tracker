import React from 'react';
import {Button, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {addPosition} from '../utils/ApiClient';
import {getAccessToken} from '../service/AuthService';

class AddPosition extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            ticker: 'BTC',
            quantity: null,
            unitCostPrice: null,
        };
        this.toggle = this.toggle.bind(this);
        this.add = this.add.bind(this);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    updateFormTicker(evt) {
        this.setState({
            ticker: evt.target.value
        });
    }

    updateFormQuantity(evt) {
        this.setState({
            quantity: evt.target.value
        });
    }

    updateUnitCostPrice(evt) {
        this.setState({
            unitCostPrice: evt.target.value
        });
    }

    add() {
        addPosition(getAccessToken(), this.props.user.id, this.state.ticker, this.state.quantity, this.state.unitCostPrice)
            .then((user) => this.props.updateUserInState(user))
            .then(this.toggle())
            .catch(error => {
                console.log(error);
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
                                <Input size="lg" type="select" name="select" id="ticker" onChange={evt => this.updateFormTicker(evt)} >
                                {this.props.tickers.map(ticker => ticker.currency1.code).map((position, index) => (<option key={index}>{position}</option>))}
                                </Input>
                            </FormGroup>
                            <FormGroup>
                                <Label for="quantity">Quantity</Label>
                                <Input size="lg" type="text" name="quantity" onChange={evt => this.updateFormQuantity(evt)} id="quantity" placeholder="1.0"/>
                            </FormGroup>
                            <FormGroup>
                                <Label for="unitCostPrice">Unit Cost Price</Label>
                                <Input size="lg" type="text" name="unitCostPrice" id="unitCostPrice" onChange={evt => this.updateUnitCostPrice(evt)} placeholder="100"/>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" size="lg" onClick={this.add}>Add!</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default AddPosition;
