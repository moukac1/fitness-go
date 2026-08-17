import axios from "axios";
import { act } from "react";

const API_URL = 'http://localhost:8080/api';
const api = axios.create({
  baseURL: API_URL
});
api.interceptors.request.use((config) => {
  //const token = localStorage.getItem('token');
  const userId = JSON.parse(localStorage.getItem('userId'));
  //if (token) {
   // config.headers['Authorization'] = `Bearer ${token}`;
  //}
  if (userId) {
    config.headers['X-User-ID'] = userId;
  }
  return config;
});

export const getActivities =  () => {
   api.get('/activities');
  
}
export const addActivity =  (activity) => {
  api.post('/activities/track' , activity);
  //return response.data;
}
export const getActivityDetail =  () => {
   api.get('/recommendations/activity/${id}' );
  
}