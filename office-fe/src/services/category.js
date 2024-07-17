import axios from "axios";

export const getCategories = async() => {
    try {

        const response = await axios.get(`/category`);
        return { data: response.data.categories, error: null};

    } catch (error) {

        console.error(`getCategories(): `, error);
        return { data: null, error };

    }
};