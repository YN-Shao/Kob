<template>
  <div class="container">
    <!-- Choose game -->
    <div class="game-selection">
      <div class="game-box snake" @click="navigateToGameIntro('Introduction_Snake')">
        <h3>Snake</h3>
      </div>
      <div class="game-box gomoku" @click="navigateToGameIntro('Introduction_Gomoku')">
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
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin-top: -50px;
  margin-bottom: 200px;
}

.game-selection {
  display: flex;
  justify-content: center;
  gap: 60px;
}

.game-box {
  width: 600px;
  height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  background-size: cover; /* Makes sure the image covers the entire box */
  background-repeat: no-repeat; /* Ensures the image doesn't repeat */
  background-position: center; /* Centers the image */
  color: white; /* Set the text color to white for better visibility on images */
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* Adds shadow to the text for better visibility */
}

.game-box:hover {
  transform: scale(1.05);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.9);
}

.snake {
  background-image: url('https://i.etsystatic.com/20027195/r/il/ebe5f2/3894622064/il_fullxfull.3894622064_ojz8.jpg');
}

.gomoku {
  background-image: url('https://store-images.s-microsoft.com/image/apps.31803.14390649523562813.11731bff-c508-48ac-8da3-de16bae6a961.df7a8912-a932-48bd-8f83-536a2959c261?q=90&w=480&h=270');
}
</style>
