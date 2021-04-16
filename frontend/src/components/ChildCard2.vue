<template>
  <div class="card">
       <h2 class="child-name">{{ child.firstName }} {{ child.lastName }}</h2>

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
          width="auto"
          height="auto"
        />
      </div>
        <div>
        <update-child v-bind:child="child" v-if="$store.state.user.authorities[0].name === 'ROLE_USER'" />
        </div>
  </div>
</template>

<script>
import UpdateChild from "@/components/updateChild.vue";
// import ChildService from '../services/ChildService';
export default {
    name: 'child-card-2',
    components: {
        UpdateChild,
    },
    props: {
        child: Object,
    },
    // created() {
    //   ChildService.getChild(this.$route.params.id)
    //   .then((response) => {
    //     if (response.status == 200) {
    //       this.$store.commit("SET_CHILD", response.data);
    //       console.log(this.$store.state.child);
    //     }
    //   })
    //   .catch((err) => {
    //     let response = err.response;
    //     console.log("SET CHILD ERROR" + response);
    //     this.$store.commit("SET_CHILD", null);
    //   });
    // }

}
</script>

<style scoped>
.card {
  display: grid;
  border: 2px solid black;
  border-radius: 0.5rem;
  justify-content: center;
  width: 30vw;
  margin: 10px;
  background-color: rgb(61, 192, 240);
}
p {
  display: grid;
  justify-content: center;
  font-weight: bold;
}
h2 {
  display: grid;
  border: 1px solid black;
  border-radius: 0.5rem;
  justify-content: center;
  background-color: whitesmoke;
  padding: 10px;
  font-size: 50px;
}
img {
  width: 50vh;
  margin-top: 15px;
}
.balances {
  display: grid;
  border: 1px solid black;
  border-radius: 0.5rem;
  width: 50vh;
  height: 20px;
  justify-self: center;
  justify-items: center;
  background-color: whitesmoke;
}
</style>