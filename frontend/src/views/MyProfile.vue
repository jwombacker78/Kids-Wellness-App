<template>
  <div>
    <div v-if="this.$store.state.user.authorities[0].name === 'ROLE_USER'" :to="{ name: 'parent-summary'}">
        <parent-summary></parent-summary>
    </div>
    <div v-else :to="{ name: 'child-summary'}">
        <child-summary></child-summary>
    </div>
  </div>
</template>

<script>
import ChildSummary from './ChildSummary.vue';
import ParentSummary from './ParentSummary.vue';

export default {
    components: {
        ChildSummary,
        ParentSummary
    }
}
</script>

<style>

</style>
    