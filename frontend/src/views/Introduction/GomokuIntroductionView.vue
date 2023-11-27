
<template>
  <div class="container">
    <!-- left third part -->
    <div class="left-section">
      
      <div class="image-container">
        <img :src="require('@/assets/image/GomokuBack.jpeg')" >
      </div>
      
      <div class="button-container">
        <button type="button" class="btn btn-light" @click="goToPKIndex">Play now</button>

      </div>
    </div>
    <!-- Middle -->
    <div style="flex-grow: 1;"></div>

    <div class="description-container">
    <div class="card">
      <div class="card-header">
        About The Game
      </div>
      <div class="card-body">
        <h5 class="card-title">Gomoku Game</h5>
        <p class="card-text">
          <strong>Become the best Gomoku player!</strong> 
          <br><strong style="color: green;">Game Rules:</strong> Who ever has 5 consecutive pieces of the same colour wins.
          <br><strong style="color: blue;">Manual Play:</strong> Click on the Gomoku board to land a piece.
          <br><strong style="color: red;">Bot Play:</strong> Implement <span style="font-family: 'Courier New', monospace;">BotInterface</span>, which includes <span style="font-family: 'Courier New', monospace;">Integer nextMove(String input)</span>function. Input contains all the previously valid step and your colour.
          <br><span style="font-family: 'Courier New', monospace;">Input : "{[x,y,colour], [x,y,colour]},colour"</span>";

          <br><strong style="color: purple;">Return Values:</strong> Return the next step in the format "x,y,colour".
        </p>
      </div>
    </div>
  </div>

    <!-- Rank -->
    <div class="ranklist-container">
      <ContentField>
        <table class="table table-striped table-hover custom-table">
          <thead>
            <tr>
              <th class="player-col">Player</th>
              <th class="rank-col">Rank of Gomoku</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td class="player-col">
                <img :src="user.photo" alt="" class="record-user-photo">
                &nbsp;
                <span class="record-user-username">{{ user.username }}</span>
              </td>
              <td class="rank-col">{{ user.gomokuRating }}</td>
            </tr>
          </tbody>
        </table>
        <!-- Pagination -->
        <nav aria-label="Page navigation example">
          <ul class="pagination justify-content-center">
            <li class="page-item" @click="click_page(-2)">
              <a class="page-link" href="#">Previous</a>
            </li>
            <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number" @click="click_page(page.number)">
              <a class="page-link" href="#">{{ page.number }}</a>
            </li>
            <li class="page-item" @click="click_page(-1)">
              <a class="page-link" href="#">Next</a>
            </li>
          </ul>
        </nav>
      </ContentField>
    </div>
  </div>
</template>

<script>

import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery';
import { useRouter } from 'vue-router';

export default{
    components:{
        ContentField
    },
    setup(){
        const store = useStore();
        let users = ref([]);
        let current_page = 1;
        let total_users = 0;
        let pages = ref([]);


        const router = useRouter();

        const goToPKIndex = () => {
          router.push({ name: "PK_Gomoku_index" });
        }

        const click_page = page => {
            if( page === -2) page = current_page - 1 ;
            else if( page === -1 ) page = current_page + 1;
            let max_pages = parseInt(Math.ceil(total_users / 10));
            if( page >= 1 && page <= max_pages){
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users / 10 ));
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

        console.log(total_users);

        const pull_page = page => {
            current_page = page;
            $.ajax({
                    url:"http://127.0.0.1:3000/ranklist/getlist/",
                    data:{
                        page,
                        gameId: 2
                    },
                    type:"get",
                    headers:{
                        Authorization:"Bearer "+ store.state.user.token,
                    },
                    success(resp){
                        users.value = resp.users;
                        total_users = resp.users_count;
                        update_pages();
                    },
                    error(resp){
                        console.log(resp);
                    }
                })
        }
        pull_page(current_page);


        return {
            users,
            pages,
            click_page,
            goToPKIndex
        }
    }
}
</script>

<style scoped>
  .custom-table {
    width: 100%;
    text-align: center;
  }

  img.record-user-photo {
    border-radius: 50%;
    height: 4vh;
    width: 4vh;
    object-fit: cover;
  }

  .page-item.active .page-link {
    background-color: lightgray;
    border-color: lightgray;
  }

  .container {
  display: flex;
  justify-content: space-between; 
}

.left-section {
  flex-basis: 30%; 
  max-width: 30%;  
}

.image-container img {
  width: 100%;
  margin-top: 0.8cm;
}

.description-container {
  flex-basis: 38%;  
  max-width: 38%;  
  padding: 1rem;
}

.button-container {
  text-align: center;  
  margin-top: 2cm;
}

.button-container button {
  width: 100%;
  font-size: 1.2rem;
  padding: 12px;
}

.ranklist-container {
  flex-basis: 30%;  
  max-width: 30%;  
  margin-left: 1rem;  
}
</style>

