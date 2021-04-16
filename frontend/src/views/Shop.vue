<template>
  <div>
      <div id="childShop">
        <child-shop></child-shop>
      </div>
  </div>
</template>

<script> 
import ChildShop from '../components/childShop.vue';
export default {
    name: "shop",
    components: {
        ChildShop,
    }

}
</script>

<style>

</style>