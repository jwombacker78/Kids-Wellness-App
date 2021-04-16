<template>
  <div class="card">
    <h2 id="name">Name: {{mascot.name}} </h2>
    <h3 id="price">Carrot Price: {{mascot.price}}</h3>
    <img
      v-bind:src="require(`../assets/mascot${mascot.id}.jpg`)"
      alt=""
      width="200"
      height="200"
    />
  </div>
</template>

<script>

export default {
  props: {
    mascot: Object,
  },
  
}
</script>

<style scoped>
.card{
  display: grid;
  justify-content: center;
  border: 1px solid black;
  background-color: rgb(61, 192, 240);
  border-radius: 0.5rem;
  margin: 10px;
  width: 25vw;
}
h2, h3{
  display: grid;
  justify-content: center;
  background-color: whitesmoke;
  border-radius: 0.5rem;
  border: 2px solid black;
  width: 250px;
}
img{
  padding-bottom: 10px;
}
</style>