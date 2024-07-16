import {createApp} from 'vue'
import App from './App.vue'
import router from "./router";
import ElementPlus from 'element-plus'
import '../node_modules/element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import Login from "./views/account/Login.vue";


const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router).use(ElementPlus)
app.component('login', Login)
app.mount('#app')