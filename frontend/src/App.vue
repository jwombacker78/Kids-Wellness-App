<template>
  <div id="app">
    <div id="nav">
      <div class="link" id="home">
      <router-link v-bind:to="{ name: 'home' }">Home&nbsp;|&nbsp;</router-link>
      </div>
      <div class="link" id="login">
      <router-link v-bind:to="{ name: 'login' }" v-if="$store.state.token == ''">Login&nbsp;|&nbsp;</router-link>
      </div>
      <div class="link" id="my-profile">
      <router-link v-if="$store.state.token != '' && this.$store.state.user.authorities[0].name != 'ROLE_USER'" :to="{ name: 'child-summary'}">My Profile&nbsp;|&nbsp;</router-link>
      </div>
      <div class="link" id="shop">
      <router-link :to="{ name: 'shop', params: { id: this.$store.state.user.id} }" v-if="$store.state.token != '' && this.$store.state.user.authorities[0].name != 'ROLE_USER'">Shop&nbsp;|&nbsp;</router-link>
      </div>
      <div class="link" id="logout">
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>
    </div>
    <div id="title-container">
      <div id="content">
        <h1>Kid's Wellness App</h1>
      </div>
    </div>
    <router-view />
  </div>
</template>

<style scoped>

#nav{
  display: flex;
  /* padding: 5px;
  margin: 5px; */
  border: solid lightblue;
  background-color: whitesmoke;
}
#app{
  height: 100%;
  background-color: #4A478B;
  margin: 0px;
}
#content {
  display: flex;
  justify-content: center;

}
h1 {
  color: lightblue;
  text-shadow: 0 0 1px whitesmoke;
}

</style>