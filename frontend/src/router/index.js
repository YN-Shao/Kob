import { createRouter, createWebHistory } from 'vue-router'
import SnakeIntroductionView from '../views/Introduction/SnakeIntroductionView'
import GomokuIntroductionView from '../views/Introduction/GomokuIntroductionView'
import PkIndexView from '../views/PK/PkIndexView'
import PkGomokuIndexView from '../views/PK/PkGomokuIndexView'
import RanklistIndexView from '../views/RankList/RanklistIndexView'
import GomokuRecordContentView from '../views/Record/GomokuRecordContentView'
import GomokuRecordIndexView from '../views/Record/GomokuRecordIndexView'
import RecordContentView from '../views/Record/RecordContentView'
import RecordIndexView from '../views/Record/RecordIndexView'
import NotfoundView from '../views/Error/NotfoundView'
import UserbotIndexView from '../views/User/Bots/UserbotIndexView'
import UserAccountLoginView from '../views/User/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/User/account/UserAccountRegisterView'

//userprofile2
import UserProfileView from  '../views/Profile/UserProfileView'
import HomeIndexView from '../views/Home/HomeIndexView'
import CommunityIndexView from  '../views/Community/CommunityIndexView'
import store from'../store/index'
import UserInformationChange from "../views/Profile/UserInformationChange";
 
const routes = [
  {
    path: "/",
    name : "home",
    meta:{
      requestAuth: true, //Add a meta domain to each page to determine whether authorization is required
    },
    component:HomeIndexView,
  },
  {
    path: "/",
    name : "home",
    meta:{
      requestAuth: true, //Add a meta domain to each page to determine whether authorization is required
    },
    component:HomeIndexView,
  },

  {
    path : "/Introduction/gomoku/",
    name : "Introduction_Gomoku",
    meta:{
      requestAuth: true, 
    },
    component:GomokuIntroductionView,
  },
  {
    path : "/Introduction/snake/",
    name : "Introduction_Snake",
    meta:{
      requestAuth: true, 
    },
    component:SnakeIntroductionView,
  },

  {
    path : "/PK/",
    name : "PK_index",
    meta:{
      requestAuth: true, 
    },
    component:PkIndexView,
  },
  {
    path : "/PK/Gomoku/",
    name : "PK_Gomoku_index",
    meta:{
      requestAuth: true, 
    },
    component:PkGomokuIndexView,
  },
  {
    path : "/404/",
    name : "404",
    meta:{
      requestAuth: false, 
    },
    component:NotfoundView,
  },

  {
    path : "/record/",
    name : "record_index",
    meta:{
      requestAuth: true, 
    },
    component: RecordIndexView,
  },
  {
    path : "/record/:recordId/",
    name : "record_content",
    meta:{
      requestAuth: true, 
    },
    component: RecordContentView,
  },

  {
    path : "/GomokuRecord/",
    name : "GomokuRecord_index",
    meta:{
      requestAuth: true, 
    },
    component: GomokuRecordIndexView,
  },
  {
    path : "/GomokuRecord/:recordId/",
    name : "GomokuRecord_content",
    meta:{
      requestAuth: true, 
    },
    component: GomokuRecordContentView,
  },


  {
    path : "/User/Bots/",
    name : "user_bot_index",
    meta:{
      requestAuth: true, 
    },
    component:UserbotIndexView,
  },


  //userprofile
  {
    path : "/User/changeInfo/",
    name : "user_profile_change",
    meta:{
      requestAuth: true, 
    },
    component:UserInformationChange,
  },
  {
    path : "/User/Profile/:userId/",
    name : "user_profile",
    meta:{
      requestAuth: true, 
    },
    component:UserProfileView,
  },



  {
    path : "/ranklist/",
    name : "RankList_index",
    meta:{
      requestAuth: true, 
    },
    component:RanklistIndexView,
  },
  {
    path : "/community/",
    name : "community_index",
    meta:{
      requestAuth: true, 
    },
    component:CommunityIndexView,
  },
  {
    path : "/user/account/login/",
    name : "user_account_login",
    meta:{
      requestAuth: false, 
    },
    component:UserAccountLoginView,
  }, 
  {
    path : "/user/account/register/",
    name : "user_account_register",
    meta:{
      requestAuth: false, 
    },
    component:UserAccountRegisterView,
  },
  {
    path: "/:catchAll(.*)",
    redirect:"404",
  }
  ]
  
const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
    // Redirect to login page when not logged in
    if(to.meta.requestAuth && !store.state.user.is_login){
      next({name: "user_account_login"})
    }else{
      next();
    }
  
})

export default router