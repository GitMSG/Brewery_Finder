<template>
    <div class="newBeer">
        <div class="header">
            <h2>Add A Beer!</h2>
            <a href="#" class = "back">
                <i></i>Return to Breweries
            </a>
        </div>
        <div class="beerInput">
            <span class="beerName">Beer Name: </span><input type="text" v-model="beer.name">
        </div>
        <div class="beerInput">
            <span class="BeerABV">ABV: </span><input type="text" v-model="beer.abv">
        </div>
        <div class="beerInput">
            <span class="beerIBU">IBU: </span><input type="text" v-model="beer.ibu">
        </div>
        <div class="beerInput">
            <span class="beerType">Type: </span><input type="text" v-model="beer.type">
        </div>
        <!-- <div class="beerInput">         ***  Thinking maybe we don't need this to add a beer, just rate when creating a review ***
            <!-- <span class="beerRating">Rating: </span><input type="text" v-model="beer.rating">
        </div> -->
        <div class="beerInput">
            <span class="beerDescription">Description: </span><textarea name="description" cols="60" rows="10" v-model="beer.description"></textarea>
        </div>
         <div class="beerInput">
            <vue-dropzone id="dropzone" :options="dropzoneOptions" v-on:vdropzone-sending="addFormData" @vdropzone-success="getSuccess" v-model="beer.image"></vue-dropzone>
        </div>
        <button v-on:click='createBeer'>Save</button>  
       
    </div>
</template>

<script>
import auth from "@/auth.js"
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'

export default {
    name: 'add-beer',
    props:{
        breweryId: Number
    },
     components: {
        vueDropzone: vue2Dropzone
    },
    data(){
        return {
            API_URL: 'http://localhost:8080/AuthenticationApplication/api',
            beer:{
                brewery_id: this.breweryId,
                name: '',
                abv: '',
                ibu: '',
                type: '',
                rating: '',
                description: '',
                image:''

            },
            dropzoneOptions: {
                url: 'https://api.cloudinary.com/v1_1/brillhart/image/upload',
                thumbnailWidth: 150,
                maxFilesize: 2.0,
                acceptedFiles: ".jpg, .jpeg, .png, .gif"
            },
            post: {
                image: ''
            }
           
        }
    },
    methods:{
         
        createBeer() {
            fetch(`${this.API_URL}/addBeer`, {
                method: 'POST',
                headers: {
                     'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),
                },
                    
                    body: JSON.stringify(this.beer)
                    
            })
            .then((response) => {
                if(response.ok) {
                   this.$router.push({name: 'confirmation', params: {confirmationObject: 'Beer'}});
                }
            })
           
            .catch((err) => console.error(err));
        },
        addFormData(file, xhr, formData) {
            formData.append("api_key", "652428861445774");
            formData.append("upload_preset", "brillhartpreset"); // my upload preset
            formData.append("timestamp", (Date.now() / 1000) | 0);
            formData.append("tags", "vue-app");
        },
        getSuccess(file, response) {
            
            this.beer.image = response.secure_url;
        }
        
    },
    created() {
      
     }
        }

</script>

<style scoped>
    .newBeer {
        margin-top: 30px;
    }
    .beerInput {
        color: white;
    }
    

</style>