<template>
  <div class="profile-container">
    <!-- Left Side: Avatar and Username -->
    <div class="profile-left">
      <img class="user-avatar" :src="photo" alt="User Avatar" @click="showModal = true">
      <h2 class="user-name">{{ username }}</h2>
    </div>

    <!-- Right Side: Buttons and Inputs -->
    <div class="profile-right">
      <div>
        <label>Update Username:</label>
        <input v-model="username" />
      </div>
      <button @click="updateUserDetails">Update Info</button>
    </div>

    <!-- Modal for updating avatar -->
    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span @click="showModal = false" class="close">&times;</span>
        <label>Choose new avatar:</label>
        <input type="file" ref="fileInput" @change="updatePhoto" style="display: none;" />
        <button @click="triggerFileInput">Choose File</button>
        <button @click="updateAvatar">Update Avatar</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      username: '',
      photo: '',
      errorMessage: '',
      showModal: false,
      selectedFile: null
    };
  },
  methods: {
    ...mapActions(['getProfileInfo', 'updateProfileInfo', 'updateAvatarMethod']),

    async updateUserDetails() {
      try {
        const data = {
          username: this.username,
          photo: this.photo
        };
        await this.updateProfileInfo(data);
        alert('Information updated successfully!');
      } catch (error) {
        this.errorMessage = 'An error occurred while updating information.';
        alert(this.errorMessage);
      }
    },

    async fetchUserInfo() {
      try {
        const userInfo = await this.getProfileInfo();
        this.username = userInfo.username;
        this.photo = userInfo.photo;
      } catch (error) {
        this.errorMessage = 'An error occurred while fetching user information.';
      }
    },

    updatePhoto(event) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.photo = e.target.result;
        };
        reader.readAsDataURL(this.selectedFile);
      }
    },

    async updateAvatar() {
      if (!this.selectedFile) {
        alert('Please select a file before updating.');
        return;
      }
      try {
        await this.updateAvatarMethod(this.photo);
        alert('Avatar updated successfully!');
        this.showModal = false;
      } catch (error) {
        this.errorMessage = 'An error occurred while updating avatar.';
        alert(this.errorMessage);
      }
    },

    triggerFileInput() {
      this.$refs.fileInput.click();
    }

  },
  async created() {
    await this.fetchUserInfo();
  }
};
</script>

<style scoped>
.profile-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.profile-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  border-right: 1px solid #ccc;
}

.user-avatar {
  width: 200px; /* Increased size */
  height: 200px; /* Increased size */
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
}

.user-name {
  margin-top: 20px;
  font-size: 24px;
}

.profile-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

button {
  margin: 10px 0;
}

/* Styles for the modal */
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
}

.close {
  cursor: pointer;
  float: right;
  font-size: 24px;
}
</style>
