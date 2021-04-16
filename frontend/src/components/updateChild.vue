<template>
        <form @submit="updateChild()">
        <input 
          type="number"
          class="stepsToAdd"
          v-model="stepsToAdd"
          placeholder="Steps to Add"
        />
        <input 
          type="number"
          class="activityMinutesToAdd"
          v-model="activityMinutesToAdd"
          placeholder="Activity Minutes to Add"
        />
        <button type="submit">Update Steps/ActivityTime</button>
      </form>
</template>

<script>
import ChildService from "@/services/ChildService.js";

export default {
    name: "update-child",
    // props: ['steps', 'activityMinutes'],
    props: ['child'],
    data() {
        return {
            stepsToAdd: '',
            activityMinutesToAdd: '',
            //child: this.$store.state.child
            //currentChild: this.$store.state.currentChild
        };
    },
    methods: {
    updateChild() {
     if (parseInt(this.stepsToAdd) > 0){
        this.child.steps += parseInt(this.stepsToAdd);
        this.child.carrotBalance = (this.child.steps / 10);
        } else {
        this.child.carrotBalance += 0;
      }
      if (parseInt(this.activityMinutesToAdd) > 0) {
        this.child.activityMinutes += parseInt(this.activityMinutesToAdd);
        this.child.playtimeBalance = this.child.activityMinutes;
        } else {
          this.child.playtimeBalance += 0;
      }
      ChildService.updateChild(this.child.id, this.child)
      .then((response) => {
        if (response == 200) {
          this.$store.commit("UPDATE_CHILD", this.child);
        }
      })
      .catch((err) => {
        const response = err.response;
        if (response.status == 500) {
          this.$store.commit("UPDATE_CHILD", null);
        }
      });
    }  
  }
}
</script>

<style scoped>
input {
  margin-bottom: 5px;
}
button {
  margin-top: 8px;
}
</style>