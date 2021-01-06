import React, {useEffect, useReducer, createContext, useMemo, useState} from 'react';
import axios from 'axios';

export const TableContext = createContext({
    currentTime: '',
    selectedFile: '',
    dispatch : () => {}
});

const initialState = () => {
    return {
        currentTime: '',
        selectedFile: ''
    }
}

const reducer = (state, action) => {
    switch (action.type) {
        case 'CURRENT_TIME': {
            return {
                currentTime: action.currentTime
            }
        }
        case 'SELECTED_FILE': {
            return {

            }
        }
    }
}

const Home = () => {
    const [state, dispatch] = useReducer(reducer, initialState);
    const { currentTime, selectedFile } = state;

    useEffect(() => {
        axios.get('/api/now')
            .then((response) => {
                const currentTime = response.data;
                dispatch({type : 'CURRENT_TIME', currentTime});
            });
    },[currentTime]);

    const fileChangedHandler = (e) => {
        const files = e.target.files;
        dispatch({type : 'SELECTED_FILE', selectedFile});
    };

    const sendEvent = () => {
        console.log('sendEvent click!!');
    }

    return (
        <>
            <div>
                <input type="file" multiple onChange={fileChangedHandler} />
                <button onClick={sendEvent}>전송하기</button>
                {currentTime}
            </div>
        </>
    )
}

export default Home;