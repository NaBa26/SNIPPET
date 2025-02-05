<template>
    <footer class="footer">
      <div class="container">
        <div class="text-center py-2 fw-bold">
          © {{ currentYear }} snippet.com, Inc. or its affiliates
        </div>
      </div>
    </footer>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  const currentYear = ref(new Date().getFullYear());
  </script>
  
  <style scoped>
  .footer {
    background-color: #1e1e1e;
    padding: 1rem 0;
    text-align: center;
    margin-top: 2rem;
  }
  
  .footer .text-center {
    font-size: 0.9rem;
    font-weight: 500;
    color: #fa8324;
  }
  </style>
  