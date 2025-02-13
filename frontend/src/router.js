import { createRouter, createWebHistory } from 'vue-router';

import Footer from './components/Footer.vue';
import NotFound from './components/NotFound.vue';
import Editor from './components/Editor.vue';
import Main from './components/Main.vue';

const routes = [
    { path: '/', redirect: '/home' },
    {
      path: '/home',
      components: {
        main: Main,
        editor: Editor,
        footer: Footer,
      },
    },
  {
    path: '/404',
    components: {
        notfound:NotFound
    }
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404',
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

