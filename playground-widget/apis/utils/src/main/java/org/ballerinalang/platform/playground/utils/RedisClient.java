/*
 * Copyright (c) 2018, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.platform.playground.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis Client
 */
public class RedisClient {

    private static JedisPool pool;

    private static RedisClient instance;

    private RedisClient() {
        pool = new JedisPool(new JedisPoolConfig(),
                EnvUtils.getRequiredEnvStringValue(EnvVariables.ENV_BPG_REDIS_WRITE_HOST),
                EnvUtils.getRequiredEnvIntValue(EnvVariables.ENV_BPG_REDIS_WRITE_PORT));
    }

    public static RedisClient getInstance () {
        if (instance == null) {
            instance = new RedisClient();
        }
        return instance;
    }

    public Jedis getClient() {
        return pool.getResource();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        pool.close();
    }
}
