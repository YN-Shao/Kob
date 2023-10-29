<template>
  <div>
    <!-- Modal for updating avatar -->
    <div v-if="isModalVisible" class="modal">
      <div class="modal-content">
        <span @click="closeModal" class="close">&times;</span>
        <label>Choose a seed:</label>
        <select v-model="selectedSeed">
          <option v-for="presetSeed in presetSeeds" :key="presetSeed" :value="presetSeed">{{ presetSeed }}</option>
        </select>
        <input v-model="customSeed" placeholder="Or enter your own seed" />
        <button @click="generateAvatar">Generate Avatar</button>
        <img v-if="generatedAvatar" :src="generatedAvatar" alt="Generated Avatar" />
        <button @click="confirmAvatar">Confirm</button>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions} from "vuex";  // Import axios for making HTTP requests

export default {
  props: {
    showModal: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      isModalVisible: this.showModal,
      generatedAvatar: '',
      presetSeeds: ['apple', 'banana', 'cherry', 'date'],
      customSeed: '',
      selectedSeed: 'apple'
    };
  },
  watch: {
    showModal(newVal) {
      this.isModalVisible = newVal;
    }
  },
  methods: {
    ...mapActions(['getProfileInfo', 'updateProfileInfo', 'updateAvatarMethod']),
    generateAvatar() {
      const seed = this.customSeed || this.selectedSeed;
      this.generatedAvatar = `https://api.dicebear.com/7.x/lorelei/svg?seed=${seed}`;
    },
    async confirmAvatar() {
      try {
        const data = {
          photo: this.generatedAvatar
        };
        await this.updateProfileInfo(data);
        alert('Avatar updated successfully!');
        this.closeModal();
      } catch (error) {
        this.errorMessage = 'An error occurred while updating avatar.';
        alert(this.errorMessage);
      }
    },

    closeModal() {
      this.isModalVisible = false;
      this.$emit('closeModal');
    }
  }
};
</script>

<style scoped>
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
  text-align: center;
}

.close {
  cursor: pointer;
  float: right;
  font-size: 24px;
}
</style>
