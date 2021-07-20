import service from "./api";

export function post_resetSettings() {
  return service.post('/map/remove-all')
}