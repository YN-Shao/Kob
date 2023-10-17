
<template>
    <div class = "playground">
   <GomokuBoard />
    </div>
 
</template>

<script>
/* eslint-disable */
import GomokuBoard from './GomokuBoard.vue'
export default{
    components:{
        GomokuBoard,
    }
}
</script>

<style scoped>
div.playground{
    width:60vw;
    height:70vh;
   
    margin: 40px auto;
}
</style>

