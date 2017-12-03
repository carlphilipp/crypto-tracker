import React, {Component} from 'react';
import {getOneUser} from '../utils/api';
import {Table} from 'reactstrap';

class User extends Component {

    constructor(props) {
        super(props);
        this.state = {user: []};
    }

    getUser(userId) {
        getOneUser(userId).then((user) => {
            this.setState({user});
        });
    }

    componentDidMount() {
        this.getUser(this.props.userId);
    }

    render() {
        const {user} = this.state;
        return (
            <div>
                <h3 className="text-center">{user.id} {user.email}</h3>
                <hr/>
                <Table hover>
                    <thead>
                    <tr>
                        <th>Currency</th>
                        <th>Quantity</th>
                        <th>Value</th>
                        <th>Original Value</th>
                        <th>Gain</th>
                        <th>Gain Percentage</th>
                    </tr>
                    </thead>
                    <tbody>{
                        (user.positions != null)
                            ? user.positions.map((position, index) => (
                                <tr key={index}>
                                    <th scope="row">{position.currency1}</th>
                                    <td>{position.quantity}</td>
                                    <td>{position.value}$</td>
                                    <td>{position.originalValue}$</td>
                                    <td>{position.gain}$</td>
                                    <td>{position.gainPercentage}%</td>
                                </tr>))
                            : ''
                    }</tbody>
                </Table>
            </div>
        );
    }
}

export default User;
