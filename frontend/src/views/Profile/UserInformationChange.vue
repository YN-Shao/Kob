<template>
  <div class="profile-container">

    <!-- Left Side: Avatar and Username -->
    <div class="profile-left">
      <!-- When user clicks on the avatar, AvatarSelector modal will be triggered -->
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

    <!-- Avatar Selector Modal -->
    <AvatarSelector :showModal="showModal" @avatarSelected="updatePhotoURL" @closeModal="closeAvatarSelector" />

  </div>
</template>


<script>
import { mapActions } from 'vuex';
import AvatarSelector from "@/components/UserProfile/AvatarSelector.vue";

export default {
  components: {
    AvatarSelector
  },
  data() {
    return {
      username: '',
      photo: '',
      errorMessage: '',
      showModal: false
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

    async updateAvatar() {
      try {
        await this.updateAvatarMethod(this.photo);
        alert('Avatar updated successfully!');
        this.showModal = false;
      } catch (error) {
        this.errorMessage = 'An error occurred while updating avatar.';
        alert(this.errorMessage);
      }
    },

    closeAvatarSelector() {
      this.showModal = false;
    },

    updatePhotoURL(newPhotoURL) {
      this.photo = newPhotoURL;
      this.showModal = false;
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
</style>
