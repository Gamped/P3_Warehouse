import React,{Component} from 'react';
import axios from 'axios';

import "../../Pages.css"
import "./EditOrderAddress.css"

export default class EditOrderAddress extends Component{
    constructor(props){
        super(props);
        const {company, contactPerson, address, zipCode, city} = props;
        this.state = {
            
        }
    }

    render(){
        return(
            <div className="PageStyle rounded">
                <div className="container col sm-1">
                    <form>
                        <input type="text" name="company" defaultValue={this.company} className="newForm" onChange={this.onChange} placeholder="Company"/> 
                        <input type="text" name="contactPerson" defaultValue={this.contactPerson} className="newForm" onChange={this.onChange} placeholder="Contact Person"/>
                        <input type="text" name="address" defaultValue={this.address} className="newForm" onChange={this.onChange} placeholder="Address"/>
                        <input type="text" name="zipCode" defaultValue={this.zipCode} className="newForm" onChange={this.onChange} placeholder="Zipcode"/>
                        <input type="text" name="city" defaultValue={this.city} className="newForm" onChange={this.onChange} placeholder="City"/>
                    </form>
                    <div className="container row">
                        <button className="btn btn-succes mx-2">Save Changes</button>
                        <button className="btn btn-warning mx-2">Discard Changes</button>
                    </div>
                </div>
            </div>
        )
    }
}