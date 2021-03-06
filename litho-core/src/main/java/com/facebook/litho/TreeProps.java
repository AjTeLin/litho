/*
 * Copyright (c) 2017-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.litho;

import java.util.Collections;
import java.util.Map;

import android.support.v4.util.ArrayMap;

import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.infer.annotation.ThreadSafe;
import com.facebook.litho.annotations.TreeProp;

/**
 * A data structure to store tree props.
 * @see {@link TreeProp}.
 */
@ThreadConfined(ThreadConfined.ANY)
public class TreeProps {

  private final ArrayMap<Class, Object> mMap = new ArrayMap<>();

  public void put(Class key, Object value) {
    mMap.put(key, value);
  }

  public <T> T get(Class key) {
    return (T) mMap.get(key);
  }

  public Map<Class, Object> toMap() {
    return Collections.unmodifiableMap(mMap);
  }

  /**
   * Whenever a Spec sets tree props, the TreeProps map from the parent is copied.
   *
   * Infer knows that newProps is owned but doesn't know that newProps.mMap is owned.
   */
  @ThreadSafe(enableChecks = false)
  public static TreeProps copy(TreeProps source) {
    final TreeProps newProps = ComponentsPools.acquireTreeProps();
    if (source != null) {
      newProps.mMap.putAll((Map<? extends Class, ?>) source.mMap);
    }

    return newProps;
  }

  void reset() {
    mMap.clear();
  }
}
