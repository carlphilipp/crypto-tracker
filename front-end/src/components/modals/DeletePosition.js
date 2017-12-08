import React from 'react';
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';
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
        };
        this.toggle = this.toggle.bind(this);
        this.delete = this.delete.bind(this);
    }

    toggle() { this.setState({ modal: !this.state.modal }); }

    delete() {
        const accessToken = getAccessToken();
        const userId = getUserId();
        deletePosition(accessToken, userId, this.props.position.id)
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
                        <b>Currency:</b> {this.props.position.currency1.currencyName}<br />
                        <b>Quantity:</b> {this.props.position.quantity}<br />
                        <b>Value:</b> <FormattedNumber value={this.props.position.value} style={`currency`} currency="USD"/><br />
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
