<template>
  <section class="editor-section">
    <div class="controls">
      <button @click="runCode" class="btn run-btn">
        <FontAwesomeIcon :icon="faPlay" /><span class="p-2">RUN</span>
      </button>
      <button @click="formatCode" class="btn format-btn">
        <FontAwesomeIcon :icon="faAlignLeft" /><span class="p-2">FORMAT</span>
      </button>
      <button @click="clearEditor" class="btn clear-btn">
        <FontAwesomeIcon :icon="faTimes" /><span class="p-2">CLEAR WINDOW</span>
      </button>
    </div>

    <div class="editor-output-container">
      <!-- Editor Panel -->
      <div class="editor-container">
        <MonacoEditor
          v-model:value="code"
          language="java"
          theme="vs-dark"
          :width="600"
          :height="800"
          :options="{
            fontSize: 14,
            wordWrap: 'on',
            automaticLayout: true,
            minimap: { enabled: false },
            scrollbar: { vertical: 'auto', horizontal: 'auto' },
            lineNumbers: 'on',
            lineNumbersMinChars: 3,
          }"
        />
      </div>

      <div class="output-section">
        <div v-if="loader" class="loader-container">
          <div class="loader"></div>
          <p>Loading...</p>
        </div>

        <div v-else>
          <p><strong>Output:</strong></p>
          <pre :style="{ color: outputColor }">{{ output }}</pre>
          <br />
          <br />
          <br />
          <span style="color: white; font-size: large">{{ outputVal }}</span>
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
import Swal from "sweetalert2";

const code = ref(`public class Main {
  public static void main(String[] args) {
      System.out.println("Hello World");
  }
}`);

const loader = ref(false);
const output = ref("");
const outputColor = ref("white");
const outputVal = ref("");

const runCode = async () => {
  try {
    output.value = "";
    outputVal.value = "";
    loader.value = true;
    const startTime = performance.now();

    const response = await axios.post("http://localhost:8080/api/execute", {
      code: code.value,
    });

    loader.value = false;
    const endTime = performance.now();
    const executionTime = (endTime - startTime).toFixed(2);
    output.value = `Time Elapsed: ${executionTime} ms\n\n`;

    if (response.status === 400) {
      outputColor.value = "red";
      output.value += `Compilation Error:\n${response.data.message}`;
      console.error(response.data.message);
      return;
    }

    if (response.status === 500 && response.data.status === "Runtime Error") {
      outputColor.value = "red";
      output.value += `Runtime Error:\n${response.data.message}`;
      console.error(response.data.message);
      return;
    }

    if (response.status === 500 && response.data.status === "Error During Execution") {
      Swal.fire({
        icon: "error",
        title: "Execution Error",
        text: response.data.message,
        confirmButtonText: "OK"
      });
      return;
    }

    if (response.status === 500 && response.data.status === "Internal Server Error") {
      Swal.fire({
        icon: "error",
        title: "Internal Server Error",
        text: response.data.message,
        confirmButtonText: "OK"
      });
      return;
    }

    if (response.data.status === "Error") {
      Swal.fire({
        icon: "error",
        title: "Unknown Error",
        text: response.data.message,
        footer: "Please try again later",
        confirmButtonText: "OK"
      });
      return;
    }

    outputColor.value = "green";
    output.value += `Execution Successful:\n${response.data.output}`;
    console.log(response.data.output);

  } catch (error) {
    loader.value = false;
    outputColor.value = "red";
    Swal.fire({
      icon: "error",
      title: "Server Error",
      text: `Error: ${error.message}`,
      confirmButtonText: "OK"
    });
  }
};

const clearEditor = () => {
  code.value = "";
};

const formatCode = () => {
    Swal.fire({
      icon: "info",
      title: "This functionality hasn't been implemented yet",
      confirmButtonText: "OK",
    });
};

</script>

<style scoped>
.editor-section {
  display: flex;
  flex-direction: column;
  padding: 1rem;
  background-color: #1e1e1e;
  min-height: 100vh;
}

.controls {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
  justify-content: center;
}

.editor-output-container {
  display: flex;
  gap: 20px;
  flex: 1;
  justify-content: center;
}

.editor-container {
  flex: 0.42;
  border: 1px solid #333;
  border-radius: 14px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

.output-section {
  flex: 1;
  padding: 1rem;
  background-color: #2e2e2e;
  color: #e0e0e0;
  border-radius: 4px;
  font-family: "Courier New", Courier, monospace;
  font-size: large;
  white-space: pre-wrap;
  max-width: 600px;
  position: relative;
  border-radius: 14px;
}

.loader-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  color: #f7f7f7;
}

.loader {
  border: 6px solid rgba(255, 255, 255, 0.3);
  border-top: 6px solid #4caf50;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.btn {
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  font-weight: bold;
  transition: 0.3s;
  font-size: large;
}

.run-btn {
  background-color: #4caf50;
  color: white;
}

.run-btn:hover {
  background-color: #056209;
}

.format-btn {
  background-color: #2196f3;
  color: white;
}

.format-btn:hover {
  background-color: #0a5b9c;
}

.clear-btn {
  background-color: #f44336;
  color: white;
}

.clear-btn:hover {
  background-color: #780806;
}
</style>
