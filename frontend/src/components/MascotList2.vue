<template>
  <div>
    <div class="mascot-list">
        <div class="mascot-card" v-for="mascot in $store.state.mascots" v-bind:key="mascot.id" v-bind:mascot="mascot">
        <mascot :mascot="mascot"></mascot>
        <button @click.prevent="purchaseMascot(mascot)">BUY</button>
        </div>
    </div>
  </div>
</template>

<script>
import Mascot from './mascot.vue';
import ChildService from '../services/ChildService.js';

export default {
    components: {
        Mascot,
    },
    methods: {
        purchaseMascot(mascot) {
            console.log("Mascot ID " + mascot.id);
            console.log("Mascot Name " + mascot.name);
            console.log("Mascot Price " + mascot.price);
            ChildService.purchaseMascot(this.$store.state.child.id, mascot)
                .then((response) => {
                    console.log("Purchase Mascot Response " + response.data);
                    if (response.status == 201) {
                        this.$store.commit("SET_CHILD_MASCOT", mascot);
                        ChildService.updateChild(this.$store.state.child.id, this.$store.state.child)
                        .then((response) => {
                            if (response.status == 200) {
                                this.$router.push(`/sub/${this.$store.state.child.id}`);
                            }
                        }).catch((err) => {
                            const response = err.response;
                            console.log(" **ERROR** Update Child Catch Response:" + response);
                        })
                    }
                }).catch((err) => {
                    const response = err.response;
                    console.log(" **ERROR** Purch Mascot CATCH Response:" + response);
                });
            
        }
    
    }  

}
</script>

<style scoped>
.mascot-card {
    display: flex;
    align-items: center;
}
button{
    justify-self: center;
    height: 40px;
    width: 80px;
    border-radius: 0.5rem;
}
</style>