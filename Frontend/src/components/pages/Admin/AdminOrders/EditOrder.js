import React,{Component} from 'react';
import axios from 'axios';
import ReactTable from 'react-table';

import "../../Pages.css";

export default class EditOrder extends Component{
    constructor(props){
        super(props);
        this.state = {

        }
    }

    render(){
        return(
            <div className="PageStyle">
                <div className="container row">
                    <div className="TextFields container col">
                    </div>
                    <div className="AllProducts container col">
                    </div>
                    <div className="OrderLines container col">
                    </div>

                </div>
            </div>
        )
    }
}