<template>
  <div>
    <!-- Choose game -->
    <div class="game-selection">
      <div class="game-box" @click="navigateToGameIntro('Introduction_Snake')">
        <h3>Snake</h3>
      </div>
      <div class="game-box" @click="navigateToGameIntro('Introduction_Gomoku')">
        <h3>Gomoku</h3>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
  components: {},
  setup() {
    const router = useRouter();

    const navigateToGameIntro = (gameIntroName) => {
      if (gameIntroName === 'Introduction_Snake') {
        router.push({ name: "Introduction_Snake" });
      } else if (gameIntroName === 'Introduction_Gomoku') {
        router.push({ name: "Introduction_Gomoku" });
      }
    };

    return {
      navigateToGameIntro,
    };
  },
};
</script>

<style scoped>
.game-selection {
  display: flex;
  overflow-x: auto;
}

.game-box {
  flex: 0 0 auto;
  width: 300px;
  height: 200px;
  margin: 10px;
  background-color: #f1f1f1;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
</style>
