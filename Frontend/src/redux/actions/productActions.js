
export const createNewProduct = (payload) => {
    return(dispatch, getState, {getFirebase, getFirestore}) =>{
        const firestore = getFirestore();
        firestore.collection("products").add({
            ...payload,
            created: new Date.now(),
        }).then(()=>{
            dispatch({type:"CREATE_PRODUCT", payload})
        }).catch((error)=>{
            dispatch({type:"CREATE_PRODUCT_ERROR",error})
        })
    }
};