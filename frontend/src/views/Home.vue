<template>
  <div class="home">
    <div id="parent">
      <div id="childCard">
        <child-card></child-card>
      </div>
      <div id="childRegistration" v-if="this.$store.state.user.authorities[0].name === 'ROLE_USER'">
        <empty-child-card></empty-child-card>
      </div>
    </div>
  </div>
</template>

<script>
import ChildCard from "../components/childCard.vue";
import EmptyChildCard from "../components/emptyChildCard.vue";



export default {
  name: "home",
  components: {
    ChildCard,
    EmptyChildCard,
  },
};
</script>

<style scoped>
#parent {
  display: grid;
  /* grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: ". chilCard ."
                        ". childRegistration ."; */
  justify-content: center;
}
</style>