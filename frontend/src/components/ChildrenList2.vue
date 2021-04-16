<template>
  <section>
      <h2 class="title">Children</h2>
      <div class="children-list">
        <div class="child">
          <child-card-2 v-for="child in this.$store.state.children" v-bind:child="child" v-bind:key="child.id"></child-card-2>
        </div>
      </div>
      <div class="register-child">
          <empty-child-card></empty-child-card>
      </div>
  </section>
</template>

<script>
import ChildCard2 from '@/components/ChildCard2.vue';
import ChildService from '@/services/ChildService.js';
import EmptyChildCard from './emptyChildCard.vue';
export default {
    name: "children-list",
    computed: {
        // children() {
        //     return this.$store.state.children;
        // }
    },
    components: {
        ChildCard2,
        EmptyChildCard,
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
    
  }

}
</script>

<style>
.title{
      height: 45px;
    display: grid;
    justify-content: center;
    align-content: center;
    margin-left: 275px;
    margin-right: 275px;
    background-color: whitesmoke;
    border: 2px solid black;
    border-radius: 0.5rem;
}
.child {
  display: grid;
  justify-content: center;
}
.register-child {
  display: grid;
  justify-content: center;
}
form {
  display: grid;
  justify-content: center;
  margin: 10px;
  padding: 10px;
  border: 1px solid black;
  border-radius: 0.5rem;
  background-color: blueviolet;
}
</style>