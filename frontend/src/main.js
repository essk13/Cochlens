import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import { Quasar } from 'quasar'
import quasarUserOptions from './quasar-user-options'

loadFonts()

createApp(App).use(Quasar, quasarUserOptions)
  .use(router)
  .use(store)
  .use(vuetify)
  .mount('#app')
