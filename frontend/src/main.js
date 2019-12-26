import Vue from 'vue'
import App from './App.vue'
import router from './router'
import * as VueGoogleMaps from "vue2-google-maps";
import * as Cloudinary from 'cloudinary-vue';

Vue.config.productionTip = false

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyDPZxNko3eJyHEWJwSFaUIaR7HRkYN1Pws",
    libraries: "places"
  }
});

Vue.use(Cloudinary, {
  configuration: {
    cloudName: 'brillhart'
  }
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
