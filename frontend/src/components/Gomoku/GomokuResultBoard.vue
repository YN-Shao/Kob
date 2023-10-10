<template>
    <div class="result-board">
        <div class = "result-board-text" v-if="$store.state.pk.loser ==='all'">
            Draw
        </div>
        <div class = "result-board-text" v-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id == $store.state.user.id">
            Lose
        </div>
        <div class = "result-board-text" v-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id == $store.state.user.id">
            Lose
        </div>
        <div class = "result-board-text" v-if="$store.state.pk.loser === 'A' && $store.state.pk.b_id == $store.state.user.id">
            Win
        </div>
        <div class = "result-board-text" v-if="$store.state.pk.loser === 'B' && $store.state.pk.a_id == $store.state.user.id">
            Win
        </div>
        <div class = "result-board-btn">
            <button @click="restart" type="button" class="btn btn-warning btn-lg">
                Play Again
            </button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';
export default{
    setup(){

        const store = useStore();
        const restart = () =>{
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                username: "My opponent",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
        }
        return  {
            restart
        };
    }
}
</script>

<style scoped>
div.result-board{
    height:30 vh;
    width:30vw;
    background-color : rgba(50, 50, 50, 0.5);
    position: absolute;
    top: 38vh;
    left: 35vw;
}
div.result-board-text{
    text-align: center;
    color: white;
    font-size : 70px;
    font-weight: 600;
    font-style : italic;
    padding-top: 5vh;
}
div.result-board-btn{
    padding-top:3vh;
    text-align: center;
}
</style>