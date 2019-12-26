<template>
    <div class="newBrewery">
        <div class="header">
            <a href="#" class = "back" v-on:click="backToBreweries()">
                <i></i>Return to Breweries
            </a>
        </div>
            <h2 class="greeting">Add your Brewery!</h2>
    <form class="new-brewery-form" @submit.prevent="saveBrewery">
        <div class="brewery-form">
            <div class="row">
                <div class="column-one">
                    <div class="breweryInput">
                        <span class="label">Brewery Name: </span><input type="text" v-model="brewery.name">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Address: </span><input type="text" v-model="brewery.address">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Phone: </span><input type="text" v-model="brewery.phoneNumber">
                    </div>
                    <div class="breweryInput">
                        <span class="label">City: </span><input type="text" v-model="brewery.city">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Zip Code: </span><input type="text" v-model="brewery.zipcode">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Open From: </span><input type="text" v-model="brewery.hoursOfOperation">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Website: </span><input type="text" v-model="brewery.website">
                    </div>
                    <div class="breweryInput">
                        <span class="label">Email: </span><input type="text" v-model="brewery.email">
                    </div>
                </div>
                <div class="column-two">
                    <div class="breweryInput">
                        <span class="label">Brewery Description: </span><textarea name="description" cols="60" rows="10" v-model="brewery.description"></textarea>
                    </div>
                    <div class="breweryInput">
                        <span class="label">Picture: </span><vue-dropzone id="dropzone" :options="dropzoneOptions" v-on:vdropzone-sending="addFormData" @vdropzone-success="getSuccess" v-model="brewery.image"></vue-dropzone>
                    </div>
                </div>
            </div>
        </div>
        <button class="submit-brewery-button" type="submit" :disabled="!isValidForm" >Add Brewery</button>
    </form>
    </div>
</template>

<script>
import auth from '@/auth.js'
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'

export default {
    props: {
        apiURL: String,
        breweryID: Number
    },
    components: {
        vueDropzone: vue2Dropzone
    },
    data() {
        return {
            brewery: {
                name: '',
                phoneNumber: '',
                address: '',
                city: '',
                zipcode: '',
                hoursOfOperation: '',
                website: '',
                email: '',
                description: '',
                image: ''
            },
            dropzoneOptions: {
                url: 'https://api.cloudinary.com/v1_1/brillhart/image/upload' /*this came with dropzone example --> 'https://httpbin.org/post'*/,
                thumbnailWidth: 150,
                maxFilesize: 2.0,
                acceptedFiles: ".jpg, .jpeg, .png, .gif"
            },
            post: {
                image: '',
                caption: ''
            }
        }
    },
    methods: {
        backToBreweries() {
            this.$emit('showBreweries')
        },
        saveBrewery() {
            this.breweryID === 0 ? this.createBrewery() : this.updateBrewery();
        },
        createBrewery() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/newBrewery`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.brewery),
                })
                .then((response) => {
                    if(response.ok) {
                        console.log(response)
                        this.$router.push({name: 'confirmation', params: {confirmationObject: 'Brewery'}});
                    }
                    //return response.json();
                })
                .catch((err) => console.error(err));
            },
        updateBrewery() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/${this.breweryID}`,{
                method: 'PUT',
                headers: {
                "Content-Type": "application/json"
                },
                body: JSON.stringify(this.brewery)
            })
            .then((response) => {
                if( response.ok ) {
                this.$router.push({path: '/breweries'});
                
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
            
            this.brewery.image = response.secure_url;
            console.log( this.brewery.imageUrl +"   in the getsuccess method")
        }
    },
    computed: {
       isValidForm() {
           return this.brewery.name != '' && this.brewery.website != '';
       }
    },
    created() {
        if(this.breweryID != 0) {
            fetch(this.apiURL + '/' + this.breweryID)
                .then((response) => {
                    return response.json();
                })
                .then((brewery) => {
                    this.brewery = brewery;
                })
                .catch((err) => console.error(err));
        }
    }
};
</script>

<style>
.brewery-form {
    background-color: hsla(240, 2%, 61%, 0.4);
    padding: 0px 50px 0px 0px;
    margin: 0px 50px 20px 0px;
    border-radius: 10px;
    align-items: center;
}
.label{
    display: inline;
    vertical-align: top;
    padding: 0px 10px 20px 0px;
    color: rgb(240, 201, 73);
    font-size: large;
    opacity: 1;
}
.breweryInput{
    padding: 10px 0px 10px 10px;
    text-align: right;
    float: left;
    width: 100%;
    opacity: 1;
}
.row {
    display: flex;
    padding: 30px 0px 40px 0px;
    
}
.column-one {
    width: 50%;
}
.column-two {
    padding-right: 50px;
    float: left;
    text-align: right;
}
.new-brewery-form{
    padding-bottom: 10px;
    text-align: center;
    
}
.new-brewery-form::after {
    content: "";
    display: table;
    clear: both;
}
.submit-brewery-button{
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
.submit-brewery-button:disabled{
    background-color: black;
    border: none;
    color: red;
    padding: 10px 40px;
    text-align: right;
    text-decoration: line-through;
    text-decoration-color: red;
    display: inline-block;
    font-size: 20px;
    border-radius: 8px;
    transition-duration: 0.4s;
    border: 2px solid red;
}
.submit-brewery-button:enabled:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
    
}
.back{
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
.back:hover{
    background-color: white;
    border: 2px solid rgb(240, 201, 73);
}
.header{
    
    text-align: center;
}
input{
    font-size: large;
}
.greeting{
    font-size: 35px;
    text-align: center;
    color: rgb(240, 201, 73);
}
#dropzone {
    width: 500px;
    display: inline-block;
    
}
</style>