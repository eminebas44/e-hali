import axios from 'axios';

// Backend (Spring Boot) adresimiz
const api = axios.create({
    baseURL: 'http://localhost:8383/api',
});

// Her istekte token kontrolü yap
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;