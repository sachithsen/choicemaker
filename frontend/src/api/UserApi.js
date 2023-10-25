import axios from 'axios';
import configs from "../Config";

const { API_BASE_URL } = configs;

export const getAllUsers = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/auth/users`);
        return response.data;
    } catch (error) {
        throw error;
    }
};