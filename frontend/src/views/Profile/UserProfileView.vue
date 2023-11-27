<template>
  <ContentBase>
    <div class="row">
      <div class="col-3">
        <!--UserProfileInfo @follow="follow" @unfollow="unfollow" v-bind:user="user" /-->
        <UserProfileInfo v-if="user" :user="user" />
        <button type="button" class="btn btn-secondary btn-sm" @click="navigateToInfoManagement">Info Management</button>
        <!-- button @click="navigateToInfoManagement">Info Management</button /-->
        <UserProfileWrite v-if="is_me" @post_a_post="post_a_post" />
      </div>
      <div class="col-9">
        <UserProfilePosts :user="user" :posts="posts" @delete_a_post="delete_a_post"/>
      </div>
    </div>
  </ContentBase>
</template>

<script>
import ContentBase from '../../components/UserProfile/ContentBase';
import UserProfileInfo from '../../components/UserProfile/UserProfileInfo';
import UserProfilePosts from '../../components/UserProfile/UserProfilePosts';
import UserProfileWrite from '../../components/UserProfile/UserProfileWrite';
import { reactive } from 'vue';
import { useRoute } from 'vue-router';
import $ from 'jquery';
import { useStore } from 'vuex';
import { computed } from 'vue';

export default {
    name: 'UserProfile',
    components: {
        ContentBase,
        UserProfileInfo,
        UserProfilePosts,
        UserProfileWrite
    },
    methods: {
      navigateToInfoManagement() {
        this.$router.push({ name: 'user_profile_change' });
      }
    },
    setup() {
        const store = useStore();
        const route = useRoute();
        const userId = parseInt(route.params.userId);
        const user = reactive({ });
        const posts = reactive({ });

        $.ajax({
            url: "http://127.0.0.1:3000/user/account/info/",
            type: "GET",
            data: {
                user_id: userId,
            },
            headers: {
                'Authorization': "Bearer " + store.state.user.token,
            },
            success(resp) {
                user.id = resp.id;
                user.username = resp.username;
                user.photo = resp.photo;
                //user.followerCount = resp.followerCount;
                //user.is_followed = resp.is_followed;
            }
        });

        $.ajax({
            url: "http://127.0.0.1:3000/user/account/post/",
            type: "GET",
            data: {
                user_id: userId,
            },
            headers: {
                'Authorization': "Bearer " + store.state.user.token,
            },
            success(resp) {
                console.log(resp);
                posts.count = resp.length;
                posts.userId = resp.userId;
                posts.posts = resp;
            }
        });

        const post_a_post = (content) => {
            posts.posts.unshift({
                id: posts.count,
                userId: user.id,
                postContent: content,
            })
        };

        const delete_a_post = post_id => {
            posts.posts = posts.posts.filter(post => post.postId !== post_id);
            posts.count = posts.posts.length;
        }

        const is_me = computed(() => userId === parseInt(store.state.user.id));

        // const follow = () => {
        //     if (user.is_followed) return;
        //     user.is_followed = true;
        //     user.followerCount ++;
        // };

        // const unfollow = () => {
        //     if (!user.is_followed) return;
        //     user.is_followed = false;
        //     user.followerCount--;
        // };

        return {
            user,
            posts,
            post_a_post,
            is_me,
            delete_a_post,
            // follow,
            // unfollow,
        }
    }
}
</script>


<style scoped>
.card {
    cursor: pointer;
    background-color: #f5f5f5;
    border: none;
}

.outer-card {
    margin-bottom: 30px; 
    box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.1); 
    transition: box-shadow 0.5s;
}

.outer-card:hover {
    box-shadow: 3px 3px 12px rgba(0, 0, 0, 0.2); 
}

::v-deep .card .img-fluid {
    width: 100px;
    height: 50px;
    object-fit: cover;
}

::v-deep .card .img-field img {
    margin-left: 10px; /* adjust as needed */
}
</style>