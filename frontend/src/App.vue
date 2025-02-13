<script setup>
import router from './router';
</script>

<template>

  <main>
    <router-view name="main" />
    <router-view name="notfound" />
  </main>

  <footer>
    <router-view name="footer" />
  </footer>
</template>

<style>
body {
  font-family: "SF Mono", Menlo, Monaco, Consolas, "Courier New", monospace !important;
  background-color: #000000;
}
</style>
