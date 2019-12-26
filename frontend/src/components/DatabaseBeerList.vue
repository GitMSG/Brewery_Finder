
<template>
    <div class="beer-list">
        <h2 class="db-header"> Beers</h2>
        <ul class="beer-table">
            <li v-for="aBeer in beerArr" v-bind:key="aBeer.id" class="beer-list-item">
                <div class="beer-card">
                    <h2><router-link v-bind:to="{name: 'beerDetail', params: {beer: aBeer, editable: true}}">{{aBeer.name}}</router-link></h2>
                    <img :src="aBeer.image" height="100px" />
                </div>
                <!-- <p>ABV:  {{aBeer.abv}} </p>
                <p>IBU:  {{aBeer.ibu}} </p>
                <p>Type:  {{aBeer.type}}</p>
                <p>Rating:  {{aBeer.rating}} </p>
                <p>Description:  {{aBeer.description}} </p>
                <p><router-link v-bind:to="{ name: 'reviewBeer', params: { beerId: aBeer.id, beerName: aBeer.name} }">Add a Review</router-link></p>  -->
            </li> 
             
        </ul>

    </div>
</template>
<script>
 import auth from '@/auth.js'

export default {
    name: 'DatabaseBeerList',
    props: {
        dbBeers: Array
    },
    data(){
        return{

            beerArr: []
        }
    },


     created() {
      fetch('http://localhost:8080/AuthenticationApplication/api/dbBeers',
      {
        method: 'GET',
        headers: {
        Authorization: 'Bearer ' + auth.getToken(),
      },
      credentials: 'same-origin',
    })
      
     .then ((response) => {     
         return response.json()
        })  
     .then ((theData) => {   
       this.beerArr = theData;       
       
     })
     .catch((err) => {
       console.log(err);
      })
    }
}
</script>

<style scoped>

ul {
    list-style: none;
}
.beer-card {
    border-radius: 5px;
}
</style>