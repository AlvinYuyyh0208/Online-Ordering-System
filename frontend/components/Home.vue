<template>
  <!--引入container布局-->
  <el-container class="home-container">
    <!--头部-->
    <el-header>
      <div>
        <img src="../assets/pageback.jpg" alt/>
        <span>网上订餐系统</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏-->
      <el-aside width="200px">
        <div class="toggle-button" @click="toggleCollapase()">|||</div>
        <el-menu background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" :router="true"
        :default-active="activePath" unique-opened>

          <!--一级菜单-->
          <el-submenu :index="item.path+''" v-for="item in menuList" :key="item.id">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>{{ item.title }}</span>
            </template>

            <!--二级菜单-->
            <el-menu-item :index="it.path" v-for="it in item.sList" :key="it.id" @click="saveNavState(it.path)">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{ it.title }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--主体内容-->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import router from "../router";

export default {
  data() {
    return {
      menuList: [],
      activePath:'/welcome',
    }
  },

  /*onload事件*/
  created() {
    /*查询menulist*/
    this.getMenuList();
    this.activePath=window.sessionStorage.getItem('activePath');
  },

  methods: {
    /*安全退出*/
    logout() {
      window.sessionStorage.clear();  //清除session
      this.$router.push({path: "/login"});  //回到首页
    },

    async getMenuList() {

      const {data: res} = await this.$http.get("menus");
      console.log(res);
      if (res.flag !== 200) return this.$message.error("获取列表失败!")
      this.menuList = res.menus;
    },
    toggleCollapase() {
this.isCollapsed= !this.isCollapsed;
    },

    //保存路径
    saveNavState(activePath){
      window.sessionStorage.setItem('activePath',activePath);
      this.activePath=activePath;
    }

  }

}
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: #cbffd5;
  display: flex;
  justify-content: space-between;
  padding-left: 0%;
  color: #feffb7;
  font-size: 50px;
  align-items: center;

  > div {
    display: flex;
    align-items: center;
  }

  span {
    margin-left: 15px;
  }

}


.el-aside {
  background-color: cornsilk;

  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #cef5ff;
}

img {
  width: 80px;
  height: 80px;
}

/*按钮样式*/
.toggle-button {
  background-color: #ffd05c;
  font-size: 10px;
  line-height: 24px;
  color: #a6ff98;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
