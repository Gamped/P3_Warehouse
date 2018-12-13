export function variableNameToString(objectWithField) {
    let varname = Object.keys(objectWithField)[0];
    let varnameWithCapitalLetter = varname.slice(0,1) + varname.slice(1,varname.length);
    
    return varnameWithCapitalLetter;
}

