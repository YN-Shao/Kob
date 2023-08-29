<template>
    <div class="container">
        <div class="row">
            <!-- User Info -->
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <!-- Create Button -->
                    <div class="card-header">
                        <span style="font-size:123%">My Bot</span>
                        <button type="button" class="btn btn-dark float-end" data-bs-toggle="modal" data-bs-target="#add-bot-button">
                            Create Bot
                        </button>
                        <!-- Modal of Create -->
                        <div class="modal fade" id="add-bot-button" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title">Create Bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- forms -->
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">Title</label>
                                            <input v-model="botadd.title" type="text" class="form-control" id="add-bot-title" placeholder="Please enter the title of Bot">
                                        </div>
                                        <div class="mb-3">
                                            <label for="dd-bot-description" class="form-label">Description</label>
                                            <textarea v-model="botadd.description" class="form-control" id="add-bot-description" rows="3" placeholder="Please enter the description of Bot"></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="dd-bot-code" class="form-label">Code</label>
                                            <VAceEditor
                                                v-model:value="botadd.content"
                                                
                                                lang="java"
                                                theme="textmate"
                                                style="height: 300px" />
                                        </div>
                                        <!-- End formss -->
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ botadd.error_message }}</div>
                                        <button type="button" class="btn btn-dark float-end" @click="add_bot">Confirm</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- End modal -->
                    </div>
                    <!-- Bot list -->
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Title</th>
                                    <th>Create Time</th>
                                    <th>Process</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.createTime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 6px;" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + bot.id" >Modify</button>
                                        <button type="button" class="btn btn-danger" @click="remove_bot(bot)">Delete</button>
                                        <!-- Modal of Update-->
                                        <div class="modal fade" :id="'update-bot-modal-' + bot.id" tabindex="-1" >
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title" >Update Bot</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <!-- form -->
                                                        <div class="mb-3">
                                                            <label for="update-bot-title" class="form-label">Title</label>
                                                            <input v-model="bot.title" type="text" class="form-control" id="update-bot-title" placeholder="Please enter the title of Bot">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-bot-description" class="form-label">Description</label>
                                                            <textarea v-model="bot.description" class="form-control" id="update-bot-description" rows="3" placeholder="Please enter the description of Bot"></textarea>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-bot-code" class="form-label">Code</label>
                                                            <VAceEditor
                                                                v-model:value="bot.content"
                                                                
                                                                lang="java"
                                                                theme="textmate"
                                                                style="height: 300px" 
                                                                :options="{
                                                                    enableBasicAutocompletion: true, 
                                                                    enableSnippets: true, 
                                                                    enableLiveAutocompletion: true,
                                                                    fontSize: 14, 
                                                                    tabSize: 2, 
                                                                    showPrintMargin: false, 
                                                                    highlightActiveLine: true,
                                                                    }"
                                                                    />
                                                        </div>
                                                        <!-- End form -->
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ bot.error_message }}</div>
                                                        <button type="button" class="btn btn-dark float-end" @click="update_bot(bot)">Confirm</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- End modal -->
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    /* th {
        text-align: center;
    }
    tbody {
        text-align: center;
    } */
    div.error-message {
        color:red;
    }

</style>

<script>
    import { ref,reactive } from 'vue'
    import $ from 'jquery'
    import { useStore } from 'vuex'
    import { Modal } from 'bootstrap/dist/js/bootstrap'
    import { VAceEditor } from "vue3-ace-editor"
    import ace from "ace-builds"
    import 'ace-builds/src-noconflict/mode-java'

    export default{
        components:{
            VAceEditor
        },
        setup() {
            ace.config.set(
                "basePath",
                "https://cdn.jsdelivr.net/npm/ace-builds@" + require("ace-builds").version + "/src-noconflict/")

            const store = useStore();
            let bots = ref([]);

            const botadd = reactive({
                title: "",
                description: "",
                content: "",
                error_message: "",
            })

            const refresh_bots = () => {
                $.ajax({
                    url:"http://127.0.0.1:3000/user/bot/getlist/",
                    type:"get",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    success(resp){
                        bots.value = resp;
                    }
                })
            }

            refresh_bots();

            const add_bot = () => { 
                botadd.error_message = "";
                $.ajax({
                    url:"http://127.0.0.1:3000/user/bot/add/",
                    type:"post",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    data:{
                        title: botadd.title,
                        description: botadd.description,
                        content: botadd.content,
                    },
                    success(resp){
                        if(resp.error_message === "success") {
                            botadd.title = "";
                            botadd.description = "";
                            botadd.content = "";
                            Modal.getInstance("#add-bot-button").hide();  
                            refresh_bots();
                        }
                        else{
                            botadd.error_message = resp.error_message;
                        }
                    }
                })
            }

            const remove_bot = (bot) => {
                $.ajax({
                    url:"http://127.0.0.1:3000/user/bot/remove/",
                    type:"post",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    data:{
                        bot_id: bot.id,
                    },
                    success(){
                        refresh_bots();
                    }
                })
            }

            const update_bot = (bot) => {
                botadd.error_message = "";
                $.ajax({
                    url: "http://127.0.0.1:3000/user/bot/update/",
                    type: "post",
                    headers: {
                        Authorization: "Bearer "+ store.state.user.token,
                    },
                    data: {
                        bot_id: bot.id,
                        title: bot.title,
                        description: bot.description,
                        content: bot.content,
                    },
                    success(resp){
                        if(resp.error_message === "Update success"){
                            Modal.getInstance('#update-bot-modal-' + bot.id).hide();
                            refresh_bots();
                        }
                        else{
                            bot.error_message = resp.error_message;
                        }
                    }
                })
            }


            return {
                bots,
                botadd,
                refresh_bots,
                add_bot,
                remove_bot,
                update_bot,
            }
        }
    }
</script>
