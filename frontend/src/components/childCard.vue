<template>
  <div>
    <div class="childCard" v-for="child in children" :key="child.id">
      <h2>{{ child.firstName }} {{ child.lastName }}</h2>

      <p>Steps</p>
      <div class="balances">{{ parseInt(child.steps) }}</div>

      <p>Activity Minutes</p>
      <div class="balances">{{ parseInt(child.activityMinutes) }}</div>

      <p>Carrots</p>
      <div class="balances">{{ parseInt(child.carrotBalance) }}</div>

      <p>Playtime (seconds)</p>
      <div class="balances">{{ parseInt(child.playtimeBalance) }}</div>

      <div>
        <p>Mascot</p>
        <img
          v-bind:src="require(`../assets/mascot${child.mascotId}.jpg`)"
          alt=""
          width="200"
          height="200"
        />
      </div>
      <update-child v-bind:child="child" v-if="$store.state.user.authorities[0].name === 'ROLE_USER'" />
    
    </div>
    
  </div>
</template>

<script>
import ChildService from "@/services/ChildService.js";
import updateChild from './updateChild.vue';

export default {
  components: { updateChild },
  data() {
    return {}; 
  },
  created() {
    if (this.$store.state.user.authorities[0].name === "ROLE_USER"){
      ChildService.list(this.$store.state.user.id)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_CHILDREN", response.data);
          }
        })
        .catch((err) => {
          const response = err.response;

          if (response.status == 500) {
            this.$store.commit("SET_CHILDREN", null);
          }
        });
    } else {
      ChildService.getChild(this.$store.state.user.id)
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_CHILD", response.data);
        }
      })
      .catch((err) => {
        const response = err.response;

        if (response.status >= 400) {
          this.$store.commit("SET_CHILD", null);
        }
      });
    }
    
  },
  computed: {
    children() {
      return this.$store.state.children;
    },
  },
};
</script>

<style scoped>

.childCard {
  display: grid;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  justify-content: center;
  width: 100%;
  margin: 10px;
  background-color: rgb(61, 192, 240);
}

p {
  display: grid;
  justify-content: center;
}

h2 {
  display: grid;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  justify-content: center;
  background-color: whitesmoke;
}

img {
  width: 50vh;
  margin-top: 15px;
}

.balances {
  display: grid;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  width: 50vh;
  height: 20px;
  justify-self: center;
  justify-items: center;
  background-color: whitesmoke;
}

form {
  display: grid;
  justify-content: center;
  margin: 10px;
}
</style>