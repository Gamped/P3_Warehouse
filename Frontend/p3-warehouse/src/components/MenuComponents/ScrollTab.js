import React from "react";
import {Radio, FormGroup} from "react-bootstrap";

const BoxWithLongContent=(props)=>{
    const {radios}=props;
    const radioList = radios.map(radio =>{
        return (
            <Radio name="radioGroup" inline>{radio.name}</Radio>
        )
    })
    
    return(
            <div className="Scroller">
                <FormGroup>
                    {radioList}
                </FormGroup>
            </div>        
    )
}

export default BoxWithLongContent;