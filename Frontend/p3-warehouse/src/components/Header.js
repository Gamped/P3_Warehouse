import React from 'react';

// The header component
const Header = (props) => {
    const title=props.title
    return(
        <div className="headerStyle">
            <titleText>{title}</titleText>
        </div>
    );
}

export default Header