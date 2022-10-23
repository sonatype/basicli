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
package org.sonatype.goodies.basicli.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ???
 *
 * @since ???
 */
public class CommandContext
{
  private static final Logger log = LoggerFactory.getLogger(CommandContext.class);

  private final Map<Object,Object> backing = new HashMap<>();

  public void put(final Object key, final Object value) {
    log.trace("Put: {}={}", key, value);
    backing.put(key, value);
  }

  public Object get(final Object key) {
    return backing.get(key);
  }

  public <T> T get(final Class<T> type) {
    //noinspection unchecked
    return (T) backing.get(type);
  }

  public Object require(final Object key) {
    Object value = backing.get(key);
    if (value == null) {
      throw new RuntimeException("Missing value for key: " + key);
    }
    return value;
  }

  public <T> T require(final Class<T> type) {
    //noinspection unchecked
    return (T) require((Object)type);
  }

  public void remove(final Object key) {
    log.trace("Remove: {}", key);
    backing.remove(key);
  }
}
