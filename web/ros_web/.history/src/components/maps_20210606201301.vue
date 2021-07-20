<template>
  <div class="bodyBoxOut">
    <el-row>
      <el-col :span="4" v-for="map in maps" :key="map.name" :offset="1">
        <el-card :body-style="{ padding: '0px', height:'360px'}" shadow="hover" style="width: 260px;height: 320px;">
          <div style="padding: 6px;height: 310px;">
            <el-row style="position: relative;top: 30px;">
              <span style="float:right; weight:100px; height:150px; width: 150px; overflow: hidden; border: solid">
                <el-image 
                  class="map_image"
                  :src="map.url"
                  :fit = "'cover'"></el-image>
              </span>
            </el-row>
            <el-row>
              <el-row v-if="map.nameMode === 'show'">
                <font size="5">{{map.name}}</font>
                <el-button 
                type="primary" 
                icon="el-icon-edit" 
                circle 
                @click="map.nameMode = 'edit'"></el-button>
              </el-row>
              <el-row v-if="map.nameMode === 'edit'">
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
                  @click="editFin(map)"></el-button>
              </el-row>
            </el-row>
            <div style="position: relative;top: 45px;">
              <el-button type="danger" @click="deleteMap(map)">删除</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { post_getMap, post_changeMapName, post_removeMap } from "../api/request";
  export default {
    data() {
      return {
        maps: [
          {
            name: '',
            url: '',
            nameMode: 'show',
          }
        ],
        mapsPre: [],
      };
    },
    methods: {
      getMap() {
        post_getMap().then((res) => {
          let mapList = [];
          for (let i = 0; i < res.data.length; ++i) {
            mapList.push({name: res.data[i].mapName, url: res.data[i].mapUrl, nameMode: 'show'})
          }
          this.maps = mapList;
          this.mapsPre = mapList;
        })
      },
      deleteMap(map) {
        post_removeMap({
          name: map.name
        }).then((res) =>{
          if (res.code === 200) {
            this.getMap();
          }
        })
      },
      editFinMap(map) {
        map.nameMode = 'show';
        post_changeMapName({
          mapPreviousName: this.mapList[this.maps.indexOf(map)].name,
          mapNewName: map.name,
        }).then((res) => {
          if (res.code !== 200) {
            map.name = this.mapList[this.maps.indexOf(map)].name;
          }
        })
      }
    },
    mounted() {
      this.getMap();
    }
  }
</script>