import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../components/Login";
import Home from '../components/Home.vue'
import Welcome from "../components/Welcome.vue";
import UserList from "../components/admin/UserList.vue";
import OrderList from "../components/admin/OrderList";
import DesertList from "../components/admin/DesertList";
import SnackList from "../components/admin/SnackList";
import StapleList from "../components/admin/StapleList";
Vue.use(VueRouter)

const routes = [
  {
    path:"/",
    redirect:"login"
  },
{
  path:"/login",
  component:Login
},

  {
    path:"/home",
    component: Home,
    redirect: "/welcome",
    children:[
      {path:"/welcome",component:Welcome,},
      {path:"/user",component:UserList,},
      {path:"/order",component:OrderList,},
      {path:"/desert",component: DesertList},
      {path:"/snack",component:SnackList},
      {path: "/staple",component: StapleList}

    ]
  }

]

const router = new VueRouter({
  routes
})

router.beforeEach((to,from,next)=>{
  /**
   * to 将要访问
   * from 从哪访问
   */
  if(to.path=='/login') return next();
  //获取user
  const userFlag=window.sessionStorage.getItem("user");//取出当前用户
  if(!userFlag) return next('/login');
  next();
})

export default router
