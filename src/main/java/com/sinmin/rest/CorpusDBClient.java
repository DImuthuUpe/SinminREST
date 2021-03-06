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
package com.sinmin.rest;

import com.sinmin.rest.beans.response.*;

public interface CorpusDBClient {
    public WordFrequencyR getWordFrequency(String word_value) throws Exception;

    public WordFrequencyR getWordFrequency(String word_value, int time) throws Exception;

    public WordFrequencyR getWordFrequency(String word_value, String category) throws Exception;

    public WordFrequencyR getWordFrequency(String word_value, int time, String category) throws Exception;

    public WordFrequencyR getBigramFrequency(String word1, String word2,int year, String category) throws Exception;

    public WordFrequencyR getBigramFrequency(String word1, String word2,int year) throws Exception;

    public WordFrequencyR getBigramFrequency(String word1, String word2,String category) throws Exception;

    public WordFrequencyR getBigramFrequency(String word1, String word2) throws Exception;

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year, String category)throws Exception;

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,int year)throws Exception;

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3,String category)throws Exception;

    public WordFrequencyR getTrigramFrequency(String word1, String word2,String word3)throws Exception;

    /////////////////////Frequent Words///////////////
    public FrequentWordR getFrequentWords(int year, String category,int amount) throws Exception;

    public FrequentWordR getFrequentWords(int year,int amount) throws Exception;

    public FrequentWordR getFrequentWords(String category,int amount) throws Exception;

    public FrequentWordR getFrequentWords(int amount) throws Exception;
/////////////////////////////Frequent Bigrams //////////////////////////////

    public FrequentWordR getFrequentBigrams(int year, String category,int amount) throws Exception;

    public FrequentWordR getFrequentBigrams(int year,int amount) throws Exception;

    public FrequentWordR getFrequentBigrams(String category,int amount) throws Exception;

    public FrequentWordR getFrequentBigrams(int amount) throws Exception;

    ///////////////////////////////////////////////////////////////////////////////////////

    public FrequentWordR getFrequentTrigrams(int year, String category,int amount) throws Exception;

    public FrequentWordR getFrequentTrigrams(int year,int amount) throws Exception;

    public FrequentWordR getFrequentTrigrams(String category,int amount) throws Exception;

    public FrequentWordR getFrequentTrigrams(int amount) throws Exception;


    /////////////////////Latest Articles for word ////////////////////////

    public ArticlesForWordR getLatestArticlesForWord(String word,int year, String category,int amount) throws Exception;

    public ArticlesForWordR getLatestArticlesForWord(String word,int year,int amount) throws Exception;

    public ArticlesForWordR getLatestArticlesForWord(String word,String category,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForWord(String word,int amount)throws Exception;


    //////////////// latest articles for bigrams

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2,int year, String category,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForBigram(String word1, String word2,int year,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForBigram(String word1,String word2,String category,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForBigram(String word1,String word2,int amount)throws Exception;


    ///////////// Latest articles in trigrams /////////////

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2,String word3, int year, String category,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForTrigram(String word1, String word2,String word3,int year,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForTrigram(String word1,String word2,String word3,String category,int amount)throws Exception;

    public ArticlesForWordR getLatestArticlesForTrigram(String word1,String word2, String word3,int amount)throws Exception;

    ///////////////////Frequencies around a word///////////////////////////////////////////////

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int year, int range, int amount) throws Exception;

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int year, int range, int amount) throws Exception;

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, String category, int range, int amount) throws Exception;

    public FrequentWordsAroundWordR getFrequentWordsAroundWord(String word, int range, int amount) throws Exception;
    //////////////////////////Get word in position ////////////////

    public WordPositionR getFrequentWordsInPosition(int position,int year,String category,int amount)throws Exception;

    public WordPositionR getFrequentWordsInPosition(int position,int year,int amount)throws Exception;


    public WordPositionR getFrequentWordsInPosition(int position,String category,int amount)throws Exception;

    public WordPositionR getFrequentWordsInPosition(int position,int amount)throws Exception;


    ///////////////////////reverse /////////

    public WordPositionR getFrequentWordsInPositionReverse(int position,int year,String category,int amount)throws Exception;

    public WordPositionR getFrequentWordsInPositionReverse(int position,String category,int amount)throws Exception;

    public WordPositionR getFrequentWordsInPositionReverse(int position,int year,int amount)throws Exception;

    public WordPositionR getFrequentWordsInPositionReverse(int position,int amount)throws Exception;

    /////////////////////get frequent words in time period around word //////////////

    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word,String category, int year1,int year2, int amount) throws Exception;

    public FrequentWordsAfterWordR getFrequentWordsAfterWordTimeRange(String word, int year1,int year2, int amount) throws Exception;

    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1,String word2,String category, int year1,int year2, int amount) throws Exception;

    public FrequentWordsAfterWordR getFrequentWordsAfterBigramTimeRange(String word1,String word2, int year1,int year2, int amount) throws Exception;

    public WordCountR getWordCount(String category,int year) throws Exception;

    public WordCountR getWordCount(String category) throws Exception;

    public WordCountR getWordCount(int year) throws Exception;

    public WordCountR getWordCount() throws Exception;

    public WordCountR getBigramCount(String category,int year) throws Exception;

    public WordCountR getBigramCount(String category) throws Exception;

    public WordCountR getBigramCount(int year) throws Exception;

    public WordCountR getBigramCount() throws Exception;

    public WordCountR getTrigramCount(String category,int year) throws Exception;

    public WordCountR getTrigramCount(String category) throws Exception;

    public WordCountR getTrigramCount(int year) throws Exception;

    public WordCountR getTrigramCount() throws Exception;

    public WordFrequencyR getWordCountInPosition(String category, int year,int position,String word);

    public WordFrequencyR getWordCountInPosition(int year,int position,String word);

    public WordFrequencyR getWordCountInPosition(String category,int position,String word);

    public WordFrequencyR getWordCountInPosition(int position,String word);

    public WordFrequencyR getWordCountInPositionReverse(String category, int year,int position,String word);

    public WordFrequencyR getWordCountInPositionReverse(int year,int position,String word);

    public WordFrequencyR getWordCountInPositionReverse(String category,int position,String word);

    public WordFrequencyR getWordCountInPositionReverse(int position,String word);
}
