// acts as a form for registering a child account

<template>
  <div>
    <p >Register New Child</p>
    <form @submit="createChild(), clearNewChild()">
      <input
        type="text"
        id="username"
        placeholder="Username"
        v-model="child.username"
        required
        autofocus
      />
      <input
        type="password"
        id="password"
        placeholder="Password"
        v-model="child.password"
        required
      />
      <input
        type="password"
        id="confirm-password"
        placeholder="Confirm Password"
        v-model="child.confirmPassword"
        required
      />
      <input
        type="text"
        id="first-name"
        placeholder="First Name"
        v-model="child.firstName"
        required
      />
      <input
        type="text"
        id="last-name"
        placeholder="Last Name"
        v-model="child.lastName"
        required
      />
       <input
        type="email"
        id="email"
        placeholder="Email@Example.com"
        v-model="child.email"
        required
      />
      <button type="Submit">Register</button>
    </form>
  </div>
</template>

<script>
import ChildService from "../services/ChildService";

export default {
  data() {
    return {
      child: {
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        parentId: this.$store.state.user.id,
        email: "",
        role: "child",
      },
    };
  },
  methods: {

    createChild() {
      ChildService.registerChild(this.$store.state.user.id, this.child).then(
        (response) => {
          if (response.status == 200) {
            this.$store.commit("ADD_CHILD", this.data.child);
            this.$router.push({
              path: "/",
            });
          }
        }
      );
    },
    clearNewChild() {
      this.child = {
        username: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        parentId: this.$store.state.user.id,
        email: "",
        role: "child",
      };
    }
  }
};
</script>
<style scoped>

form {
  display: grid;
  justify-content: center;
}
div {
  display: grid;
  justify-content: center;
  background-color: rgb(61, 192, 240);
  width: 50vh;
  padding: 10px;
  border-radius: 0.5rem;
  margin: 10px;
}

input {
  display: grid;
  margin-top: 5px;
  margin-bottom: 5px;
}
p{
  display: grid;
  justify-content: center;
  background-color: whitesmoke;
  border-radius: 0.5rem;
}

</style>