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

            <div class= "col-12">
                <div class = "start-matching" style ="text-align: center; padding-top: 5vh;">
                <button @click="click_match_btn" type="button" class="btn btn-light btn-lg">{{match_btn_info}}</button>
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
                    console.log(select_bot.value , 2);
                    store.state.pk.socket.send(JSON.stringify({
                        event: "start-matching",
                        bot_id: select_bot.value,
                        game_id : 2,
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
    position: relative;
    width:60vw;
    height:70vh;
    margin: 40px auto;
}

div.matchground::before {
    content: "";
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-image: url('~@/assets/image/GomokuBack.jpeg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    opacity: 0.5;
    z-index: -1;
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
    padding-top: 45vh;
}
div.user-select-bot > select {
    width: 73%;
    margin: 0 auto;
}


</style>

