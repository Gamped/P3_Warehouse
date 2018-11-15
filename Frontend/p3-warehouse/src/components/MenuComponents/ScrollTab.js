import React from "react";
import {Radio, FormGroup} from "react-bootstrap";

const BoxWithLongContent=(props)=>{
    const {radios}=props;
    const radioList = radios.map(radio =>{
        return (
            <ToggleButton value={radio.id} >{radio.name}</ToggleButton>
        )
    })
    
    return(
            <div className="Scroller">
                <ToggleButtonGroup type="radio" name="options" defaultValue={radios[0].id}>
                    {radioList}
                </ToggleButtonGroup>
            </div>        
    )
}

export default BoxWithLongContent;