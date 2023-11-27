
<template>
  <div class="container">
    <!-- left third part -->
    <div class="left-section">
      <!-- The above is a picture -->
      <div class="image-container">
        <img :src="require('@/assets/image/snake.jpg')" >
      </div>
      <!-- Below is a Play button -->
      <div class="button-container">
        <button type="button" class="btn btn-light" @click="goToPKIndex">Play now</button>

      </div>
    </div>
    <!-- middle part -->
    <div style="flex-grow: 1;"></div>

    <div class="description-container">
    <div class="card">
      <div class="card-header">
        About The Game
      </div>
      <div class="card-body">
        <h5 class="card-title" style="font-weight: bold;">King of Snakes</h5>
<p class="card-text">
  <strong>Become the smartest snake!</strong> 
  <br><strong style="color: green;">Game Rules:</strong> The snake will die if its head collides with an obstacle or any part of another snake's body.
  <br><strong style="color: blue;">Manual Play:</strong> Use <span style="font-family: 'Courier New', monospace;">ASDW</span> to control the snake's movement.
  <br><strong style="color: red;">Bot Play:</strong> Implement <span style="font-family: 'Courier New', monospace;">BotInterface</span>, which includes <span style="font-family: 'Courier New', monospace;">Integer nextMove(String input)</span>function, where <span style="font-family: 'Courier New', monospace;">input</span> consists of 7 parts：
  <br><strong style="color: purple;">Return Values:</strong> A return value of 0-4 represents the four directions: left, up, right, down.
  <br><strong style="color: orange;">MapString():</strong> A 13x14 center-symmetric matrix, where 0 represents an empty space and 1 represents an obstacle.
  <br><strong style="color: brown;">getSx, getSy:</strong> Retrieve the head positions for both players.
  <br><strong style="color: teal;">getStepString():</strong> Retrieve the positions of the body.
  <br><span style="font-family: 'Courier New', monospace;">Input : MapString() + "#" +       
      me.getSx() + "#" +
      me.getSy() + "#(" +
      me.getStepString() + ")#" +
      you.getSx() + "#" +
      you.getSy() + "#(" +
      you.getStepString() + ")</span>";
</p>


      </div>
    </div>
  </div>

    <!-- The ranking section on the right -->
    <div class="ranklist-container">
      <ContentField>
        <table class="table table-striped table-hover custom-table">
          <thead>
            <tr>
              <th class="player-col">Player</th>
              <th class="rank-col">Rank of Snake</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td class="player-col">
                <img :src="user.photo" alt="" class="record-user-photo">
                &nbsp;
                <span class="record-user-username">{{ user.username }}</span>
              </td>
              <td class="rank-col">{{ user.rating }}</td>
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
          router.push({ name: "PK_index" });
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
                        gameId: 1
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
  align-items: flex-start;
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
  margin-top: 1cm;
}

.button-container button {
  width: 100%;
  font-size: 1.2rem;
  padding: 10px;
}

.ranklist-container {
  flex-basis: 33%;  
  max-width: 33%;  
  margin-left: 1rem;  
}
</style>

