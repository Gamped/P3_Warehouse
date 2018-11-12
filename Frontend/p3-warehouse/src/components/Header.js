import React from 'react';

// The header component
const Header = (props) => {
    let title=props.title
    return(
        <div className="headerStyle">
            <titleText>{title}</titleText>
        </div>
    );
}

export default Header