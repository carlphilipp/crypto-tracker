import {save, get, remove} from './LocalStorageService';

const PAGE = 'page'

export function getCurrentPage() {
  let page = get(PAGE)
  if(page != null) {
    return page;
  } else {
    return 'home'
  }
}

export function saveCurrentPage(page) {
  save(PAGE, page);
}

export function removePage() {
  remove(PAGE)
}
