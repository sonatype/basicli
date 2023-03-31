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

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * {@link MoreStrings} tests.
 */
public class MoreStringsTest
{
  @Test
  void blankToNull() {
    assertThat(MoreStrings.blankToNull(null), nullValue());
    assertThat(MoreStrings.blankToNull(""), nullValue());
    assertThat(MoreStrings.blankToNull("      "), nullValue());
    assertThat(MoreStrings.blankToNull("foo"), not(nullValue()));
    assertThat(MoreStrings.blankToNull("  bar  "), not(nullValue()));

    // ensure that result is the same as given when its non-null or blank
    String text = "  bar  ";
    assertThat(MoreStrings.blankToNull(text), is(text));
  }

  @Test
  void lower() {
    String value = "FooBar";
    assertThat(MoreStrings.lower(value), is("foobar"));
  }

  @Test
  void upper() {
    String value = "FooBar";
    assertThat(MoreStrings.upper(value), is("FOOBAR"));
  }

  @Test
  void dquote_string_value() {
    assertThat(MoreStrings.dquote("foo"), is("\"foo\""));
  }

  @Test
  void dquote_null_value() {
    assertThat(MoreStrings.dquote(null), nullValue());
  }
}