<template >
    <div class="reviewPage" :style="image" >
        <div class="header">
            <h1 id="beer-name">{{name}}</h1>
        </div>
        <div class="form-div">
            <form class="beer-review-form" @submit.prevent="saveReview">
                <div class="reviewInput">
                    <div class="rating-title">
                        <h2>{{reviewText}}</h2>
                    </div>
                    <div class="beer-images">
                        <img class="beer-img" src="@/assets/empty-beer.jpg" v-if="review.rating < 1" @mouseenter="review.rating = 1" />
                        <img class="beer-img" src="@/assets/full-beer.png" v-if="review.rating >= 1" @mouseenter="review.rating = 1" @dblclick="review.rating = 0"/>
                        <img class="beer-img" src="@/assets/empty-beer.jpg" v-if="review.rating < 2" @mouseenter="review.rating = 2" />
                        <img class="beer-img" src="@/assets/full-beer.png" v-if="review.rating >= 2" @mouseenter="review.rating = 2" @dblclick="review.rating = 0"/>
                        <img class="beer-img" src="@/assets/empty-beer.jpg" v-if="review.rating < 3" @mouseenter="review.rating = 3" />
                        <img class="beer-img" src="@/assets/full-beer.png" v-if="review.rating >= 3" @mouseenter="review.rating = 3" @dblclick="review.rating = 0"/>
                        <img class="beer-img" src="@/assets/empty-beer.jpg" v-if="review.rating < 4" @mouseenter="review.rating = 4" />
                        <img class="beer-img" src="@/assets/full-beer.png" v-if="review.rating >= 4" @mouseenter="review.rating = 4" @dblclick="review.rating = 0"/>
                        <img class="beer-img" src="@/assets/empty-beer.jpg" v-if="review.rating < 5" @mouseenter="review.rating = 5" />
                        <img class="beer-img" src="@/assets/full-beer.png" v-if="review.rating >= 5" @mouseenter="review.rating = 5" @dblclick="review.rating = 0" />
                    </div>
                </div>
                <div class="reviewInput">
                    <span class="review">Review:</span>
                    <textarea name="review" cols="60" rows="10" v-model="review.text"></textarea>
                </div>
                <button type="submit" >Submit Review</button>
            </form>
        </div>
    </div>
</template>

<script>
import auth from '@/auth.js'

export default {
    data() {
        return {
            name: this.$route.params.beerName,
            review: {
                beerId: this.$route.params.beerId,
                rating: 0,
                text: '',
                username: auth.getUser().sub
            },
            image: {
                background: `url(${require('@/assets/reviewBackground.jpeg')}) no-repeat`,
                backgroundSize: "100%",
                backgroundPosition: "center"
            }
        }
    },
    methods: {
        saveReview(){
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/addReview`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken(),     
                },
                body: JSON.stringify(this.review),
                })
                .then((response) => {
                    if(response.ok) {
                        this.$router.push({name: 'confirmation', params: {confirmationObject: 'Review'}});
                    }
                    //return response.json();
                })
                .catch((err) => console.error(err));
        },
        // setRating(rating) {
        //     this.userRating = rating;
        //     this.review.rating = rating;
        // }
    },
    computed: {
        reviewText: function(){
            switch(this.review.rating){
                case 1:
                    return "Sober";
                    break;
                case 2:
                    return "Buzzed";
                    break;
                case 3:
                    return "Tipsy";
                    break;
                case 4:
                    return "Stupor";
                    break;
                case 5:
                    return "Blackout";
                    break;
            }
        }
    }
}
</script>

<style scoped>
   .form-div {
        display: block;
        text-align: center;
    }
    form {
        display: inline-block;
        margin-left: auto;
        margin-right: auto;
        text-align: left;
    }
    
    .review{
        display: flex;
        font-size: 15px;
        vertical-align:middle; 
    }
    select {
        margin-left: 20px;
        margin-bottom: 30px;
    }
    .beer-img {
        height: 100px;
        width: auto;
    }
    .beer-images {
        display: block;
        text-align: center;
    }
    .rating-title {
        display: block;
        text-align: center;
    }
    .reviewPage {
        padding-bottom: 500px;
    }

</style>