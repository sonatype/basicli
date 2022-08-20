/*
 * Copyright (c) 2022-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.goodies.basicli.common

import groovy.util.logging.Slf4j

/**
 * ???
 *
 * @since ???
 */
@Slf4j
@Singleton
class CommandContext
{
  private final Map<Object,Object> backing = [:]

  void put(final Object key, final Object value) {
    log.trace("Put: $key=$value")
    backing[key] = value
  }

  Object get(final Object key) {
    return backing[key]
  }

  def <T> T get(final Class<T> type) {
    return get(type as Object) as T
  }

  Object require(final Object key) {
    def value = backing[key]
    assert value != null : "Missing value for key: $key"
    return value
  }

  def <T> T require(final Class<T> type) {
    return require(type as Object) as T
  }

  void remove(final Object key) {
    log.trace("Remove: $key")
    backing.remove(key)
  }
}
