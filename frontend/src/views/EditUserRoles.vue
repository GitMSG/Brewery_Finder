<template>
    <div id="editUsersPage" class="role-page">
        <h2 class="role-header">User Info</h2>
        <ul class="role-group">
            <li v-for="user in users" v-bind:key="user.id" class="role-item">
                <User :username = user.username :currentRole = user.role :id = user.id />
            </li>
        </ul>
    </div>
</template>

<script>
import auth from '@/auth.js'
import User from '@/components/User.vue'

export default {
    components: {
        User
    },
    data() {
        return {
            users: []
        }
    },

    created() {
      fetch('http://localhost:8080/AuthenticationApplication/api/users',
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
        this.users = theData;
        })
        .catch((err) => {
        console.log(err);
        })
    }
}
</script>

<style>
.role-page{
  background: dimgray;
  padding-bottom: 100%;
}
.role-header{
  font-size: 50px;
  margin-top: 45px;
  font-family: fantasy;
  color: rgb(240, 201, 73);
  padding-top: 5px;
}
.role-item{
    list-style-type: none;
    border: 2px solid black;
    width: 45%;
    margin-bottom: 5px;
    background: rgb(75, 75, 75);
}
.role-username{
    color: rgb(240, 201, 73);
}
.role-select{
    float:right;
}
.role-submit{
    float: right;
}
.role-form{
    padding: 2px 5px;
}

</style>