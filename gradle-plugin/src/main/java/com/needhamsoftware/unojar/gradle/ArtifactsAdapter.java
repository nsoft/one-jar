/*
 * Copyright 2003-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.needhamsoftware.unojar.gradle;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.result.ResolvedArtifactResult;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Provider;

import java.util.Set;
import java.util.stream.Collectors;

class ArtifactsAdapter {
  private final ListProperty<ArtifactDetails> artifacts;

  ArtifactsAdapter(ListProperty<ArtifactDetails> artifacts) {
    this.artifacts = artifacts;
  }

  public ListProperty<ArtifactDetails> getArtifacts() {
    return artifacts;
  }

  public void from(Provider<Configuration> configuration) {
    from(configuration.get());
  }

  public void from(Configuration configuration) {
    fromProvider(configuration.getIncoming().getArtifacts().getResolvedArtifacts());
  }

  public void fromProvider(Provider<Set<ResolvedArtifactResult>> artifacts) {
    getArtifacts().set(artifacts.map(result ->
        result.stream().map(artifact -> new ArtifactDetails(artifact.getId(), artifact.getVariant(), artifact.getFile())).collect(Collectors.toList())
    ));
  }
}