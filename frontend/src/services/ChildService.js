import axios from 'axios';

export default {
    list(parentId) {
        return axios.get(`/user/${parentId}/list`);
    },
    
    registerChild(parentId, user) {
        return axios.post(`user/${parentId}/add-sub`, user);
    },

    updateChild(childUserId, child) {
        return axios.put(`/sub/${childUserId}`, child);
    },

    getChild(id) {
        return axios.get(`/sub/${id}`);
    },
    purchaseMascot(childUserId, mascot) {
        return axios.post(`/shop/${childUserId}`, mascot);
    }
}