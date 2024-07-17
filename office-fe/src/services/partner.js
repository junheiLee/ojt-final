import axios from "axios";

export const getPartners = async() => {
    try {

        const response = await axios.get(`/partners`);
        return { data: response.data.partners, error: null};

    } catch (error) {

        console.error(`getPartners(): `, error);
        return { data: null, error };

    }
};