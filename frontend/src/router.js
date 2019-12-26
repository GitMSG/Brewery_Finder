import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import BreweryList from './views/BreweryList.vue'
import ConfirmationPage from './views/ConfirmationPage.vue'
import DeletionPage from './views/DeletionPage.vue'

import BeerList from './views/BeerList.vue'

import Brewery from './views/Brewery.vue'

import ReviewBeer from './views/ReviewBeer.vue'

import EditUserRoles from './views/EditUserRoles.vue'

import BeerDetail from './views/BeerDetail.vue'


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
     {
       path: "/breweries",
       name: "breweries",
       component: BreweryList,
       meta: {
         requiresAuth: false
       }
     },
     {

      path: "/beers",
      name: "beers",
      component: BeerList,
      meta: {
        requiresAuth: false
    }
  },
    {
       path: "/brewery",
       name: "brewery",
       component: Brewery,
       meta: {
         requiresAuth: false
       }
     },
     {
       path: "/confirmation",
       name: "confirmation",
       component: ConfirmationPage,
       meta: {
         requiresAuth: false
       }
     },
     {
      path: "/deletion",
      name: "deletion",
      component: DeletionPage,
      meta: {
        requiresAuth: false
      }
    },
     {
       path: "/reviewBeer",
       name: "reviewBeer",
       component: ReviewBeer,
       meta: {
         requiresAuth: false
       }
     },
     {
       path: "/editUserRoles",
       name: "editUserRoles",
       component: EditUserRoles,
       meta: {
         requiresAuth: true
       }
     },
     {
       path: "/beerDetail",
       name: "beerDetail",
       component: BeerDetail,
       meta: {
         requiresAuth: true
       }
     }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
