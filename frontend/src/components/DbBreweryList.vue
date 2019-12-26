<template>
    <div class="db-breweries">
        <router-link v-bind:key="breweries"></router-link>          
        <div>
            <div class="add-brewery">
                <a href="#" class="add-brewery-button" v-on:click="addBrewery" v-if="canAddBrewery">
                    <i class="far fa-address-card"></i> Add Brewery
                </a>
            </div>
        <new-brewery v-if="showAddNewBrewery" v-on:showBreweries="showBreweries" :apiURL="API_URL" :breweryID="breweryID"/>
        <!-- <brewery  :apiURL="API_URL" v-on:addBrewery="addBrewery" v-on:editBrewery="editBrewery($event)"/> -->
        <div v-if="!showAddNewBrewery" class="brewery-container">
            <ul class="brewery-list" v-for="aBrewery in theBreweries" v-bind:key="aBrewery.name">
            <div class="brewery-list-item">
                <router-link v-bind:to="{ name: 'brewery', params: { brewery: aBrewery } }" style="text-decoration: none"><h2>{{aBrewery.name}} </h2></router-link>
                <img :src="aBrewery.image" contain   height="75px" />
            </div>
            </ul>
        </div>
        </div>
        
    </div>
    <!-- id: aBrewery.id, allBreweries: theBreweries -->
</template>

<script>
import auth from "@/auth.js"
import NewBrewery from "@/views/NewBrewery.vue";
// import Brewery from "@/views/Brewery.vue";

export default {
    name: "DbBreweryList",
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
        }
    }, 
    created() {
        fetch('http://localhost:8080/AuthenticationApplication/api/getDbBreweries', {
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
        this.theBreweries = data;
      })
      .catch((err) => console.error(err));
  },
}
</script>

<style scoped>
    .brewery-list-item {
        transition: transform 1s;
        border-radius: 5px;
    }
    .add-brewery-button {
        /* float: right; */
    }
    h2 {
       /*  opacity: 0;
        position: absolute;
        transition: 1s; */
        font-weight: bold;
        padding-top: 5px 5px;
        text-decoration: none;
        
    }
    .brewery-list-item:hover {
        transform: scale(1.1);
        opacity: 1;
    }
    h2:hover {
        opacity: 1;
    }
    router-link h2 {
        
    }
</style>