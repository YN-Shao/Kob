<template>
    <ContentBase>
        <!-- Loop through users and display their posts and details -->
        <div class="card" v-for="post in posts.posts" :key="post.postId">
            <div class="card-body">
                <div class="row">
                    <div class="col-3">
                        <UserProfileInfo v-if="post.user" :user="post.user" />
                        
                    </div>
                    <div class="col-9">
                        <!-- Passing entire post object to UserProfilePosts -->
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
img {
    border-radius: 50%;
}


.card {
    margin-bottom: 20px;
    cursor: pointer;
} 

.card:hover {
    box-shadow: 2px 2px 10px lightgrey;
    transition: 500ms;
}

.img-field {
    display: flex;
    flex-direction: column;
    justify-content: center;
}



</style>

