<template>
    <section class="editor-section">
      <div class="controls">
        <button @click="runCode" class="btn run-btn"><FontAwesomeIcon :icon="faPlay" /><span class="p-2">RUN</span></button>
        <button @click="clearEditor" class="btn clear-btn"><FontAwesomeIcon :icon="faTimes" /><span class="p-2">CLEAR WINDOW</span></button>
      </div>
      <div class="editor-container">
        <div ref="editorContainer" class="editor"></div>
      </div>
      <div class="output-section" v-if="output">
        <p><strong>Output:</strong></p>
        <pre>{{ output }}</pre>
      </div>
    </section>
  </template>
  
  <script setup>
  import { ref, onMounted, onBeforeUnmount } from "vue";
  import * as monaco from "monaco-editor";
  import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
  import { faPlay, faTimes } from "@fortawesome/free-solid-svg-icons";
  
  const editorContainer = ref(null);
  let editorInstance = null;
  const output = ref("");
  
  onMounted(() => {
    editorInstance = monaco.editor.create(editorContainer.value, {
      value: `public class Main {
      public static void main(String[] args) {
          System.out.println("Hello, World!");
      }
  }`,
      language: "java",
      theme: "vs-dark",
      automaticLayout: true,
      fontFamily: "'Monaco', 'Menlo', 'Ubuntu Mono', 'Consolas', 'source-code-pro', monospace",
      fontSize: 16,
    });
  });

  const runCode = () => {
    output.value = "Compiling Succesfully!\n\nHello, World!";
  };

  const clearEditor = () => {
    if (editorInstance) {
      editorInstance.setValue("");
    }
  };
  
  onBeforeUnmount(() => {
    if (editorInstance) {
      editorInstance.dispose();
    }
  });
  </script>
  
  <style scoped>
  /* Editor Container */
  .editor-section {
    padding: 2rem;
    background-color: #1e1e1e;
    min-height: 500px;
  }
  
  /* Buttons Container */
  .controls {
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
  }
  
  .btn {
    padding: 8px 16px;
    font-size: 14px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    transition: 0.2s;
  }
  
  .run-btn {
    background-color: #4caf50;
    color: white;
    font-weight: 1000;
    font-size: large;
  }
  
  .run-btn:hover {
    background-color: #056209;
  }

  
  .clear-btn {
    background-color: #f44336;
    color: white;
    font-weight: 1000;
    font-size: large;
  }
  
  .clear-btn:hover {
    background-color: #780806;
  }
  
  /* Editor Styling */
  .editor-container {
    border: 1px solid #333;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  }
  
  .editor {
    height: 500px;
    width: 100%;
    background-color: #1e1e1e;
  }
  
  /* Output Section */
  .output-section {
    margin-top: 1rem;
    padding: 1rem;
    background-color: #2e2e2e;
    color: #e0e0e0;
    border-radius: 4px;
    font-family: "JetBrains Mono", monospace;
    white-space: pre-wrap;
  }
  </style>
  