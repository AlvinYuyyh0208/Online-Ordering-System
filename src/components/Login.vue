<template>
  <div class="login_container">
    <!--登录块-->
    <div class="login_box">
      <!--头像-->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt/>
      </div>
      <!--表单区域-->
      <el-form ref="loginFormRef" :rules="loginrules" :model="loginForm" class="login_form" label-width="0">
        <el-form-item prop="username">
          <!--用户名-->

          <el-input v-model="loginForm.username" prefix-icon="el-icon-user"></el-input>

        </el-form-item>
        <!--密码-->
        <el-form-item prop="password">


          <el-input v-model="loginForm.password" prefix-icon="el-icon-fork-spoon" type="password"></el-input>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="btns">
          <el-button type="primary" @click="login()">登录</el-button>
          <el-button type="info" @click="resetLoginForm()">重置</el-button>
        </el-form-item>
      </el-form>

    </div>

  </div>
</template>
<script>
export default {
  data() {
    return {
      //表单数据
      loginForm: {
        username: "admin",
        password: "123456"
      },

      //验证
      loginrules: {
        //校验用户名
        username: [
          {required: true, message: '用户名为必填项', trigger: 'blur'},  //必填项验证
          {min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur'}   //长度验证
        ],

        //校验密码
        password: [
          {required: true, message: '用户密码为必填项', trigger: 'blur'},  //必填项验证
          {min: 6, max: 10, message: '长度在 6~10 个字符', trigger: 'blur'}   //长度验证
        ],
      },
    };
  },
  methods: {
    /*重置表单内容*/
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },

    /*登录方法*/
    login() {
      /*验证校验规则*/
      this.$refs.loginFormRef.validate(async valid => {
        if (!valid) return;    //访问失败
        const {data: res} = await this.$http.post("login",this.loginForm);
        if (res.flag === "ok") {
          this.$message.success("操作成功");
          this.$router.push({path: "/home"});   //页面路由跳转
          console.log(res.user);
          window.sessionStorage.setItem("user",res.user); //存储user对象

        } else {
          this.$message.error("操作失败");
        }
      })
    }
  }
}

</script>
<style lang="less" scoped>

.login_container {
  /*background-color: #ffd05c;*/
  /*background-image: image("../assets/logo.png");*/
  /*height: 100%;*/

  background: url("../assets/ms.jpg") no-repeat;
  height: 100%;
  width: 100%;
  background-size: cover;//全屏展示
}

.login_box {
  opacity: 0.8;
  width: 450px;
  height: 300px;
  background-color: rgb(243, 154, 21);
  border-radius: 10px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar_box {
  width: 130px;
  height: 130px;
  border: 1px solid #ffaf96;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ffe9e4;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffdbc6;

}

img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #a6ff98;
}

.btns {
  display: flex;
  justify-content: flex-end;

}

.login_form {
  position: absolute;
  bottom: 0%;
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}
</style>