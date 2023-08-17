import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/PK/PkIndexView'
import RanklistIndexView from '../views/RankList/RanklistIndexView'
import RecordContentView from '../views/Record/RecordContentView'
import NotfoundView from '../views/Error/NotfoundView'
import RecordIndexView from '../views/Record/RecordIndexView'
import UserbotIndexView from '../views/User/Bots/UserbotIndexView'
import UserAccountLoginView from '../views/User/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/User/account/UserAccountRegisterView'
import store from'../store/index'
 
const routes=[
  {
    path: "/",
    name : "home",
    redirect :"/PK/",
    meta:{
      requestAuth: true, //给每个页面增加一个meta域，判断是否需要授权
    }
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
    path : "/User/Bots/",
    name : "user_bot_index",
    meta:{
      requestAuth: true, 
    },
    component:UserbotIndexView,
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
    // 未登录时重定向到登陆页面
    if(to.meta.requestAuth && !store.state.user.is_login){
      next({name: "user_account_login"})
    }else{
      next();
    }
  
})

export default router
