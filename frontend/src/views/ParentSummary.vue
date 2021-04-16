<template>
  <div>
      <children-list></children-list>
  </div>
</template>

<script>
import ChildrenList from '@/components/ChildrenList2.vue'
export default {
    components: {
        ChildrenList
    }
}
</script>

<style>
children-list {
    display: grid;
    justify-content: center;
}
</style>