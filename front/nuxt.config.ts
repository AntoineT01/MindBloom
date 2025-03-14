export default defineNuxtConfig({
  modules: [
    '@pinia/nuxt',
    '@nuxt/content',
    '@nuxtjs/tailwindcss',
    '@nuxt/image'
  ],
  css: ['~/assets/css/tailwind.css'],
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },

  build: {
    transpile: ['pinia']
  },

  pinia: {
    autoImports: ['defineStore', 'storeToRefs']
  },

  imports: {
    dirs: ['stores']
  },

  vite: {
    optimizeDeps: {
      include: ['pinia']
    }
  },
  app: {
    head: {
      title: 'MindBloom', // Ceci sera le titre par défaut
      titleTemplate: '%s - TUX', // Ceci permet d'ajouter un suffixe à chaque titre de page
      meta: [
        { name: 'description', content: 'NDI - 2024 - TUX' }
      ],
      link: [
        { rel: 'icon', type: 'image/x-icon', href: '/favicon.png' },
        {
          rel: 'stylesheet',
          href: 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css'
        }
      ],
      script: [
        {
          innerHTML: `
            (function() {
              const theme = localStorage.getItem('theme') || (window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light');
              document.documentElement.classList.toggle('dark', theme === 'dark');
            })();
          `,
          type: 'text/javascript',
        },
      ],
    }
  },
  components: {
    global: true,
    dirs: ['~/components']
  },
  nitro: {
    devProxy: {
      '/api/': {
        target: 'https://mindbloom-4ibi.onrender.com',
        changeOrigin: true,
        autoRewrite: true
      }
    }
  },

  compatibilityDate: '2024-09-24'
})