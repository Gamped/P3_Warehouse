import React from 'react';
//TODO: Import react router dom here and make the a to link tos

// The header component
const Menu = (props) => {
    const {buttons}=props;
    const mappedButtonList = buttons.map(button =>{
        return (
            <div className="Link">
                <a href={button.location}>{button.name}</a>
                <br/>
            </div>
        )
    })
    return(
        <div class="menuStyle">
           {mappedButtonList}
        </div>
    )
}

export default Menu