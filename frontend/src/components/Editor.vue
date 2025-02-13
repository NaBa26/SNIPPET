<template>
  <section class="editor-section">
    <div class="controls">
      <button @click="runCode" class="btn run-btn">
        <FontAwesomeIcon :icon="faPlay" /><span class="p-1">RUN</span>
      </button>
      <button @click="formatCode" class="btn format-btn">
        <FontAwesomeIcon :icon="faAlignLeft" /><span class="p-1">FORMAT</span>
      </button>
      <button @click="clearEditor" class="btn clear-btn">
        <FontAwesomeIcon :icon="faTimes" /><span class="p-1">CLEAR WINDOW</span>
      </button>
    </div>
    <div class="editor-output-container">
      <div class="editor-container">
        <MonacoEditor
          v-model:value="code"
          language="java"
          theme="vs-dark"
          :width="600"
          :height="800"
          :options="{
            fontSize: 18,
            wordWrap: 'on',
            automaticLayout: true,
            minimap: { enabled: false },
            scrollbar: { vertical: 'auto', horizontal: 'auto' },
            lineNumbers: 'on',
            lineNumbersMinChars: 3,
          }"
        />
      </div>
      <div class="loading" v-if="loader">
      <div class="output-section">
        <p><strong>Output:</strong></p>
        <pre :style="{ color: outputColor }">{{ output }}</pre>
      </div>
    </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import MonacoEditor from "monaco-editor-vue3";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faPlay, faTimes, faAlignLeft } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

const code = ref(
  `public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
  }`
);

const loader=ref(false);

const output = ref("");
const outputColor = ref("white");

const runCode = async () => {
  try {
    const startTime = performance.now();
    const response = await axios.post("http://localhost:8080/api/execute", {
      code: code.value
    });
    const endTime = performance.now();

    const executionTime = endTime - startTime; 
    output.value=response.data + "\n Time Elapsed: "+executionTime.toFixed(2) +" ms";
  } catch (error) {
    output.value = "Error: " + error.message;
  }
};

const clearEditor = () => {
  code.value = "";
};

const formatCode = () => {
  alert("Not yet implemented");
};
</script>


<style scoped>
.editor-section {
  padding: 1rem;
  background-color: #1e1e1e;
  min-height: 30px;
}

.controls {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.editor-output-container {
  display: flex;
  gap: 20px;
}

.editor-container {
  flex: 1;
  border: 1px solid #333;
  border-radius: 4px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

.output-section {
  width: 600px;
  padding: 1rem;
  background-color: #2e2e2e;
  color: #e0e0e0;
  border-radius: 4px;
  font-family: "Courier New", Courier, monospace;
  font-size: large;
  white-space: pre-wrap;
}

.btn {
  padding: 6px 12px;
  font-size: 10px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: bold;
  transition: 0.2s;
}

.run-btn {
  background-color: #4caf50;
  color: white;
  font-weight: 800;
  font-size: medium;
}

.run-btn:hover {
  background-color: #056209;
}

.format-btn {
  background-color: #2196f3;
  color: white;
  font-weight: 800;
  font-size: medium;
}

.format-btn:hover {
  background-color: #0a5b9c;
}

.clear-btn {
  background-color: #f44336;
  color: white;
  font-weight: 800;
  font-size: medium;
}

.clear-btn:hover {
  background-color: #780806;
}

.loading {
  background-color: yellow;
}
</style>
