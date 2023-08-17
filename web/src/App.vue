<template>
  <NavBar/>
  <RouterView></RouterView>
</template>

<script>
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
//import $ from 'jquery'

export default{
  components:{
    NavBar
  },
  setup() {
    /*$.ajax({
      url:"http://127.0.0.1:3000/user/account/token/",
      type:"post",
      data: {
        username: 'Suili Zhang',
        password: "123",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      }
    });
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/info/",
      type:"get",
      headers:{
        Authorization:"Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyYTAzOWNjNTMyM2U0N2UwYWI0NTVlNzAxMzQwMWY0MiIsInN1YiI6IjIiLCJpc3MiOiJzZyIsImlhdCI6MTY4ODU2MDQyNCwiZXhwIjoxNjg5NzcwMDI0fQ.D4Mu2KEQv9YPd_K3x49EsFpbar82psXNKxIdtk7MmvU"
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      }
    });
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/register/",
      type: "post",
      data:{
        username: "Yining Shao",
        password:"123",
        confirmPassword:"123",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp) {
        console.log(resp);
      }
    })*/
  }
}
</script>

<style>
body{
  background-image: url("./assets/image/background.jpg");
  background-size: cover;
}
</style>
