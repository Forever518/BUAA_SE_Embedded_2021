import service from "./api";

export function post_resetSettings() {
  return service.post('/map/remove-all');
}

export function post_checkUpstate() {
  return service.post('/control/check-update');
}

export function post_upstateService() {
  return service.post('/control/update-service');
}

export function post_createMap() {
  return service.post('/ros-map/create');
}

export function post_saveMap() {
  return service.post('/ros-map/save');
}

export function post_renameMap_name(data) {
  return service.post('/map/insert',data);
}

export function post_getMap() {
  return service.post('/map/display-all');
}

export function post_startMark(data) {
  return service.post('/ros-index/mark',data);
}

export function post_saveMark() {
  return service.post('/ros-index/save');
}

export function post_getWayPoint() {
  return service.post('/ros-index/count');
}

export function post_renameMark(data) {
  return service.post('/ros-index/rename-mark', data);
}

export function post_finishMark() {
  return service.post('/ros-index/finish-mark');
}

export function post_startService(data) {
  return service.post('/control/robot-service', data);
}

export function post_stopService() {
  return service.post('/control/stop-service');
}

export function post_getDirect(data) {
  return service.post('/control/direct', data);
}