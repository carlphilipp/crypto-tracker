/**
 * Copyright 2017 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
