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

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

// copied/modified from: https://github.com/sonatype/dropwizard-support/blob/master/core/src/main/java/org/sonatype/goodies/dropwizard/version/VersionLoader.java

/**
 * Helper to load version information from build via resource.
 *
 * @since ???
 */
public class VersionLoader
{
  private static final Logger log = LoggerFactory.getLogger(VersionLoader.class);

  public static final String RESOURCE = "version.properties";

  public static final String UNKNOWN = "unknown";

  @VisibleForTesting
  static final String VERSION = "version";

  @VisibleForTesting
  static final String TIMESTAMP = "timestamp";

  @VisibleForTesting
  static final String TAG = "tag";

  @VisibleForTesting
  static final String NOTES = "notes";

  @Nullable
  private final URL resource;

  public VersionLoader(final Class<?> owner) {
    checkNotNull(owner);
    this.resource = owner.getResource(RESOURCE);
  }

  public VersionLoader(@Nullable final URL resource) {
    this.resource = resource;
  }

  @VisibleForTesting
  VersionLoader() {
    this.resource = null;
  }

  @VisibleForTesting
  protected Properties load() {
    Properties result = new Properties();
    if (resource == null) {
      log.warn("Missing resource: {}", RESOURCE);
    }
    else {
      log.debug("Resource: {}", resource);
      try {
        try (InputStream input = resource.openStream()) {
          result.load(input);
        }
      }
      catch (Exception e) {
        log.warn("Failed to load resource: {}", RESOURCE, e);
      }
      log.debug("Properties: {}", result);
    }
    return result;
  }

  private Properties properties;

  private Properties properties() {
    if (properties == null) {
      properties = load();
    }
    return properties;
  }

  private String property(final String name) {
    String value = properties().getProperty(name);
    if (value == null || value.contains("${")) {
      return UNKNOWN;
    }
    return value;
  }

  public String getVersion() {
    return property(VERSION);
  }

  public String getTimestamp() {
    return property(TIMESTAMP);
  }

  public String getTag() {
    return property(TAG);
  }

  public String getNotes() {
    return property(NOTES);
  }

  public String getYear() {
    return property("year");
  }

  @Override
  public String toString() {
    return String.format("%s (%s; %s)", getVersion(), getTimestamp(), getTag());
  }
}
