<template>
    <ContentField>
        <table class="table table-striped table-hover" style="text-align: center;">
            <thead>
                <tr>
                    <!-- Title Row -->
                    <td colspan="2" style="font-size: 24px; font-weight: bold;">King of Gomoku</td>
                </tr>
                <tr>
                    <th>Player A</th>
                    <th>Player B</th>
                    <th>Result</th>
                    <th>Time</th>
                    <th>Process</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.record.id" >
                    <td>
                        <img :src="record.a_photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.a_username }}</span>
                    </td>
                    <td>
                        <img :src="record.b_photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.b_username }}</span> 
                    </td>
                    <td>{{ record.result }}</td>
                    <td>{{ record.record.createTime }}</td>
                    <td>
                        <button @click="open_record_content(record.record.id)" type="button" class="btn btn-secondary">Replay</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <!--Pagination-->
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item" @click="click_page(-2)">
                <a class="page-link" href="#" >Previous</a>
                </li>
                <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)"><a class="page-link" href="#">{{ page.number }}</a></li>
                
                <li class="page-item" @click="click_page(-1)">
                    <a class="page-link" href="#">Next</a>
                </li>
            </ul>
        </nav>
    </ContentField>
</template>
<script>

import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery';
import router from '@/router/index';

export default{
    components:{
        ContentField
    },
    setup(){
        const store = useStore();
        let records = ref([]);
        let current_page = 1;
        let total_records = 0;
        let pages = ref([]);

        const click_page = page => {
            if( page === -2) page = current_page - 1 ;
            else if( page === -1 ) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_records / 10));
            if( page >= 1 && page <= max_pages){
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records / 10 ));
            let new_pages = [];
            for(let i = current_page - 2 ; i <= current_page + 2 ; i++){
                if( i >= 1 && i <= max_pages ){
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_pages;
        }

        console.log(total_records);

        const pull_page = page => {
            current_page = page;
            $.ajax({
                    url:"http://127.0.0.1:3000/record/getgomokulist/",
                    data:{
                        page,
                    },
                    type:"get",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    success(resp){
                        records.value = resp.records;
                        total_records = resp.records_count;
                        update_pages();
                    },
                    error(resp){
                        console.log(resp);
                    }
                })
        }
        pull_page(current_page);

        const open_record_content = recordId =>{
            for(const record of records.value){
                if( record.record.id === recordId ){
                    store.commit("updateIsRecord", true);
                    store.commit("updateGomokuSteps", {
                        gomoku_steps: record.record.steps,
                    });
                    store.commit("updateRecordLoser",record.record.loser);

                    router.push({
                        name: "GomokuRecord_content",
                        params:{
                            recordId
                        }
                    })
                    break;
                }
            }
        }

        return {
            records,
            open_record_content,
            pages,
            click_page,
        }
    }
}
</script>

<style  scoped>
img.record-user-photo {
    height: 4vh;
    width: 4vh;
    border-radius:50%;
    object-fit: cover;
}
.page-item.active .page-link {
    background-color: lightgray;
    border-color: lightgray;
}

</style>