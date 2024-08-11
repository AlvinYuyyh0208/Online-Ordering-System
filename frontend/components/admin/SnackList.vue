<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>甜品列表
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrum-item :to="{path:'/home'}">首页</el-breadcrum-item>
      <el-breadcrumb-item>食品管理</el-breadcrumb-item>
      <el-breadcrumb-item>小吃类</el-breadcrumb-item>
    </el-breadcrumb>

    <!--甜品类主体-->
    <el-card>

      <el-row :gutter="25">
        <!--搜索区域-->
        <!--搜索添加-->
        <el-col :span="10">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getSnackList">
            <el-button slot="append" icon="el-icon-search" @click="getSnackList"></el-button>
          </el-input>
        </el-col>


        <!--添加按钮-->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加甜品</el-button>
        </el-col>

      </el-row>

      <el-table :data="snackList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="甜品名" prop="snackname"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="人气" prop="popularity"></el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button type="primary" icon="el-icon-edit" size="mini"  @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteSnack(scope.row.id)"></el-button>


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


    <!-- 创建新甜品对话框 -->
    <el-dialog title="添加甜品" :visible.sync="addDialogVisible" width="50%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules"
               ref="addFormRef" label-width="70px">
        <!-- 甜品名 -->
        <el-form-item label="甜品名" prop="snackname">
          <el-input v-model="addForm.snackname"></el-input>
        </el-form-item>
        <!-- 价格 -->
        <el-form-item label="价格" prop="price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
        <!-- 人气 -->
        <el-form-item label="人气" prop="popularity">
          <el-input v-model="addForm.popularity"></el-input>
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSnack">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改甜品对话框 -->
    <el-dialog title="修改甜品信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!-- 甜品名 -->
        <el-form-item label="甜品名" prop="snackname">
          <el-input v-model="editForm.snackname" disabled></el-input>
        </el-form-item>
        <!-- 价格 -->
        <el-form-item label="价格" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <!-- 人气 -->
        <el-form-item label="人气" prop="popularity">
          <el-input v-model="editForm.popularity"></el-input>
        </el-form-item>
      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editSnackInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {

  created() {
    this.getSnackList();
  },

  data() {
    return {
      queryInfo: {
        query: "",   //查询信息
        pageStart: 1,    //当前页
        pageSize: 5,   //每页最大数
      },
      snackList: [],    //甜品列表
      total: 0,        //总记录数
      addDialogVisible: false,
      addForm: {
        snackname: '',
        password: '',
        email: ''
      },

      // 控制修改甜品对话框显示/隐藏
      editDialogVisible:false,
      // 修改甜品信息
      editForm:{},


      //表单验证

      addFormRules: {
        snackname: [
          {required: true, message: "请输入甜品名", trigger: "blur"},
          {min: 1, max: 8, message: "长度在 1 到 8 个字符", trigger: "blur"}
        ],
        price: [
          {required: true, message: "请输入价格", trigger: "blur"},
          {min: 1, max: 4, message: "长度在 1 到 4 个字符", trigger: "blur"}
        ],
        popularity: [
          {required: true, message: "请输入人气", trigger: "blur"},
          {min: 1, max: 2, message: "长度在 1 到 2 个字符", trigger: "blur"}
        ],
      },

    }
  },
  methods: {
    //获取所有甜品
    async getSnackList() {
      const {data: res} = await this.$http.get("allsnack", {params: this.queryInfo});
      this.snackList = res.data;
      this.total = res.numbers;
    },

    //最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getSnackList();
    },
    //触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getSnackList();
    },


    addDialogClosed() {
      this.$refs.addFormRef.resetFields();

    },

    addSnack() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("addSnack", this.addForm);
        if (res !== "success") {
          return this.$message.error("操作失败");

        }
        this.$message.success("操作成功");
        this.addDialogVisible = false;
        this.getSnackList();
      });

    },


    async deleteSnack(id) {

      const confirmResult=await this.$confirm('此操作将永久删除甜品,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info("已取消删除");
      }
      const {data: res} = await this.$http.delete("deleteSnack?id=" + id);
      if (res !== "success") {
        return this.$message.error("删除失败");

      }
      this.$message.success("删除成功");
      await this.getSnackList();
    },

    // 展示修改框
    async showEditDialog(id){

      const {data:res} = await this.$http.get("updateSnack?id="+id);
      // if (res !== "success") {
      // snackinfo.state = !snackinfo.state;
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
    editSnackInfo(){
      this.$refs.editFormRef.validate(async valid =>{
        console.log(valid);
        if( !valid ) return;
        // 发起请求
        const {data:res} = await this.$http.put("editsnack",this.editForm);
        console.log(res);
        if (res != "success") return this.$message.error("操作失败！！！");
        this.$message.success("操作成功！！！");
        //隐藏
        this.editDialogVisible = false;
        this.getSnackList();
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