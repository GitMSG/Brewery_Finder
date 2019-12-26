<template>
    <div class="api-breweries"> 
        <new-brewery v-if="showAddNewBrewery" v-on:showBreweries="showBreweries" :apiURL="API_URL" :breweryID="breweryID"/>
        <div v-if="!showAddNewBrewery" class="brewery-container">
            <ul class="brewery-list" v-for="aBrewery in theBreweries" v-bind:key="aBrewery.name">
                <router-link v-bind:to="{ name: 'brewery', params: { brewery: aBrewery } }">
                <div class="brewery-list-item">
                    <h2 class="brewery-name" style="text-decoration: none; font-weight: bold;">{{ aBrewery.name }}</h2>
                    
                    <img :src="aBrewery.images.medium"   class="brewery-image"/>       
                </div>
                </router-link>
                
            </ul>
        </div>
    </div>
</template>

<script>
import auth from "@/auth.js"

import NewBrewery from "@/views/NewBrewery.vue";

export default {
    name: "brewery-list",
    components: {
        NewBrewery,
        // Brewery
    },
    props:{
        breweries: Array
    } ,
    data(){
        return{
            API_URL: 'http://localhost:8080/AuthenticationApplication/api',
            showAddNewBrewery: false,
            theBreweries: [],
            breweryID: 0,
            canAddBrewery: (auth.getUser().rol == 'admin')
        }
    },
    methods: {
        addBrewery() {
            this.showAddNewBrewery = true;
        },
        showBreweries() {
            this.showAddNewBrewery = false;
            this.breweryID = 0;
        },
        editBrewery() {
            this.breweryID = aBrewery.id;
            this.showAddNewBrewery = true;
        },
        showName() {
           
        }
    }, 
    created() {
        fetch('http://localhost:8080/AuthenticationApplication/api/breweries', {
        method: 'GET',
        headers: {
        Authorization: 'Bearer ' + auth.getToken(),
      },
      credentials: 'same-origin',
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.theBreweries = data.data.filter( (aBrewery)=>{
        return aBrewery.hasOwnProperty('images')
      })
    })
      .catch((err) => {
          console.error(err)
      })
  }
}
</script>


<style scoped>
    .brewery-list-item {
        transition: transform 1s;
        overflow: auto;
        border-radius: 5px;
    }
    h2 {
       /*  opacity: 0;
        position: absolute;
        transition: 1s; */
        font-weight: bold;
        padding-top: 5px 5px;
    }
    .brewery-list-item:hover {
        transform: scale(1.1);
        opacity: 1;
    }
    h2:hover {
        opacity: 1;
        
    }
   
    

</style>