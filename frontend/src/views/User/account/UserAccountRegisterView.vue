<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                    <form @submit.prevent="register">
                        <div class="mb-3">
                            <label for="username" class="form-label">User name</label>
                            <input v-model="username" type="text" class="form-control" id="username" aria-describedby="Please input username">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input v-model="password" type="password" class="form-control" id="password" aria-describedby="Please input password">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Confirm Password</label>
                            <input v-model="confirmedPassword" type="password" class="form-control" id="confirmedPassword" aria-describedby="Please input password again">
                        </div>
                        <div class="error-message">{{ error_message }}</div>
                        <button type="submit" class="btn btn-dark">Sumbit</button>
                    </form>
            </div>
        </div>
    </ContentField>
</template>
<script>
import ContentField from '../../../components/ContentField.vue'
import {ref} from 'vue'
import router from '../../../router/index'
import $ from 'jquery' 

export default{
    components:{
        ContentField
    },
    setup(){
        let username = ref("");
        let password = ref("");
        let confirmedPassword = ref("");
        let error_message = ref("");

        const register = () =>{
            $.ajax({
                url:"http://127.0.0.1:3000/user/account/register/",
                type:"post",//会修改数据库就post，安全性强一些
                data:{
                    username: username.value,
                    password: password.value,
                    confirmedPassword: confirmedPassword.value,
                },
                success(resp){
                    if(resp.error_message === "success"){
                        router.push({name:"user_account_login"});
                    }else{
                        error_message.value = resp.error_message;
                    }
                },
                error(resp){
                    console.log(resp);
                },
            });
        }
        return {
            username,
            password,
            confirmedPassword,
            error_message,
            register,
        }
    }
}
</script>

<style  scoped>
button {
    width : 100%;
}
div.error-message {
    color:red;
}
</style>