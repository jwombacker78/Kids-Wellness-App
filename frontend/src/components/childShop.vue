<template>
  <div>
    <mascot-list></mascot-list>
  </div>
</template>

<script>
import MascotList from "../components/MascotList2.vue";
export default {
  props: ['mascot'],
  components: {
    MascotList
  },
  methods: {


  }
};
</script>

<style scoped>
div {
  display: grid;
  justify-content: center;
}
</style>