
<template>
    <PlayGround v-if="$store.state.pk.status=== 'playing'" />
    <MatchGround v-if="$store.state.pk.status=== 'matching'" />
    <ResultBoard v-if="$store.state.pk.loser != 'none'"/>
</template>

<script>
/* eslint-disable */
import PlayGround from '../../components/Gomoku/PlayGround.vue'
import MatchGround from '../../components/Gomoku/GomokuMatchGround.vue'
import ResultBoard from '../../components/Gomoku/GomokuResultBoard.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'

export default{
    components:{
        PlayGround,
        MatchGround,
        ResultBoard,
    },
    setup(){
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        store.commit("updateLoser", "none");
        store.commit("updateIsRecord", false);

        let socket = null;
        onMounted(() => {
            store.commit("updateOpponent", {
                username: "My opponent",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                store.commit("updateSocket", socket);
                console.log(socket);
                console.log("connected!");
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                const game = store.state.pk.gameObject;
                if(data.event === "start-matching") {
                    store.commit("updateOpponent",{
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    
                    setTimeout(() => {
                        store.commit("updateStatus","playing");
                    }, 200);
                    // data.game.set_color(store.state.pk.color)
                    store.commit("updateGomokuBoard", data.game);
                    
                }else if(data.event === "move"){
                    // const game = store.state.pk.gameObject;
                    game.pieces[data.x][data.y].set_color(data.color);
                }
                else if (data.event === "result"){
                    store.commit("updateLoser",data.loser);
                }
                else if (data.event === "assignColorCode"){
                    // const game = store.state.pk.gameObject;
                    store.commit("updateColor",data.color);
                    store.state.pk.color = data.color;
                    // game.set_color(data.color);
                }
                //console.log(data)
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }

        });
        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus","matching");
            
        })
    }
}
</script>

<style scoped>
</style>