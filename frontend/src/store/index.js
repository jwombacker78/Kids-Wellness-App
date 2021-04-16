import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    parent: null || {},
    children: null || {},
    child: {
      id: "",
      authorities: {},
      username: "",
      firstName: "",
      lastName: "",
      email: "",
      activityMinutes: "",
      carrotBalance: "",
      playtimeBalance: "",
      steps: "",
      mascotId: "",
      parentId: "",
      active: "",
    },
    mascots: [
      {
        id: 1,
        name: "Halo",
        price: 1500,
      },
      {
        id: 2,
        name: "Viking",
        price: 2500,
      },
      {
        id: 3,
        name: "Dave",
        price: 1500,
      },{
        id: 4,
        name: "Monster",
        price: 75000,
      },
      {
        id: 5,
        name: "Dark Knight",
        price: 250000,
      },
      {
        id: 6,
        name: "Royalty",
        price: 500000,
      },{
        id: 7,
        name: "Birfday Kake",
        price: 44,
      },
      {
        id: 8,
        name: "SCUBA Steve",
        price: 2508,
      },
      {
        id: 9,
        name: "Mike Jo-dan",
        price: 500000,
      },
      {
        id: 10,
        name: "Ms. Meow",
        price: 234,
      }
    ],
    mascot: {
      id: 0,
      name: "",
      price: 0
    }
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_CHILDREN(state, children) {
      state.children = children;
    },
    SET_CHILD(state, child) {
      state.child = child;
    },
    ADD_CHILD(state, child) {
      state.children.push(child);
    },
    UPDATE_CHILD(state, child) {
      // state.currentChild = child;
      state.children.push(child);
    },
    SET_CHILD_MASCOT(state, mascot){
      state.child.mascotId = mascot.id;
    }
  }
})
