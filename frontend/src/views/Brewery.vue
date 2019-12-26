<template>
    <div class="brewery-list-page" :style="image">
        <div class="header">
           
        </div>
            <h1 class="brewery-name">{{brewery.name}}</h1>
        <div class="brewery-detail">
           <div class="brewery-info">
              <img v-if="brewery.hasOwnProperty('images')" :src="brewery.images.medium" /> 
              <img v-if="!brewery.hasOwnProperty('images')" :src="brewery.image" /> 
             <div class="contact-card">
                <p >{{brewery.hours_of_operation}}</p>
                <p >{{brewery.phoneNumber}}</p>
                <p >{{brewery.address}}</p>
                <p >{{brewery.city}}</p>
                <a :href="brewery.website" target="_blank" >Website</a>
                <p>{{brewery.email}}</p>
                <p>{{locationObj.phone}}</p>
                <p>{{locationObj.streetAddress}}</p>
                <p>{{locationObj.locality}} {{locationObj.region}} {{locationObj.postalCode}}</p>
                
             </div>
            </div>
            <div class="brewery-desc">
            <p class="brewery-text">{{brewery.description}} </p>
            <button href="#" class="add-beer-button" v-on:click="addBeer" v-if="this.role != 'user'"> Add A Beer
            </button >
                <add-beer v-if='showAddBeer' :breweryId="breweryId" />
            </div>
        </div>
        <div>
          <ul class="brewery-beer-table">
               <li v-for="theBeer in dbBeerArr" v-bind:key="theBeer.id" class="brewery-beerlist">
                 <div class="brewery-beer-card">
                    <router-link v-bind:to="{name: 'beerDetail', params: {beer: theBeer}}"><h3 >{{theBeer.name}}</h3></router-link>
                     <img :src="theBeer.image" height="75" /> 
                     </div> 
                </li>
           </ul>
            <ul class="brewery-beer-table">
                <li v-for="aBeer in beerArr" v-bind:key="aBeer.id" class="brewery-beer-list">
                  <div class="brewery-beer-card">
                    <router-link v-bind:to="{name: 'beerDetail', params: {beer: aBeer}}"><h3 class="brewery-beer-name">{{aBeer.name}}</h3></router-link>
                     <img :src="aBeer.labels.icon"/>  
                  </div>
                </li>
            </ul>
        </div>
        <google-map :center = center />
    </div>
</template>

<script>
import auth from "@/auth.js"
import AddBeer from '@/components/AddBeer.vue'
import GoogleMap from '@/components/GoogleMap.vue'

export default {
    components:{
         AddBeer,
         GoogleMap
    },
    props: {
      latitude: Number,
      longitude: Number
    },
    name: 'brewery',
    data() {
        return {
            role: auth.getUser().rol,
            showAddBeer: false,
            brewery: this.$route.params.brewery,
            breweryId: this.$route.params.brewery.id,
            beerArr: [],
            dbBeerArr:[],
            locationObj: [],
            image: {
              backgroundImage: `url(${require('@/assets/anotherBackground.jpeg')})`
            },
            center: {
              lat: 0,
              lng: 0
            }
        }
    },
    methods:{
         addBeer() {
            this.showAddBeer = true;
        }/* ,
        hideBeerForm(){
            this.showAddBeer = false;
        } */
    },
    created() {
      
       fetch(`http://localhost:8080/AuthenticationApplication/api/breweryBeers/${this.breweryId}`, {
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
        this.beerArr = data.data.filter( (aBeer)=>{
        return aBeer.hasOwnProperty('labels')
        })
    })
      .catch((err) => {
          console.error(err)
      })
      
      fetch(`http://localhost:8080/AuthenticationApplication/api/breweryBeerList/${this.breweryId}`, {
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
        this.dbBeerArr  = data
        
    })
      .catch((err) => {
          console.error(err)
      })
       fetch(`http://localhost:8080/AuthenticationApplication/api/breweryLocation/${this.breweryId}`, {
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
        this.locationObj  = data.data[0];
        this.center.lat = data.data[0].latitude;
        this.center.lng = data.data[0].longitude;
    })
      .catch((err) => {
          console.error(err)
      })
    }
}
</script>

<style  scoped>
.brewery-list-page{
  background: dimgray;
}
.brewery-beer-card{
  box-shadow: 5px 10px rgb(49, 48, 48);
  padding: 16px;
  text-align: center;
  background-color: rgb(75, 75, 75);
  border-radius: 5px;
}
.brewery-beer-card:hover{
  box-shadow: 5px 10px rgb(49, 48, 48);
}
.brewery-beer-list{
  float: left;
  width: 30%;
  padding: 10px 10px;
  transition: transform 1s;
  overflow: auto;
}
.brewery-beer-list:hover{
  transform: scale(1.1);
  opacity: 1;
}
.brewery-beer-table{
  margin: 0 -5px;
}
.brewery-beer-table:after{
  content: "";
  display: table;
  clear: both;
}
.brewery-beer-name{
  font-weight: bold;
  padding-top: 5px 5px;
}
.brewery-beer-name:hover{
  opacity: 1;
}
.brewery-detail{
  display: flex;
}
.brewery-name{
  font-size: 50px;
  margin-top: 45px;
  font-family: fantasy;
  color: rgb(240, 201, 73);
  padding-top: 5px;
  text-align: center;
}
.brewery-info{
  padding-left: 45px;
  padding-right: 20px;
}
.brewery-text{

  margin-top: 0;
  font-size: x-large;
  background-color: rgb(82, 82, 83);
  margin: 0px 80px 10px 50px;
  padding: 10px 10px 10px 10px;
  border-radius: 5px;
  border: 1px solid black;
  box-shadow: 5px 10px rgb(49, 48, 48);
}
.contact-card{
  font-size: large;
  background-color: rgb(82, 82, 83);
  margin: 10px 0px 10px 0px;
  padding: 10px 10px 10px 10px;
  border-radius: 5px;
  border: 1px solid;
  box-shadow: 5px 10px rgb(49, 48, 48);
}
.add-beer-button{
    background-color: rgb(240, 201, 73);
    border: none;
    color: black;
    padding: 10px 40px;
    text-align: center;
    text-decoration: none;
    font-size: 20px;
    border-radius: 8px;
    transition-duration: 0.4s;
    border: 2px solid rgb(240, 201, 73);  
    float: right;
    margin-right: 80px;
    margin-top: 10px;
    margin-bottom: 10px;

}
.add-beer-button:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
   
}
p {
  color: white;
}

</style>