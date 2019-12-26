<template>
  <div>
    <div>
      <label>
          <br>
        <gmap-autocomplete
          @place_changed="setPlace">
        </gmap-autocomplete>
        <button @click="addMarker">Add</button>
      </label>
      <br/>

    </div>
    <br>
    <gmap-map
      :center="center"
      :zoom="12"
      style="width:100%;  height: 400px;"
    >
      <gmap-marker
        v-for="(m, index) in markers"
        :key="index"
        :position="m.position"
        @click="center=m.position"
      ></gmap-marker>
    </gmap-map>
  </div>
</template>

<script>
import Brewery from '@/views/Brewery.vue'
export default {
  name: "GoogleMap",
  props: {
    center: Object
  },
  components: {
    Brewery
  },
  data() {
    return {
      // default to Montreal to keep it simple
      // change this to whatever makes sense
      center: this.$props.center,
      markers: [{position: this.$props.center}],
      places: [],
      currentPlace: Brewery
    };
  },

  created() {
    this.addMarker();
  },

  methods: {
    // receives a place object via the autocomplete component
    setCenter(){
      this.center.lat = this.$props.latitude;
      this.center.lng = this.$props.longitude;
    },
    setPlace(place) {
      this.currentPlace = place;
    },
    addMarker() {
      if (this.currentPlace) {
        const marker = {
          lat: this.$props.center.lat,
          lng: this.$props.center.lng
        };
        this.markers.push({ position: marker });
        this.places.push(this.currentPlace);
        this.center = marker;
        this.currentPlace = null;
      }
    },
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
      });
    }
  }
};
</script>
