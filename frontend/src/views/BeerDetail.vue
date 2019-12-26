<template>
    <div class="beer-detail-page">
        <h1 class="beer-detail-name">{{beer.name}}</h1>

        <button v-if="this.editable && userRole == 'admin' && !this.editMode" v-on:click="toggleEditMode" class="add-review-button">Edit Beer</button>
        <button v-if="!beer.hasOwnProperty('labels') && userRole != 'user'"  v-on:click="deleteBeer" class="add-review-button">Delete Beer</button>
        <form v-if="this.editMode" @submit.prevent="updateBeer">
            <button v-on:click="toggleEditMode">Cancel</button>
            <div class="beerInput">
                <span  class="beerName">Beer Name: </span><input type="text" v-model="editBeer.name" />
            </div>
            <div class="beerInput">
                <span class="BeerABV">ABV: </span><input type="text" v-model="editBeer.abv">
            </div>
            <div class="beerInput">
                <span class="beerIBU">IBU: </span><input type="text" v-model="editBeer.ibu">
            </div>
            <div class="beerInput">
                <span class="beerType">Type: </span><input type="text" v-model="editBeer.type">
            </div>
            <div class="beerInput">
                <span class="beerDescription">Description: </span><textarea name="description" cols="60" rows="10" v-model="editBeer.description"></textarea>
            </div>
            <button class="update-beer-button" type="submit" >Update Beer</button>
        </form>


        <div class="beer-info" v-if="!this.editMode">
            <div class="beer-detail-table">
            <img v-if="beer.hasOwnProperty('labels')" :src="beer.labels.medium" class="beer-detail-img"/>
            <img v-if="!beer.hasOwnProperty('labels')" :src="beer.image" class="beer-detail-img">
            <div class="beer-detail-card">
            <p>ABV:  {{beer.abv}} </p>
            <p>IBU:  {{beer.ibu}} </p>
            <p>Rating:  {{rating}} </p>
            <p v-if="beer.hasOwnProperty('description')" >Description:  {{beer.description}} </p>
            </div>
            </div>
            <p class="beer-detail-review"><router-link v-bind:to="{ name: 'reviewBeer', params: { beerId: beerId, beerName: beer.name} }" class="add-review-button">Add a Review</router-link></p>
            
        </div>
        

        <div v-for="review in reviews" v-bind:key="review.id" class="review-card">
            <h2>{{review.username}}</h2>
            <img id="full-beer-img" v-for="n in review.rating" v-bind:key="n" src='@/assets/full-beer.png' />
            <img id="empty-beer-img" v-for="n in (5-review.rating)" v-bind:key="n" src='@/assets/empty-beer.jpg' />
            <div>
            <p id="review-text">{{review.text}}</p>
            </div>
        </div> 
    </div>
</template>

<script>
import auth from '@/auth.js'


export default {
    
    name: 'beer-detail',
    data() {
        return {
            userRole: auth.getUser().rol,
            editable: this.$route.params.editable,
            editMode: false,
            beer: this.$route.params.beer,
            beerId: typeof(this.$route.params.beer.id) == 'string' ? this.$route.params.beer.id : this.$route.params.beer.id.toString(),
            reviews: [],
            editBeer: {
                name: this.$route.params.beer.name,
                abv: this.$route.params.beer.abv,
                ibu: this.$route.params.beer.ibu,
                type: this.$route.params.beer.type,
                description: this.$route.params.beer.description
            }
        }
    },
    methods: {
        toggleEditMode() {
            if (this.editMode){
                this.editBeer.name = this.$route.params.beer.name;
                this.editBeer.abv = this.$route.params.beer.abv;
                this.editBeer.ibu = this.$route.params.beer.ibu;
                this.editBeer.type = this.$route.params.beer.type;
                this.editBeer.description = this.$route.params.beer.description;
            }
            this.editMode = !this.editMode
        },
        updateBeer() {
            fetch(`http://localhost:8080/AuthenticationApplication/api/dbBeers/${this.beerId}`,{
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                    Authorization: 'Bearer ' + auth.getToken(),
                },
                body: JSON.stringify(this.editBeer)
            })
            .then((response) => {
                if( response.ok ) {
                    this.$router.push({name: 'beers'});
                }
            })
            .catch((err) => console.error(err));
        },
        deleteBeer(){
            fetch(`http://localhost:8080/AuthenticationApplication/api/deleteBeer/${this.beerId}`, {
            method: 'DELETE',
            headers: {
            Authorization: 'Bearer ' + auth.getToken(),
            },
            credentials: 'same-origin',
            })
            .then((response) => {
            if(response.ok) {
                    this.$router.push({name: 'deletion', params: {confirmationObject: 'Deleted'}});
                    }
            
            })
            .catch((err) => console.error(err));
        }
        
    },
    computed: {
        rating: function () {
            if (this.reviews.length > 0){
                let ratingSum = this.reviews.reduce((total, review) => {
                    return total += review.rating;
                },0);
                return (ratingSum/this.reviews.length).toFixed(2);
            } else {
                return 'Be the first to review!!!';
            }
        }
    },
    created() {
        fetch(`http://localhost:8080/AuthenticationApplication/api/getReviewsByBeerId/${this.beerId}`,
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
                this.reviews = theData;       
            })
            .catch((err) => {
                console.log(err);
            })
    }
}
</script>

<style scoped>
    #full-beer-img {
        width: 25px;
        height: auto;
    }
    #empty-beer-img {
        width: 25px;
        height: auto;
    }
    #review-text {
        font-size: 30px;
        
    }
    .beer-detail-page{
        background: dimgray;
    }
    .beer-detail-card{
        font-size: large;
        background-color: rgb(82, 82, 83);
        margin: 0px 10px 10px 0px;
        padding: 10px 10px 10px 10px;
        border-radius: 5px;
        border: 1px solid;
        box-shadow: 5px 10px rgb(49, 48, 48);
        
    }
    .delete-beer-button{
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
    margin-right: 80px;
    margin-top: 10px;
    margin-bottom: 10px;
    
    }
    .add-review-button{
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
    margin-right: 30px;
    margin-left: 30px;
    margin-bottom:30px;
    }
    .beer-detail-table{
        display: flex;
    }
    .beer-detail-img{
        margin-left:  10px;
        margin-right: 10px;
    }
    .beer-detail-name{
        color: rgb(240, 201, 73);
    }
    .beer-detail-review{
        margin-top: 25px;
    }
    .review-card{
        margin:50px 50px;
        border-bottom: 1px solid  rgb(240, 201, 73);
    }
    p{
        color: white;
    }
    body{
        background: dimgray
    }
</style>