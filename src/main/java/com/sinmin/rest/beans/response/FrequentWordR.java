/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package com.sinmin.rest.beans.response;

public class FrequentWordR {
    private WordR[] value1;
    private WordR[] value2;
    private WordR[] value3;
    private String category;
    private int time;

    public WordR[] getValue1() {
        return value1;
    }

    public void setValue1(WordR[] value1) {
        this.value1 = value1;
    }

    public WordR[] getValue2() {
        return value2;
    }

    public void setValue2(WordR[] value2) {
        this.value2 = value2;
    }

    public WordR[] getValue3() {
        return value3;
    }

    public void setValue3(WordR[] value3) {
        this.value3 = value3;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
