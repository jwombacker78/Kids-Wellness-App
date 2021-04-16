<template>
  <div id="register" class="wrap text-center example-custom-css">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <!-- <label for="username" class="sr-only">Username</label>
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
      <password-meter id="passwordMeter" :password="user.password" />
      <label for="password" class="sr-only">Confirm Password</label>
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      /> -->
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
        <div class="password-container">
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <password-meter id="passwordMeter" :password="user.password" />
        </div>
        
          
        <label for="password" class="sr-only">Confirm Password</label>
      
        <input
          type="password"
          id="confirmPassword"
          class="form-control"
          placeholder="Confirm Password"
          v-model="user.confirmPassword"
          required
        />
      
      <div name="hint">
        <div
          v-if="passwordValidation.errors.length > 0 && !submitted"
          class="hints"
        >
          <h2>Hints</h2>
          <p v-for="error in passwordValidation.errors" v-bind:key="error">
            {{ error }}
          </p>
        </div>
      </div>

      <div class="matches" v-if="notSamePasswords">
        <p>Passwords don't match.</p>
      </div>

      <label for="firstname" class="sr-only">First Name</label>
      
      <input
        type="firstname"
        id="firstname"
        class="form-control"
        placeholder="First Name"
        v-model="user.firstName"
        required
      />
      

      <label for="lastname" class="sr-only">Last Name</label>
      <input
        type="lastname"
        id="lastname"
        class="form-control"
        placeholder="Last Name"
        v-model="user.lastName"
        required
      />
      

      <label for="email" class="sr-only">Email</label>
      
      <input
        type="email"
        id="email"
        class="form-control"
        placeholder="Email"
        v-model="user.email"
        required
      />

      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import passwordMeter from "vue-simple-password-meter";

export default {
  components: {
    passwordMeter,
  },
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
        firstName: "",
        lastName: "",
        email: "",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
      rules: [
        { message: "One lowercase letter required.", regex: /[a-z]+/ },
        { message: "One uppercase letter required.", regex: /[A-Z]+/ },
        { message: "8 characters minimum.", regex: /.{8,}/ },
        { message: "One number required.", regex: /[0-9]+/ },
      ],
      passwordVisible: false,
      submitted: false,
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user, this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    togglePasswordVisibility() {
      this.passwordVisible = !this.passwordVisible;
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
  computed: {
    notSamePasswords() {
      if (this.passwordsFilled) {
        return this.user.password !== this.user.confirmPassword;
      } else {
        return false;
      }
    },
    passwordsFilled() {
      return this.user.password !== "" && this.user.confirmPassword !== "";
    },
    passwordValidation() {
      let errors = [];
      for (let condition of this.rules) {
        if (!condition.regex.test(this.user.password)) {
          errors.push(condition.message);
        }
      }
      if (errors.length === 0) {
        return { valid: true, errors };
      } else {
        return { valid: false, errors };
      }
    },
  },
};
</script>

<style scoped>
/* form {
  display: grid;
  justify-content: center;
} */

.wrap {
   font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  max-width: 400px;
  margin: 40px auto;
}

.register {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: 
  ". form ."
  ". form ."
  ". form ."
  ;
}
.form-register {
  grid-area: form;
  display: grid;
  background: lightblue;
  padding: 0px 25px 25px 25px;
  border-radius: 15px 15px 15px 15px;
}
h1 {
  display: flex;
  justify-content: center;
}
label {
  padding: 2px;
}
input {
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  background-color: whitesmoke;
}
.password-container{
  position: relative;
  display: flex;
  align-items: center;
}
.hints {
  grid-area: hints;
  padding: 8px;
  background: whitesmoke;
  margin: 1em 0;
  font-size: 0.9em;
  color: darken(#d4dedf, 50%);
  border-radius: 0.5rem;
}
.hints h2{
		margin: .5em 0 .2em 0;
}
.hints p {
  margin: 0;
  padding-left: 1em;
}

.visibility {
  all: unset;
  background: whitesmoke;
  display: inline-block;
  color: white;
  padding: 2px 0.4em 0;
  vertical-align: center;
}
.material-icons {
  font-size: 1.5em;
}

[type="password"] {
  letter-spacing: 0.2em;
}
button{
  border-radius: 0.2rem;
  margin-left: 100px;
  margin-right: 100px;
}

.example-custom-css .po-password-strength-bar {
  border-radius: 2px;
  transition: all 0.2s linear;
  height: 20px;
  width: 20px;
  position: absolute;
  right: 10px;
  margin-top: auto;
}

.example-custom-css .po-password-strength-bar.risky {
  background-color: #f95e68;
}

.example-custom-css .po-password-strength-bar.guessable {
  background-color: #fb964d;
}

.example-custom-css .po-password-strength-bar.weak {
  background-color: #fdd244;
}

.example-custom-css .po-password-strength-bar.safe {
  background-color: #b0dc53;
}

.example-custom-css .po-password-strength-bar.secure {
  background-color: #35cc62;
}

#childRegistration {
  display: grid;
}
</style>
