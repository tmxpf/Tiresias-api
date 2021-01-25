import React, {useEffect, useReducer, createContext} from 'react';
import { Navbar, Nav, Button } from 'reactstrap';
import axios from 'axios';

export const TableContext = createContext({
    currentTime: '',
    videoFile: null,
    dispatch : () => {}
});

const initialState = () => {
    return {
        currentTime: '',
        videoFile: null
    }
}

const reducer = (state, action) => {
    switch (action.type) {
        case 'CURRENT_TIME': {
            return {
                ...state,
                currentTime: action.currentTime
            }
        }
        case 'UPLOAD_FILE': {
            return {
                ...state,
                videoFile: action.file
            }
        }
    }
}

const ConverVideo = () => {
    const [state, dispatch] = useReducer(reducer, initialState);
    const { currentTime, videoFile } = state;

    useEffect(() => {
        axios.get('/api/now')
            .then((response) => {
                const currentTime = response.data;
                dispatch({type : 'CURRENT_TIME', currentTime});
            });
    }, [currentTime]);

    const fileChangedHandler = (e) => {
        const file = e.target.files;
        dispatch({type : 'UPLOAD_FILE', file});
    };

    const sendEvent = () => {
        const formData = new FormData();
        formData.append("uploadVideoFile", videoFile[0], videoFile.name);

        const config = {
            headers: {
                "content-type": "multipart/form-data"
            }
        };

        axios.post("api/video-convert", formData, config)
            .then(response => {
                console.log("success!!");
                console.log(response);
            })
            .catch(response => {
                console.log("error!!");
                console.log(response);
            });
    }

    return (
        <div>
            <input type="file" multiple onChange={fileChangedHandler} />
            <Button onClick={sendEvent}>전송하기</Button>
            {currentTime}
        </div>
    )
}

export default ConverVideo;