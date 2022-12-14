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
package org.sonatype.goodies.basicli.support;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * System property helpers.
 *
 * @since ???
 */
public class SystemPropertyHelper
{
  private static final Logger log = LoggerFactory.getLogger(SystemPropertyHelper.class);

  private SystemPropertyHelper() {
    // empty
  }

  static void set(final String name, final String value) {
    log.trace("Set: '{}' -> '{}'", name, value);
    System.setProperty(name, value);
  }

  public static void merge(final Map<String,String> source) {
    log.trace("Merge: $source");
    source.forEach(SystemPropertyHelper::set);
  }
}
