<template>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <router-link class="navbar-brand" :to="{name : 'home'}" > Kings Of Bot</router-link>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <router-link :class="route_name == 'PK_index'? 'nav-link active' : 'nav-link'" :to="{name:'home'}">Fight</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name == 'record_index'? 'nav-link active' : 'nav-link'" :to="{name:'record_index'}">List</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name == 'RankList_index'? 'nav-link active' : 'nav-link'" :to="{name:'RankList_index'}">Rank</router-link>
        </li>
        </ul>
        <ul class="dropdown" v-if ="$store.state.user.is_login">
          <a class="btn btn-secondary dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
             {{$store.state.user.username}}
          </a>
          <ul class="dropdown-menu">
         
          <router-link class="dropdown-item" :to="{name:'user_bot_index'}">My Bot</router-link>
          <li><hr class = "dropdown-divider"></li>
          <li><a class="dropdown-item" href="#" @click="logout">Exit</a></li>
         </ul>
        </ul>
        <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info" >
          <li class="nav-item">
          <router-link class="nav-link" :to="{name : 'user_account_login'}" role="button" >
             Login
          </router-link>
          </li>       
          <li class = "navbar-item">
          <router-link class="nav-link" :to="{name : 'user_account_register'}" role="button">
              Register
          </router-link>
          </li>
        </ul>
    </div>
  </div>
</nav>
</template>
<script>
import { useRoute }from 'vue-router'
import {computed} from 'vue'
import { useStore } from 'vuex';

export default{
    setup(){
      const store = useStore();
      const route = useRoute();
      let route_name = computed(() => route.name)

      const logout = () =>{
        store.dispatch("logout");
      }

      return{
        route_name,
        logout
      }
    }
}
</script>

<style>
</style>
