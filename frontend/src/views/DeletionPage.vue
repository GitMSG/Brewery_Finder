<template>
    <div class="deletion">
        <h1> {{confirmationObject}}</h1>
        <h3><router-link v-bind:to="{ name: 'beers'}">Return to Beers</router-link></h3>
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