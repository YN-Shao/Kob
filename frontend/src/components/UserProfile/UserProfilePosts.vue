<template>
    <div class="card">
        <div class="card-body">
            <div v-for="post in posts.posts" :key="post.postId">
                <div class="card single-post">
                    <div class="card-body">
                        {{ post.postContent }}
                        <button @click="delete_a_post(post.postId)" v-if="is_me" type="button" class="btn btn-danger btn-sm">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
import $ from 'jquery';

export default {
    name: "UserProfilePosts",
    props: {
        posts: {
            type: Object,
            required: true,
        },
        user: {
            type: Object,
            required: true,
        }
    },
    setup(props, context) {
        const store = useStore();
        let is_me = computed(() => store.state.user.id === props.user.id);

        const delete_a_post = post_id => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/post/",
                type: "DELETE",
                data: {
                    post_id,
                },
                headers: {
                    'Authorization': "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        context.emit('delete_a_post', post_id);
                    }
                }
            })
        }
        return {
            is_me,
            delete_a_post,
        }
    }

}
</script>

<style scoped>
.single-post {
    margin-bottom: 10px;
}

button {
    float: right;
}
</style>