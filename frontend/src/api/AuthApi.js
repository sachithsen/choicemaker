import axios from 'axios';
import configs from "../Config";

const { API_BASE_URL } = configs;

export const authUser = async (username) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/auth/user`, {
            params: {username}
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const updateUser = async (username, userData) => {
    try {
        const response = await axios.put(`${API_BASE_URL}/${username}`, userData);
        return response.data;
    } catch (error) {
        throw error;
    }
};
