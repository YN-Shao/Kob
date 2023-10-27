<template>
  <div class="profile-container">
    <div class="profile-box">
      <!-- Avatar -->
      <img class="user-avatar" :src="photo" alt="User Avatar" @click="showModal = true">

      <!-- Username and Update -->
      <h2 class="user-name">{{ username }}</h2>
      <div class="input-group">
        <input v-model="username" placeholder="New Username"/>
        <button @click="updateUsername">Update Username</button>
      </div>

      <!-- Password Update -->
      <div class="input-group">
        <input type="password" v-model="oldPassword" placeholder="Old Password"/>
      </div>
      <div class="input-group">
        <input type="password" v-model="password" placeholder="New Password"/>
        <button @click="updatePassword">Update Password</button>
      </div>

      <!-- Avatar Selector Modal -->
      <AvatarSelector :showModal="showModal" @avatarSelected="updatePhotoURL" @closeModal="closeAvatarSelector" />
    </div>
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
      oldPassword: '',
      password: '',
      photo: '',
      errorMessage: '',
      showModal: false
    };
  },
  methods: {
    ...mapActions(['getProfileInfo', 'updateProfileInfo', 'updateAvatarMethod']),

    async updateUsername() {
      try {
        const data = {
          username: this.username
        };
        const response = await this.updateProfileInfo(data);
        if (response.success) {
          alert(response.message);
        } else {
          alert('Error updating username: ' + response.message);
        }
      } catch (error) {
        this.errorMessage = 'An error occurred while updating username.';
        alert(this.errorMessage);
      }
    },

    async updatePassword() {
      console.log("Starting updatePassword method");  // 新添加的日志语句

      if (this.oldPassword === this.password) {
        alert('New password cannot be the same as the old password.');
        return;
      }

      try {
        const data = {
          oldPassword: this.oldPassword,
          newPassword: this.password
        };
        console.log("Sending request with data:", data);  // 移动到这里
        const response = await this.updateProfileInfo(data);
        console.log("Received response:", response);
        if (response.success) {
          alert('Successfully update the password!');
        } else {
          alert('Error updating password: ' + response.message);
        }
      } catch (error) {
        console.error("Error during updatePassword:", error);  // 新添加的日志语句
        this.errorMessage = error.message || 'An error occurred while updating password.';
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

    closeAvatarSelector() {
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
  background-image: url('~@/assets/image/background.jpg'); /* Added image background */
  background-size: cover; /* To make sure the image covers the whole container */
  background-repeat: no-repeat; /* To prevent image repetition */
}

.profile-box {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 300px;
}

.user-avatar {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  cursor: pointer;
  margin-bottom: 15px;
}

.user-name {
  font-size: 20px;
  margin-bottom: 10px;
}

.input-group {
  margin: 10px 0;
  display: flex;
  justify-content: space-between;
}

button {
  margin-left: 10px;
}
</style>
