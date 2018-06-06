import Vue from 'vue'
import Router from 'vue-router'
import bookingConfigure from '@/components/bookingConfigure'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'bookingConfigure',
      component: bookingConfigure
    }
  ]
})
