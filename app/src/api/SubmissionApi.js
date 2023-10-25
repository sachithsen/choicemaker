import axios from 'axios';
import configs from "../Config";

const { API_BASE_URL } = configs;

export const createSubmission = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/submission`, data);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const selectSubmission = async (submissionId) => {
    console.log("Sub ID : ", submissionId)
    try {
        const response = await axios.get(`${API_BASE_URL}/selectSubmission`, {
            params: {submissionId}
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};