<template>
    <div class="beer-list">
        <ul class="beer-table">
            <li v-for="aBeer in beerArr" v-bind:key="aBeer.id" class="beer-list-item">
                <div class="beer-card">
                    <router-link v-bind:to="{name: 'beerDetail', params: {beer: aBeer, editable: false}}"><h2>{{aBeer.name}}</h2>
                    <img :src="aBeer.labels.icon" height="75" width="75"/></router-link>
                </div>
            </li> 
             
        </ul>

    </div>
</template>

<script>
 
 import auth from '@/auth.js'

export default {
    name: 'BeerList',
    props: {
        theBeers: Array
    },
    data(){
        return{
            beerArr: []
        }
    },


     created() {
      fetch('http://localhost:8080/AuthenticationApplication/api/beers',
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
       this.beerArr = theData.data.filter( (aBeer)=>{
        return aBeer.hasOwnProperty('labels')
       })     
       
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