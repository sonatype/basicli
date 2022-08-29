/*
 * Copyright (c) 2022-present Sonatype, Inc. All rights reserved.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */
package org.sonatype.goodies.basicli.common;

import java.util.Locale;
import javax.annotation.Nullable;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkNotNull;

// Copied and adjusted from: https://github.com/sonatype/dropwizard-support/blob/61adfb77a2540c688b526469b57235ca9e9f42e0/core/src/main/java/org/sonatype/goodies/dropwizard/text/MoreStrings.java

/**
 * String helpers.
 *
 * @since ???
 */
public final class MoreStrings
{
  private MoreStrings() {
    // empty
  }

  /**
   * Returns the given string if when trimmed it is not the empty string; {@code null} otherwise.
   */
  @Nullable
  public static String blankToNull(@Nullable final String value) {
    if (value != null) {
      return Strings.emptyToNull(value.trim()) != null ? value : null;
    }
    return null;
  }

  /**
   * Returns lower-case {@link Locale#ENGLISH} string.
   */
  public static String lower(final String value) {
    checkNotNull(value);
    return value.toLowerCase(Locale.ENGLISH);
  }

  /**
   * Returns upper-case {@link Locale#ENGLISH} string.
   */
  public static String upper(final String value) {
    checkNotNull(value);
    return value.toUpperCase(Locale.ENGLISH);
  }

  /**
   * Quote given value with token if non-null.
   */
  @Nullable
  public static String quote(@Nullable final String value, final String token) {
    if (value != null) {
      return String.format("%s%s%s", token, value, token);
    }
    else {
      return null;
    }
  }

  /**
   * Double-quote given value if non-null.
   */
  public static String dquote(@Nullable final String value) {
    return quote(value, "\"");
  }
}