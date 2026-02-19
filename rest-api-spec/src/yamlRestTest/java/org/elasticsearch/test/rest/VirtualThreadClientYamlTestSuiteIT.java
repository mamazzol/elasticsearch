/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */

package org.elasticsearch.test.rest;

import com.carrotsearch.randomizedtesting.annotations.Name;
import com.carrotsearch.randomizedtesting.annotations.ParametersFactory;
import com.carrotsearch.randomizedtesting.annotations.TimeoutSuite;

import org.apache.lucene.tests.util.TimeUnits;
import org.elasticsearch.test.cluster.ElasticsearchCluster;
import org.elasticsearch.test.cluster.FeatureFlag;
import org.elasticsearch.test.rest.yaml.ClientYamlTestCandidate;
import org.elasticsearch.test.rest.yaml.ESClientYamlSuiteTestCase;
import org.junit.ClassRule;

/**
 * Runs the same YAML REST test suite as {@link ClientYamlTestSuiteIT} but with
 * {@code es.http.rest.virtual_threads=true} on the cluster nodes, so every HTTP
 * request is dispatched on a virtual thread.
 */
@TimeoutSuite(millis = 40 * TimeUnits.MINUTE)
public class VirtualThreadClientYamlTestSuiteIT extends ESClientYamlSuiteTestCase {

    @ClassRule
    public static ElasticsearchCluster cluster = ElasticsearchCluster.local()
        .module("mapper-extras")
        .module("rest-root")
        .module("reindex")
        .module("analysis-common")
        .module("health-shards-availability")
        .module("data-streams")
        .feature(FeatureFlag.TIME_SERIES_MODE)
        .feature(FeatureFlag.SYNTHETIC_VECTORS)
        .feature(FeatureFlag.RANDOM_SAMPLING)
        .feature(FeatureFlag.EXTENDED_DOC_VALUES_PARAMS)
        .systemProperty("es.http.rest.virtual_threads", "true")
        .build();

    public VirtualThreadClientYamlTestSuiteIT(@Name("yaml") ClientYamlTestCandidate testCandidate) {
        super(testCandidate);
    }

    @ParametersFactory
    public static Iterable<Object[]> parameters() throws Exception {
        return createParameters();
    }

    @Override
    protected String getTestRestCluster() {
        return cluster.getHttpAddresses();
    }
}
