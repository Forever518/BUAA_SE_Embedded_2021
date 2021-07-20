import service from "./api";

export function post_resetSettings() {
  return service.post('/map/remove-all')
}

export function post_createMap() {
  return service.post('/ros-map/create')
}

export function post_saveMap() {
  return service.post('/ros-map/save')
}

export function post_renameMap_name(data) {
  return service.post('/map/insert')({
    data: data
  })
}