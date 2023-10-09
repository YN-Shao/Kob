<template>
    <div class="card edit-field">
        <div class="card-body">
            <label for="edit-post" class="form-label">Edit</label>
            <textarea v-model="content" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
            <div class="error_message">{{ result.error_message }} </div>
            <button @click="post_a_post" type="button" class="btn btn-primary btn-sm">Post</button>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue';
import $ from 'jquery';
import { useStore } from 'vuex';

export default {
    name: "UserProfileWrite",
    setup(props, context) {
        const store = useStore();
        const result = reactive({
            error_message: "",
        });
        let content = ref('');

        const post_a_post = () => {

            $.ajax({
                url: "http://127.0.0.1:3000/user/account/post/",
                type: "POST",
                data: {
                    content: content.value,
                },
                headers: {
                    'Authorization': "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        context.emit('post_a_post', content.value);
                        content.value = "";
                    } else {
                        result.error_message = resp.error_message;
                    }
                }
                
            });
        };

        return {
            content,
            post_a_post,
            result,
        }
    }
}
</script>

<style scoped>
.edit-field {
    margin-top: 20px;
}
button {
    margin-top: 10px;
}
.error_message {
    color: red;
}
</style>