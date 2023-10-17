<template>
    <div class="card">
        <div class="card-body">
            <div class="row">
                <div class="col-3 img-field" v-if="user && user.photo">
                    <img class="img-fluid" :src="user.photo" alt="">
                </div>
                <div class="col-9">
                    <div class="username">{{ user.username }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "UserProfileInfo",
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
}

</script>



<style scoped>
img {
    border-radius: 50%;
}

.username {
    font-weight: bold;
    display: flex;
    align-items: center;     /* 垂直居中 */
    height: 100%;            /* 假设这是一个块级元素，需要一个明确的高度 */
}



.img-field {
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>