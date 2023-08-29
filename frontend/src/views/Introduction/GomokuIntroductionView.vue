<template>
    Introductuon of Gomoku 
    <button @click="goToPKIndex">Play Gomoku</button>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
  components: {},
  setup() {
    const router = useRouter();

    const goToPKIndex = () => {
      router.push({ name: "PK_Gomoku_index" });
    };

    return {
      goToPKIndex
    };
  },
};
</script>
