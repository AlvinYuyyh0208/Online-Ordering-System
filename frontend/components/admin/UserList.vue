<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>用户列表
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrum-item :to="{path:'/home'}">首页</el-breadcrum-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--用户列表主体-->
    <el-card>

      <el-row :gutter="25">
        <!--搜索区域-->
        <!--搜索添加-->
        <el-col :span="10">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>


        <!--添加按钮-->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>
        </el-col>

      </el-row>

      <el-table :data="userList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="状态" prop="state">

          <template slot-scope="scope">
            <el-switch v-model="scope.row.state" @change="userStateChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button type="primary" icon="el-icon-edit" size="mini"  @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.id)"></el-button>


          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 size-change current-change-->
      <div>


        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="queryInfo.pageNum"
            :page-sizes="[1,2,5,100]"
            :page-size="queryInfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        ></el-pagination>
      </div>

    </el-card>


    <!-- 创建新用户对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules"
               ref="addFormRef" label-width="70px">
        <!-- 用户名 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户对话框 -->
    <el-dialog title="修改用户信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!-- 用户名 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <!-- 邮箱 -->
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {

  created() {
    this.getUserList();
  },

  data() {
    return {
      queryInfo: {
        query: "",   //查询信息
        pageStart: 1,    //当前页
        pageSize: 5,   //每页最大数
      },
      userList: [],    //用户列表
      total: 0,        //总记录数
      addDialogVisible: false,
      addForm: {
        username: '',
        password: '',
        email: ''
      },

      // 控制修改用户对话框显示/隐藏
      editDialogVisible:false,
      // 修改用户信息
      editForm:{},


      //表单验证

      addFormRules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {min: 5, max: 8, message: "长度在 5 到 8 个字符", trigger: "blur"}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 6, max: 8, message: "长度在 6 到 8 个字符", trigger: "blur"}
        ],
        email: [
          {required: true, message: "请输入邮箱", trigger: "blur"},
          {min: 5, max: 15, message: "请输入正确邮箱地址", trigger: "blur"}
        ],
      },



    }
  },
  methods: {
    //获取所有用户
    async getUserList() {
      const {data: res} = await this.$http.get("alluser", {params: this.queryInfo});
      this.userList = res.data;
      this.total = res.numbers;
    },

    //最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getUserList();
    },
    //触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getUserList();
    },

    async userStateChanged(userInfo) {
      console.log(userInfo);
      const {data: res} = await this.$http.put(`userstate?id=${userInfo.id}&state=${userInfo.state}`);
      if (res !== "success") {
        userInfo.id = !userInfo.id;
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
    },

    addDialogClosed() {
      this.$refs.addFormRef.resetFields();

    },

    addUser() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("addUser", this.addForm);
        if (res !== "success") {
          return this.$message.error("操作失败");

        }
        this.$message.success("操作成功");
        this.addDialogVisible = false;
        this.getUserList();
      });

    },


    async deleteUser(id) {

     const confirmResult=await this.$confirm('此操作将永久删除用户,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info("已取消删除");
      }
      const {data: res} = await this.$http.delete("deleteUser?id=" + id);
      if (res !== "success") {
        return this.$message.error("删除失败");

      }
      this.$message.success("删除成功");
      await this.getUserList();
    },

    // 展示修改框
    async showEditDialog(id){

      const {data:res} = await this.$http.get("getupdate?id="+id);
       // if (res !== "success") {
       // userinfo.state = !userinfo.state;
       // return this.$message.error("操作失败！！！");
       // }
       // this.$message.success("操作成功！！！");

      this.editForm = res;
      this.editDialogVisible = true;
    },
    // 关闭窗口
    editDialogClosed(){
      this.$refs.editFormRef.resetFields();
    },
    // 确认修改
    editUserInfo(){
      this.$refs.editFormRef.validate(async valid =>{
        console.log(valid);
        if( !valid ) return;
        // 发起请求
        const {data:res} = await this.$http.put("edituser",this.editForm);
        console.log(res);
        if (res != "success") return this.$message.error("操作失败！！！");
        this.$message.success("操作成功！！！");
        //隐藏
        this.editDialogVisible = false;
        this.getUserList();
      });
    },

  }



}
</script>

<style lang="less" scoped>
.el-breadcrumb {
  margin-bottom: 15px;
  font-size: 17px;
}

</style>