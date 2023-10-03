<template>
    <PlayGround v-if="$store.state.pk.status=== 'playing'" />
    <MatchGround v-if="$store.state.pk.status=== 'matching'" />
    <ResultBoard v-if="$store.state.pk.loser != 'none'"/>
</template>

<script>

import PlayGround from '../../components/Snake/PlayGround.vue'
import MatchGround from '../../components/Snake/MatchGround.vue'
import ResultBoard from '../../components/Snake/ResultBoard.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex' // 全局变量

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
        onMounted(() => {//当前组件被挂载(当前页面打开)时执行：
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
                const data = JSON.parse(msg.data); //Spring定义的数据格式: data
                if(data.event === "start-matching") {
                    store.commit("updateOpponent",{
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateStatus","playing");
                    }, 200);
                    store.commit("updateGamemap", data.game);
                }else if(data.event === "move"){
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                }
                else if (data.event === "result"){
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;

                    if(data.loser === "all" || data.loser ==="A"){
                        snake0.status = "die";
                    }
                    if(data.loser === "all" || data.loser ==="B" ){
                        snake1.status = "die";
                    }
                    store.commit("updateLoser",data.loser);
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