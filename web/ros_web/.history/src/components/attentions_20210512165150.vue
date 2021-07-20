<template>
  <div class="bodyBoxOut">
    <div v-if="type === 'build'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
          :subfield = "false"    
          :defaultOpen = "'preview'"
          :toolbarsFlag = "false"
          :editable="false"
          :scrollStyle="false"
          :boxShadow = "true"
        ></mavon-editor>
      </div>
      <el-row :gutter="20" type="flex" justify="space-around">
        <el-col :span="4">
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始建图</el-button>
        </el-col>
      </el-row>
    </div>
    <div v-if="type === 'mark'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
          :subfield = "false"    
          :defaultOpen = "'preview'"
          :toolbarsFlag = "false"
          :editable="false"
          :scrollStyle="false"
          :boxShadow = "true"
        ></mavon-editor>
      </div>
      <el-row :gutter="20" type="flex" justify="space-around">
        <el-col :span="4">
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="startMark">开始标注</el-button>
        </el-col>
      </el-row>
      <el-dialog
        title="请选择地图"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <el-form :model="form" @submit.native.prevent>
          <el-form-item label="地图">
            <el-select v-model="form.mapName" filterable clearable placeholder="请选择地图">
              <el-option
                v-for="map in maps"
                :key="map.name"
                :label="map.name"
                :value="map.name">
                <span style="float:left"> {{map.name}} </span>
                <span style="float:right">
                  <el-image 
                    :src="map.url"
                    :fit = "'fill'"></el-image>
                </span>
              </el-option>  
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="back(next_component)">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div v-if="type === 'serve'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
          :subfield = "false"    
          :defaultOpen = "'preview'"
          :toolbarsFlag = "false"
          :editable="false"
          :scrollStyle="false"
          :boxShadow = "true"
        ></mavon-editor>
      </div>
      <el-row :gutter="20" type="flex" justify="space-around">
        <el-col :span="4">
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="startService">开始服务</el-button>
        </el-col>
      </el-row>
      <el-dialog
        title="请选择地图"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <el-form :model="form" @submit.native.prevent>
          <el-form-item label="地图">
            <el-select v-model="form.mapName" clearable placeholder="请选择地图">
              <el-option
                v-for="map in maps"
                :key="map.name"
                :label="map.name"
                :value="map.name">
                <span style="float:left"> {{map.name}} </span>
                <span style="float:right">
                  <el-image 
                    :src="map.url"
                    :fit = "'fill'">
                    <div slot="error" class="image-slot">
                      <i class="el-icon-picture-outline"></i>
                    </div>
                  </el-image>
                </span>
              </el-option>  
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="back(next_component)">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {post_createMap, post_startMark, post_startService, post_getMap} from '../api/request';
  export default {
   data() {
     return {
       haveRead: false,
       attention: '',
       dialogVisible: false,
       form: {
         mapName: '',
       },
       maps: [
         {
           name: '',
           url: '',
         }
       ],
       mapLoading: false,
     }
   },
   props: {
     type: {
       type: String,
       require: true,
     }
   },
   methods: {
     back(next_component) {
       switch (this.type) {
         case 'build':
           if(this.haveRead === true) {
              post_createMap().then(() => {
                this.$emit('next-step-click', next_component);
              });
              // this.$notify({
              //   title: '开始建图',
              //   message: '请跟随提示在Rviz界面完成建图',
              //   type: 'success',
              //   showClose: false,
              //   duration: 2000,
              // })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                showClose: false,
                duration: 2000,
              })
            }
            break;
          case 'mark':
            post_startMark({
              name: this.form.mapName,
            }).then(() => {
              this.$emit('next-step-click', next_component);
              this.$cookie.set('nowMap', {
                name: this.form.mapName,
              })
            });
            // this.$notify({
            //   title: '开始标注',
            //   message: '请跟随提示在Rviz界面完成标注',
            //   type: 'success',
            //   showClose: false,
            //   duration: 2000,
            // })
            break;
          case 'serve':
            post_startService({
              name: this.form.mapName,
            }).then(() => {
              this.$emit('next-step-click', next_component);
              this.$cookie.set('nowMap', {
                name: this.form.mapName,
              })
            });
            // this.$notify({
            //   title: '开始服务',
            //   message: '请跟随提示使用服务',
            //   type: 'success',
            //   showClose: false,
            //   duration: 2000,
            // })
            break;
          default:
            this.$notify.error({
              title: '错误',
              message: '出现未知错误',
              showClose: false,
              duration: 2000,
            })
       }
     },
     get_attention_data() {
       if (this.type === 'build') {
         this.attention = '## Building Attention';
       } else if (this.type === 'mark') {
         this.attention = '## Marking Attention';
       } else {
         this.attention = '## Serving Attention';
       }
     },
     handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            this.form.name = '';
            done();
          }).catch(()=>{})
      },
      startMark() {
        if(this.haveRead === true) {
          this.dialogVisible = true;
          this.mapLoading = true;
          post_getMap().then((res) => {
            console.log(res.data);
            this.maps = res.data;
            this.mapLoading = false;
          })
        } else {
          this.$notify({
            title: '警告',
            message: '请先阅读注意事项',
            type: 'warning',
            showClose: false,
            duration: 2000,
          })
        }
      },
      startService() {
        if(this.haveRead === true) {
          this.dialogVisible = true;
          this.mapLoading = true;
          post_getMap().then((res) => {
            let mapList = [];
            for (let i = 0; i < res.data.length; ++i) {
              mapList.push({name: res.data[i].mapName, url: res.data[i].mapUrl})
            }
            this.maps = mapList;
            this.mapLoading = false;
          })
        } else {
          this.$notify({
            title: '警告',
            message: '请先阅读注意事项',
            type: 'warning',
            showClose: false,
            duration: 2000,
          })
        }
      }
   },
   mounted() {
     this.get_attention_data();
   },
   computed: {
     next_component() {
       if(this.type === 'build') {
         return 'building';
       } else if(this.type === 'mark') {
         return 'marking';
       } else {
         return 'serving';
       }
     }
   }
  }
</script>
