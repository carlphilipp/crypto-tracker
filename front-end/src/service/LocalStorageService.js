export function save(name, value) {
  localStorage.setItem(name, value)
}

export function get(name) {
  return localStorage.getItem(name)
}

export function remove(name) {
  localStorage.removeItem(name)
}
