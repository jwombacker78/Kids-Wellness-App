<template>
  <div class="child-card">
      <child-card v-bind:child="$store.state.child"></child-card>
  </div>
</template>

<script>
import ChildCard from '../components/ChildCard2.vue';

export default {
    components: {
        ChildCard,  
    },
    props: {
        child: Object,
    },

}
</script>

<style>
child-card {
    display: grid;
    justify-content: center;
}
.child-card {
    display: grid;
    justify-content: center;
}
</style>