<template>
    <div class="all-beers" :style="image">
        <database-beer-list v-bind:dbBeers = "dbBeers"/>
        <beer-list v-bind:theBeers = "theBeers"/> 
    </div>
</template>

<script>
import BeerList from "@/components/BeerList.vue"
import DatabaseBeerList from "@/components/DatabaseBeerList.vue"
export default {
    components: {
        BeerList,
        DatabaseBeerList
    },
    data() {
        return {
            dbBeers:[],
            theBeers: [],
            image: {
              background: `url(${require('@/assets/breweryBack2.jpeg')})`
            }
        }
    }
}
</script>

<style  >
.all-beers{
  background: dimgray;
  padding-bottom: 100%;
}
.db-header{
  font-size: 50px;
  margin-top: 45px;
  font-family: fantasy;
  color: rgb(240, 201, 73);
  padding-top: 5px;
}
.beer-list-item{
  float: left;
  width: 30%;
  padding: 10px 10px;
  transition: transform 1s;
  overflow: auto;
}
.beer-list-item:hover{
  transform: scale(1.1);
  opacity: 1;
}
.beer-name{
  font-weight: bold;
  padding-top: 5px 5px;
}
.beer-name:hover{
  opacity: 1;
}
.beer-card{
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  padding: 16px;
  text-align: center;
  background-color: rgb(75, 75, 75);
}
.beer-card:hover{
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.beer-table{
  margin: 0 -5px;
}
.beer-table:after{
  content: "";
  display: table;
  clear: both;
}

</style>
