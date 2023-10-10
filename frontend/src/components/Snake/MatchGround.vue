<template>
    <div class = "matchground">
        <div class="row">
            <div class="col-4">
                <div class = "user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>

            <div class="col-4">
                <div class="user-select-bot">  
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>Manual Battles</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">{{bot.title}}</option>
                    </select>
                </div>

            </div>
            <div class="col-4">
                <div class = "user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div> 
        </div>
        <div class="row">    
            <div class= "col-4">
                <div class="left-snake-gif" >
                    <img :src="require('@/assets/image/snake.gif')" >
                </div>
            </div>

            <div class= "col-4">
                <div class = "start-matching" style ="text-align: center; padding-top: 12vh;">
                <button @click="click_match_btn" type="button" class="btn btn-light btn-lg">{{match_btn_info}}</button>
                </div>
            </div>

            <div class= "col-4">
                <div class="right-snake-gif" >
                    <img :src="require('@/assets/image/snake.gif')" >
                </div>
            </div>

        </div>
    </div>
 
</template>



<script>
import { ref } from 'vue'
import { useStore } from 'vuex';
import $ from 'jquery';

export default{
    setup(){
        const store = useStore();
        let match_btn_info = ref("Start Matching");
        let bots = ref([]);
        let select_bot = ref("-1");

        const click_match_btn = () => {
            if (store.state.pk.socket !== null) {
                if(match_btn_info.value === "Start Matching"){
                    match_btn_info.value = "Cancel";
                    store.state.pk.socket.send(JSON.stringify({
                        event: "start-matching",
                        bot_id: select_bot.value,
                    }));  //向后端发这个JSON
                } else {
                    match_btn_info.value = "Start Matching";
                    store.state.pk.socket.send(JSON.stringify({
                        event: "stop-matching",
                    }));
                }
            } else {
                console.error('Socket is null');
            }
        }

        const refresh_bots = () => {
                $.ajax({
                    url:"http://127.0.0.1:3000/user/bot/getlist/",
                    type:"get",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    success(resp){
                        bots.value = resp;
                    }
                })
            }

        
        refresh_bots();//从云端动态获取bots

        return {
            match_btn_info,
            click_match_btn,
            bots,
            select_bot,
        }
    }
}


</script>

<style scoped>
div.matchground{
    width:60vw;
    height:70vh;
    margin: 40px auto;
    background-color: rgba(50,50,50,0.5);
}
div.user-photo{
    text-align : center;
    padding-top: 8vh;
}
div.user-photo > img {
    border-radius: 50%;
    width:28vh;
    height: 28vh; 
    object-fit: cover;
}
div.user-username{
    text-align: center;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.user-select-bot {
    padding-top: 37vh;
}
div.user-select-bot > select {
    width: 73%;
    margin: 0 auto;
}
 .right-snake-gif img {
    width: 20vh;
    height: 20vh;
    object-fit: cover;
}
.left-snake-gif img {
    width: 30vh;
    height: 20vh;
    object-fit: cover;
    transform: scaleX(-1); 
    padding-right: 10vh;
}

</style>

