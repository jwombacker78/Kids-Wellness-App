<template>
  <div id="login" class=" wrap text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link :to="{ name: 'register' }">Need an account?</router-link>
      <button type="submit">Sign in</button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import ChildService from '../services/ChildService';

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },

      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            if (response.data.user.authorities[0].name == "ROLE_USER"){
              this.$router.push(`/user/${this.$store.state.user.id}/list`);
              } else if (response.data.user.authorities[0].name == "ROLE_CHILD"){
                  ChildService.getChild(this.$store.state.user.id)
                  .then((response) => {
                    if (response.status == 200) {
                      this.$store.commit("SET_CHILD", response.data);
                      // console.log(this.$store.state.child);
                    }
                    this.$router.push(`/sub/${this.$store.state.child.id}`);
                  })
                  .catch((err) => {
                    const response = err.response;

                    if (response.status >= 400) {
                      this.$store.commit("SET_CHILD", null);
                    }
                  });
                  this.$router.push(`/sub/${this.$store.state.child.id}`)
              } 
            // this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;
          console.log(error);
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

/* #username, #password {
 margin: 10px;
} */
/* .app {
  display: grid;
  justify-content: center;
  height: 100vh;
  width: 100%;
  
} */

.wrap {
   font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  max-width: 1200px;
  margin: 40px auto;
}

.text-center {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: 
    ". form ."
  ;
}
.form-signin{
  grid-area: form;
  display: grid;
  background: lightblue;
  padding: 15px;
  /* height: 100%; */
  border-radius: 15px 15px 15px 15px;
} 
/* h1 {
  display: flex;
  justify-content: center;
} */
input {
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  background-color: whitesmoke;
}
button{
  border-radius: 0.2rem;
  margin-left: 100px;
  margin-right: 100px;
}
/* label {
  padding: 30px;
} */




/* .form-signin {
  display: grid;
  justify-content: center;
}

label {
  padding-bottom: 5px;
  padding-top: 5px;
  font-weight: bold;
}

a {
  padding-top: 5px;
  padding-bottom: 5px;
}

#login {
  display: grid;
  background-color: rgba(65, 171, 169, 0.975);
  padding: 25px;
  border-radius: 15px 15px 15px 15px;
  width: 50%;
  
  width: 250vh;
  height: 100vh; 
}

h1 {
  display: grid;
  justify-content: center;
  font-weight: 50;
}

#app {
  display: grid;
  justify-content: center;
  height: 100vh;
  width: 100%;
  
}

html {
  background-color: rgba(116, 86, 225, 0.954);
  background-image: url(../assets/playground.jpg);
  background-repeat: no-repeat;
  background-position-x: center;
  background-position-y: center;
  background-size: 1000px;
}

#h3 {
  display: grid;
  justify-content: center;
} 

#nav {
  display: grid;
  justify-items: center;
  align-content: center;
  font-weight: 40;  
}
*/
</style>