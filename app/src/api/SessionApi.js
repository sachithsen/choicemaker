import axios from 'axios';
import configs from "../Config";

const { API_BASE_URL } = configs;

export const createSession = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/session`, data);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getSessionById = async (sessionId) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/session`, {
            params: {sessionId}
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getAllSessions = async (username) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/sessions`, {
            params: {username}
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};