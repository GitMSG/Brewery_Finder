<template>
    <div class="confirmation">
        <h1>Thank you for adding your {{confirmationObject}}</h1>
        <h3><router-link v-bind:to="{ name: 'breweries'}">Return to Breweries</router-link></h3>
    </div>
</template>

<script>
export default {
    data() {
        return {
            confirmationObject: this.$route.params.confirmationObject
        }
    }
    
}
</script>