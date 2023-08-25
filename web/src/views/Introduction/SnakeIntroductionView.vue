<template>
    Introductuon of Snake
    <button @click="goToPKIndex">Play Snake</button>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
  components: {},
  setup() {
    const router = useRouter();

    const goToPKIndex = () => {
      router.push({ name: "PK_index" });
    };

    return {
      goToPKIndex
    };
  },
};
</script>
