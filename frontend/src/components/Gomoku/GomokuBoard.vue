<template>
  <div ref="parent" class = "gomokuboard">
      <canvas 
      ref = "canvas" tabindex="0"></canvas>
  </div>
</template>
<!-- style="width:450px;height:450px;" width="450" height="450"  -->
<script>
import { GomokuBoard } from '@/assets/scripts/GomokuBoard';
import { ref ,onMounted} from 'vue';
import { useStore } from 'vuex';

export default {
  setup(){
      const store = useStore();
      let parent = ref(null);
      let canvas = ref(null);

      onMounted(() => { 
          store.commit(
              "updateGameObject", 
              new  GomokuBoard(canvas.value.getContext('2d'), parent.value, store)
              );
      });

      return {
          parent,
          canvas
      }
  }
}
</script>

<style scoped>
div.gomokuboard{
  width:100%;
  height:100%;
  display:flex;
  justify-content: center;
  align-items: center;
} 

</style>