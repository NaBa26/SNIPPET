<template>
    <div class="app">
      <!-- Intro Section -->
      <section class="intro-section">
        <div class="intro-logo">
          <img src="/public/android-chrome-512x512.png" alt="logo" class="intro-logo-img" />
        </div>
        <div class="intro-text">
          <h1 class="title">SNIPPET</h1>
          <p class="subtitle">A minimalist online java editor and compiler</p>
        </div>
      </section>
  
      <!-- Editor Component -->
      <Editor />
      <OutputWindow />
  
    </div>
  </template>
  
  <script setup>
  import Editor from './Editor.vue';
  import OutputWindow from './OutputWindow.vue';
  </script>
  
  <style scoped>
  .app {
    color: #e0e0e0;
    font-family: 'JetBrains Mono', monospace;
    overflow-x: hidden;
  }
  
  /* Intro Section Styles */
  .intro-section {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 50px 2rem 2rem;
    flex-wrap: wrap;
    text-align: center;
  }
  
  .intro-logo {
    flex: 1;
    /* Slide in animation from left */
    opacity: 0;
    animation: slideInFromLeft 1s ease-out forwards;
  }
  
  .intro-logo-img {
    width: 200px;
    height: 200px;
  }
  
  .intro-text {
    flex: 2;
    /* Slide in animation from right */
    opacity: 0;
    animation: slideInFromRight 1s ease-out forwards;
  }
  
  .title {
    font-size: 3rem;
    margin-bottom: 0.5rem;
    color: #fa8324;
  }
  
  .subtitle {
    font-size: 1.5rem;
    color: #ffffff;
  }
  
  /* Animations */
  @keyframes slideInFromLeft {
    from {
      transform: translateX(-100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
  
  @keyframes slideInFromRight {
    from {
      transform: translateX(100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
  </style>
  