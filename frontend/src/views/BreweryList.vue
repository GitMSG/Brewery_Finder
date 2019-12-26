<template>
    <div class="breweries" :style="image">
        <h2 class="brewery-tag">Breweries</h2>
        <db-brewery-list v-bind:breweries="dbBreweries"/>
        <brewery-list v-bind:breweries="breweries"/>
    </div>  
</template>

<script>
import BreweryList from "@/components/BreweryList.vue";
import DbBreweryList from "@/components/DbBreweryList.vue";

export default {
    components: {
    BreweryList,
    DbBreweryList
  },
  data() {
      return{
          breweries: [],
          dbBreweries: [], 
          image: {
              background: `url(${require('@/assets/breweryBack2.jpeg')})`
          }
      }
  }
}
</script>

<style>
.breweries {
    padding: 0px 0px 0px 50px;
}
.brewery-list{
    float: left;
    width: 30%;
    padding: 10px 10px;
    /* padding: 0px 30px 50px 0px;
    max-width: 1050px;
    margin: 0 auto;
    border-top: 1px solid #d8d8d8;
    display: -webkit-box;
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: start;
    align-items: flex-start;
    position: relative;
     */
}
ul a{
    color: rgb(240, 201, 73);
    text-decoration: none;
}
.brewery-list:hover{
    background-color: rgb(140, 140, 140);
    
}
.add-brewery-button{
    font-size: large;
    
}
.add-brewery{
    text-align: center;
    margin-bottom: 10px;
    
}
.add-brewery-button{
    background-color: rgb(240, 201, 73);
    border: none;
    color: black;
    padding: 10px 40px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 20px;
    border-radius: 8px;
    transition-duration: 0.4s;
    border: 2px solid rgb(240, 201, 73);
    
}
.add-brewery-button:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
   
}
.brewery-tag{
    font-size: 50px;
    margin-top: 1px;
    font-family: fantasy;
    color: rgb(240, 201, 73);
}
/* .brewery-container{
    margin: 0 -5px;
} */
.breweries:after{
  content: "";
  display: table;
  clear: both;
}
.breweries{
    background:dimgray;
    padding-bottom: 100%;
}
.brewery-list-item{
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  padding: 16px;
  text-align: center;
  background-color: rgb(75, 75, 75);
}
</style>