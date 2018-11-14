import React from "react";
import ScrollArea from 'react-scrollbar';

const BoxWithLongContent=(props)=>{
    const {radios}=props;
    const radioList = radios.map(radio =>{
        return (
            <div class="input-group">
                <input type="radio" key="name.id"/>
            </div>
        )
    })
    
    return(
        <div className="Scroller">
            <form action="">
                {radioList}
            </form>
        </div>
    )
}

export default BoxWithLongContent;