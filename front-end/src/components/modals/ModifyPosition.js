import React from 'react';
import {Button, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
import {updatePosition} from '../../utils/ApiClient';
import {getAccessToken} from '../../service/AuthService';
import LoginFailure from '../LoginFailure';

class ModifyPosition extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            quantity: null,
            unitCostPrice: null,
            failure: false,
        };
        this.toggle = this.toggle.bind(this);
        this.modifyPosition = this.modifyPosition.bind(this);
    }

    componentDidMount() {
      this.setState({
          quantity: this.props.position.quantity,
          unitCostPrice: this.props.position.unitCostPrice,
      });
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    handleUserInput(e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({
          [name]: value,
          failure: false
        });
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
                          <Label for="ticker">Ticker</Label>
                          <Input size="lg" type="text" name="select" id="ticker" onChange={evt => this.handleUserInput(evt)} value={this.props.position.currency1.code}/>
                      </FormGroup>
                      <FormGroup>
                          <Label for="quantity">Quantity</Label>
                          <Input size="lg" type="text" name="quantity" id="quantity" onChange={evt => this.handleUserInput(evt)} value={this.state.quantity}/>
                      </FormGroup>
                      <FormGroup>
                          <Label for="unitCostPrice">Unit Cost Price</Label>
                          <Input size="lg" type="text" name="unitCostPrice" id="unitCostPrice" onChange={evt => this.handleUserInput(evt)} value={this.state.unitCostPrice}/>
                      </FormGroup>
                    </ModalBody>
                    {/* FIXME: create a state failure*/}
                    {(this.state.failure) ? <LoginFailure /> : ''}
                    <ModalFooter>
                        <Button color="success" size="lg" onClick={this.modifyPosition}>Modify</Button>{' '}
                        <Button color="secondary" size="lg" onClick={this.toggle}>Cancel</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default ModifyPosition;
