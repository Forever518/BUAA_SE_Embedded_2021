<template>
  <div class="bodyBoxOut">
    <el-row>
      <el-col :span="4" v-for="map in maps" :key="map.name" :offset="1">
        <el-card :body-style="{padding: '0px'}" shadow="hover" style="width: 300px">
          <!-- style="width: 260px;height: 320px;" -->
          <div style="padding: 6px 20px;">
            <div style="margin: 0 auto; overflow: hidden; zoom: 1">
              <span style="float:right; weight:100px; height:150px; width: 150px; overflow: hidden; border: solid; margin-right: auto; margin-left: auto;">
                <el-image 
                  class="map_image"
                  :src="map.url"
                  :fit = "'cover'"></el-image>
              </span>
              <span style="clear: both"></span>
            </div>
            <div style="padding: 15px">
              <el-row v-if="map.nameMode === 'show'" type="flex" justify="space-around" style="height: 40px">
                <el-col :span="6" style="line-height: 40px">地图名:</el-col>
                <el-col :span="12" style="line-height: 40px">{{map.name}}</el-col>
                <el-col :span="6"><el-button 
                type="primary" 
                icon="el-icon-edit" 
                circle 
                @click="map.nameMode = 'edit'"></el-button></el-col>
              </el-row>
              <el-row v-if="map.nameMode === 'edit'" type="flex" justify="space-around" style="height: 40px">
                <el-col :span="6" style="line-height: 40px">地图名:</el-col>
                <el-col :span="12" style="line-height: 40px"><el-input 
                  type="text" 
                  placeholder="请输入地图名" 
                  v-model="map.name" 
                  maxlength="50" 
                  show-word-limit></el-input></el-col>
                <el-col :span="6"><el-button 
                  type="success" 
                  icon="el-icon-check" 
                  circle 
                  @click="editFin(map)"></el-button></el-col>
              </el-row>
            </div>
            <div style="margin: 0 auto; text-align: center; padding: 15px">
              <el-button type="danger" @click="deleteMap(map)" style="margin: 0 auto!important;">删除</el-button>
            </div>
            <!-- <div style="clear:both"></div> -->
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