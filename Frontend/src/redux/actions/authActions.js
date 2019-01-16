export const signIn = (credentials) =>{
    return (dispatch, getState, {getFirebase})=>{
        const firebase = getFirebase();

        firebase.auth().signInWithEmailAndPassword(
            credentials.email,
            credentials.password
        ).then( ()=> {
            dispatch({type: "LOGIN_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "LOGIN_ERROR",error})
        })
    }
}

export const signOut = () =>{
    return (dispatch,getState,{getFirebase}) =>{
        const firebase = getFirebase();
        firebase.auth().signOut().then(()=>{
            dispatch({type: "SIGNOUT_SUCCESS"})
        }).catch((error)=>{
            dispatch({type: "SIGNOUT_ERROR",error})
        })
    }
}

export const signUp = (payload) =>{
    return(dispatch,getState,{getFirebase,getFirestore}) =>{
        const firebase = getFirebase();
        const firestore = getFirestore();

        firebase.auth().createUserWithEmailAndPassword(
            payload.email,
            payload.password
        ).then((res)=>{
            return firestore.collection("users").doc(res.user.uid).set({
                //TODO: Indsæt hvad vi gerne vil have på dette dokument.
            })
        }).then(()=>{
            dispatch({type:"SIGNUP_SUCCESS"})
        }).catch(error =>{
            dispatch({type:"SIGNUP_ERROR",error})
        })
    }
}