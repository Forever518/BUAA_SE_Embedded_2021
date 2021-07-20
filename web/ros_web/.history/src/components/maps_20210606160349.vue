<template>
  <div class="bodyBoxOut">
    <el-row>
      <el-col :span="4" v-for="map in maps" :key="map.pid" :offset="1">
        <el-card :body-style="{ padding: '0px', height:'360px'}" shadow="hover" style="width: 260px;height: 320px;">
          <div style="padding: 6px;height: 310px;">
            <div style="position: relative;top: 30px;">
              <span style="float:right; weight:100px; height:150px; width: 150px; overflow: hidden; border: solid">
                <el-image 
                  class="map_image"
                  :src="map.url"
                  :fit = "'cover'"></el-image>
              </span>
            </div>
            <div>
              <div v-if="nameMode === 'show'">
                <font size="5">{{map.name}}</font>
                <el-button 
                type="primary" 
                icon="el-icon-edit" 
                circle 
                @click="editMapName"></el-button>
              </div>
              <div v-if="nameMode === 'edit'">
                <el-input 
                  type="text" 
                  placeholder="请输入地图名" 
                  v-model="map.name" 
                  maxlength="50" 
                  show-word-limit></el-input>
                <el-button 
                  type="success" 
                  icon="el-icon-check" 
                  circle 
                  @click="editNameFin"></el-button>
              </div>
            </div>
            <div style="position: relative;top: 45px;">
              <el-button type="danger" @click="deleteMap">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { post_getMap } from "../api/request";
  export default {
    data() {
      return {
        maps: [
          {
            name: '',
            url: '',
          }
        ]
      };
    },
    methods: {
      getMap() {
        post_getMap().then((res) => {
          let mapList = [];
          for (let i = 0; i < res.data.length; ++i) {
            mapList.push({name: res.data[i].mapName, url: res.data[i].mapUrl})
          }
          this.maps = mapList;
        })
      }
    },
    mounted() {

    }
  }
</script>