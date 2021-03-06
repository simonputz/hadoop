/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.hadoop.hdds.scm.container.common.helpers;

import org.apache.hadoop.hdds.protocol.proto.HddsProtos;
import org.apache.ratis.protocol.RaftGroupId;

import java.util.UUID;

/**
 * ID for the pipeline, the ID is based on UUID so that it can be used
 * in Ratis as RaftGroupId, GroupID is used by the datanodes to initialize
 * the ratis group they are part of.
 */
public class PipelineID {

  private UUID id;
  private RaftGroupId groupId;

  private PipelineID(UUID id) {
    this.id = id;
    this.groupId = RaftGroupId.valueOf(id);
  }

  public static PipelineID randomId() {
    return new PipelineID(UUID.randomUUID());
  }

  public static PipelineID valueOf(RaftGroupId groupId) {
    return new PipelineID(groupId.getUuid());
  }

  public RaftGroupId getRaftGroupID() {
    return groupId;
  }

  public UUID getId() {
    return id;
  }

  public HddsProtos.PipelineID getProtobuf() {
    return HddsProtos.PipelineID.newBuilder().setId(id.toString()).build();
  }

  public static PipelineID getFromProtobuf(HddsProtos.PipelineID protos) {
    return new PipelineID(UUID.fromString(protos.getId()));
  }

  @Override
  public String toString() {
    return "pipelineId=" + id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PipelineID that = (PipelineID) o;

    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
