<template>
    <ContentBase>
        <!-- Loop through users and display their posts and details -->
        <div class="card outer-card" v-for="post in posts.posts" :key="post.postId">
            <div class="card-body">

                <!-- for UserProfileInfo -->
                <div class="row"> 
                    <div class="col-4">
                        <!-- UserProfileInfo component for displaying user's profile info -->
                        <UserProfileInfo v-if="post.user" :user="post.user" />
                    </div>
                    <div class="col-8">
        
                    </div>
                </div>

                <!-- UserProfilePosts -->
                <div class="row">
                    <div class="col-12">
                        <UserProfilePosts :user="post.user" :posts="{ posts: [post] }" />
                    </div>
                </div>
                
            </div>
        </div>
    </ContentBase>
</template>

  
<script>
import ContentBase from '../../components/UserProfile/ContentBase';
import UserProfileInfo from '../../components/UserProfile/UserProfileInfo';
import UserProfilePosts from '../../components/UserProfile/UserProfilePosts';
import $ from 'jquery';
import { useStore } from 'vuex';
import { reactive } from 'vue';


export default {
    name: 'CommunityIndexView',
    components: {
        ContentBase,
        UserProfileInfo,
        UserProfilePosts
    },

    setup() {
        const store = useStore();
        const posts = reactive({ posts: [] });

        $.ajax({
            url: "http://127.0.0.1:3000/community/",
            type: "GET",
            headers: {
                'Authorization': "Bearer " + store.state.user.token,
            },
            success(resp) {
                console.log(resp);  
                let completedRequests = 0;
                resp.forEach(post => {
                    $.ajax({
                        url: "http://127.0.0.1:3000/getCommunityInfo/",
                        data: {
                            user_id: post.userId,
                        },
                        type: "GET",
                        headers: {
                            'Authorization': "Bearer " + store.state.user.token,
                        },
                        success(userResp) { 
                            console.log(userResp);
                            post.user = userResp;
                            completedRequests++;
                            if (completedRequests === resp.length) {
                                posts.posts = resp;
                            }
                        }
                    });
                });
            }
        });


        return {
            posts,
        };
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
    width: 60px;
    height: 60px;
    object-fit: cover;
}

::v-deep .card .img-field img {
    margin-left: 10px; 
}
</style>

