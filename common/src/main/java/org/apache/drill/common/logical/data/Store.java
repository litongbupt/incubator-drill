/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.common.logical.data;

import com.google.common.collect.Iterators;
import org.apache.drill.common.JSONOptions;
import org.apache.drill.common.defs.PartitionDef;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.drill.common.logical.data.visitors.LogicalVisitor;

import java.util.Iterator;

@JsonTypeName("store")
public class Store extends SinkOperator{
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Store.class);
  
  private final String storageEngine;
  private final JSONOptions target;
  private final PartitionDef partition;

  @JsonCreator
  public Store(@JsonProperty("storageengine") String storageEngine, @JsonProperty("target") JSONOptions target, @JsonProperty("partition") PartitionDef partition) {
    super();
    this.storageEngine = storageEngine;
    this.target = target;
    this.partition = partition;
  }

  public String getStorageEngine() {
    return storageEngine;
  }

  public JSONOptions getTarget() {
    return target;
  }

  public PartitionDef getPartition() {
    return partition;
  }

  @Override
  public <T, X, E extends Throwable> T accept(LogicalVisitor<T, X, E> logicalVisitor, X value) throws E {
      return logicalVisitor.visitStore(this, value);
  }

  @Override
  public Iterator<LogicalOperator> iterator() {
      return Iterators.singletonIterator(getInput());
  }

  public static StoreBuilder builder() {
    return new StoreBuilder();
  }
  
}
