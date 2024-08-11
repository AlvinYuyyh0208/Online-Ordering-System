<template xmlns:el-col="http://www.w3.org/1999/html">
  <div>订单列表
    <!--面包屑导航-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrum-item :to="{path:'/home'}">首页</el-breadcrum-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--订单列表主体-->
    <el-card>

      <el-row :gutter="25">
        <!--搜索区域-->
        <!--搜索添加-->
        <el-col :span="10">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getOrderList">
            <el-button slot="append" icon="el-icon-search" @click="getOrderList"></el-button>
          </el-input>
        </el-col>


        <!--添加按钮-->
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加订单</el-button>
        </el-col>

      </el-row>

      <el-table :data="orderList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="订单名称" prop="ordername"></el-table-column>
        <el-table-column label="数量" prop="num"></el-table-column>
        <el-table-column label="订餐者" prop="user"></el-table-column>
        <el-table-column label="下单时间" prop="time"></el-table-column>
        <el-table-column label="价格" prop="price"></el-table-column>
        <el-table-column label="状态" prop="state">

          <template slot-scope="scope">
            <el-switch v-model="scope.row.state" @change="orderStateChanged(scope.row)"></el-switch>
          </template>
        </el-table-column>

        <el-table-column label="备注" prop="note">

        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button type="primary" icon="el-icon-edit" size="mini"  @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteOrder(scope.row.id)"></el-button>


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


    <!-- 创建新订单对话框 -->
    <el-dialog title="添加订单" :visible.sync="addDialogVisible" width="50%"
               @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules"
               ref="addFormRef" label-width="70px">
        <!-- 订单名称 -->
        <el-form-item label="订单名称" prop="ordername">
          <el-input v-model="addForm.ordername"></el-input>
        </el-form-item>
        <!-- 数量 -->
        <el-form-item label="数量" prop="num">
          <el-input v-model="addForm.num"></el-input>
        </el-form-item>
        <!-- 订餐者 -->
        <el-form-item label="订餐者" prop="user">
          <el-input v-model="addForm.user"></el-input>
        </el-form-item>
        <!--时间-->
        <el-form-item label="时间" prop="time">
          <el-input v-model="addForm.time"></el-input>
        </el-form-item>
        <!--价格-->
        <el-form-item label="价格" prop="price">
          <el-input v-model="addForm.price"></el-input>
        </el-form-item>
        <!--备注-->
        <el-form-item label="备注" prop="note">
          <el-input v-model="addForm.note"></el-input>
        </el-form-item>

      </el-form>

      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrder">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 修改订单对话框 -->
    <el-dialog title="修改订单信息" :visible.sync="editDialogVisible" width="50%" @colse="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <!-- 订单名称 -->
        <el-form-item label="订单名称" prop="ordername">
          <el-input v-model="editForm.ordername" disabled></el-input>
        </el-form-item>
        <!-- 数量 -->
        <el-form-item label="数量" prop="num">
          <el-input v-model="editForm.num"></el-input>
        </el-form-item>
        <!-- 订餐者 -->
        <el-form-item label="订餐者" prop="user">
          <el-input v-model="editForm.user"></el-input>
        </el-form-item>
        <!-- 时间 -->
        <el-form-item label="时间" prop="time">
          <el-input v-model="editForm.time"></el-input>
        </el-form-item>
        <!-- 价格 -->
        <el-form-item label="价格" prop="price">
          <el-input v-model="editForm.price"></el-input>
        </el-form-item>
        <!-- 备注 -->
        <el-form-item label="备注" prop="note">
          <el-input v-model="editForm.note"></el-input>
        </el-form-item>




      </el-form>
      <!--内容底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editOrderInfo">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
export default {

  created() {
    this.getOrderList();
  },

  data() {
    return {
      queryInfo: {
        query: "",   //查询信息
        pageStart: 1,    //当前页
        pageSize: 5,   //每页最大数
      },
      orderList: [],    //订单列表
      total: 0,        //总记录数
      addDialogVisible: false,
      addForm: {
        ordername: '',
        num: '',
        user: '',
        time:'',
        price:'',
        note:'',
      },

      // 控制修改订单对话框显示/隐藏
      editDialogVisible:false,
      // 修改订单信息
      editForm:{},


      //表单验证

      addFormRules: {
        ordername: [
          {required: true, message: "请输入订单名称", trigger: "blur"},
          {min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "blur"}
        ],
        num: [
          {required: true, message: "请输入数量", trigger: "blur"},
          {min: 1, max: 4, message: "长度在 1 到 4 个字符", trigger: "blur"}
        ],
        user: [
          {required: true, message: "请输入订餐者", trigger: "blur"},
          {min: 3, max: 8, message: "长度在 " +
                " 到 8 个字符", trigger: "blur"}
        ],
        time: [
          {required: true, message: "请输入下单时间", trigger: "blur"},
          {min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "blur"}
        ],
        price: [
          {required: true, message: "请输入价格", trigger: "blur"},
          {min: 1, max: 4, message: "长度在 1 到 4 个字符", trigger: "blur"}
        ],
        note: [
          {required: true, message: "请输入备注", trigger: "blur"},
          {min: 1, max: 20, message: "长度在 1 到 20 个字符", trigger: "blur"}
        ],
      },



    }
  },
  methods: {
    //获取所有订单
    async getOrderList() {
      const {data: res} = await this.$http.get("allorder", {params: this.queryInfo});
      this.orderList = res.data;
      this.total = res.numbers;
    },

    //最大数
    handleSizeChange(newSize) {
      this.queryInfo.pageSize = newSize;
      this.getOrderList();
    },
    //触发动作
    handleCurrentChange(newPage) {
      this.queryInfo.pageNum = newPage;
      this.getOrderList();
    },

    async orderStateChanged(orderInfo) {
      console.log(orderInfo);
      const {data: res} = await this.$http.put(`orderstate?id=${orderInfo.id}&state=${orderInfo.state}`);
      console.log(res);
      if (res !== "success") {
        orderInfo.id = !orderInfo.id;
        return this.$message.error("操作失败");
      }
      this.$message.success("操作成功");
    },

    addDialogClosed() {
      this.$refs.addFormRef.resetFields();

    },

    addOrder() {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("addOrder", this.addForm);
        if (res !== "success") {
          return this.$message.error("操作失败");

        }
        this.$message.success("操作成功");
        this.addDialogVisible = false;
        this.getOrderList();
      });

    },


    async deleteOrder(id) {

      const confirmResult=await this.$confirm('此操作将永久删除订单,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)
      if (confirmResult !== 'confirm') {
        return this.$message.info("已取消删除");
      }
      const {data: res} = await this.$http.delete("deleteOrder?id=" + id);
      if (res !== "success") {
        return this.$message.error("删除失败");

      }
      this.$message.success("删除成功");
      await this.getOrderList();
    },

    // 展示修改框
    async showEditDialog(id){

      const {data:res} = await this.$http.get("updateOrder?id="+id);
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
    editOrderInfo(){
      this.$refs.editFormRef.validate(async valid =>{
        console.log(valid);
        if( !valid ) return;
        // 发起请求
        const {data:res} = await this.$http.put("editorder",this.editForm);
        console.log(res);
        if (res != "success") return this.$message.error("操作失败！！！");
        this.$message.success("操作成功！！！");
        //隐藏
        this.editDialogVisible = false;
        this.getOrderList();
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