<template>
  <div id="app">
    <nav>
      <router-link to="/">Lista Produktów</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
}

nav {
  background: #42b983;
  padding: 10px;
}

nav a {
  color: white;
  text-decoration: none;
  font-size: 18px;
  margin: 0 10px;
}
</style>
