export default {
    state: {
        a_photo: "",
        a_username: "",
        b_photo: "",
        b_username: "",
    },
    getters: {
    },
    mutations: {
      updateUser(state,data){
          a_photo = data.a_photo;
          a_username = data.a_username;
          b_photo = data.b_photo;
          b_username = data.b_username;
      },
    },
    actions: {
    },
    modules: {
    
    }
  }
  