<template>
    <div class="output-window">
      <pre>{{ output }}</pre>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const output = ref('Output will be displayed here...');
  </script>
  
  <style scoped>
  .output-window {
    background-color: #1e1e1e;
    color: #e0e0e0;
    padding: 1rem;
    border: 1px solid #333;
    border-radius: 4px;
    margin-top: 1rem;
    min-height: 200px;
    overflow: auto;
  }
  </style>
  