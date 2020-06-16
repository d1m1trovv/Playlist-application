import React, {Component} from 'react';
import {
    Table,
    TableBody,
    TableHead,
    TableCell,
    TableRow, TextField
} from "@material-ui/core";
import CheckIcon from "@material-ui/icons/Check";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";

export default class List extends Component {
    constructor(props) {
        super(props);
    }

    row(i, data){
        return(
            <TableRow key={i} selectable={false}>
                {this.props.header.map((element, index) => (
                    <TableCell key={index}>
                        {this.props.editableElementIndex === i ? (
                            <TextField
                                name={element.prop}
                                onChange={e => this.props.handleChange(e, element.prop, i)}
                                value={data[element.prop]}
                            />
                        ) : (
                            data[element.prop]
                        )}
                    </TableCell>
                ))}
                <TableCell>
                    {this.props.editableElementIndex === i ? (
                        <CheckIcon onClick={() => this.props.handleStopEditing()} />
                    ) : (
                        <EditIcon onClick={() => this.props.handleStartEditing(i)} />
                    )}
                </TableCell>
                <TableCell>
                    <DeleteIcon onClick={() => this.props.handleDelete(i)} />
                </TableCell>
            </TableRow>
        )
    }

    showInfo(){
        return(
            <Table>
                <TableHead>
                    <TableRow>
                        {this.props.header.map((element, index) => (
                            <TableCell key={index}><b>{element.name}</b></TableCell>
                        ))}
                        <TableCell />
                        <TableCell />
                    </TableRow>
                </TableHead>
                <TableBody>
                    {this.props.data.map((element, index) =>
                        this.row(
                            index,
                            element
                        )
                    )}
                </TableBody>
            </Table>
        )
    }

    render() {
        return(
            this.showInfo()
        )
    }
}