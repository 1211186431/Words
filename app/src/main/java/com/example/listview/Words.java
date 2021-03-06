package com.example.listview;

import android.net.Uri;
import android.provider.BaseColumns;

public class Words {
    public static final String AUTHORITY = "com.example.listview.provider";//URI授权者

    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";//表名
        public static final String COLUMN_NAME_WORD = "word";//列：单词
        public static final String COLUMN_NAME_MEANING = "meaning";//列：单词含义
        public static final String COLUMN_NAME_SAMPLE = "sample";//列：单词示例
        // MIME类型
        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";
        public static final String MINE_ITEM = "vnd.com.example.listview.words";
        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MINE_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MINE_ITEM;
        public static final String PATH_SINGLE = "words/#";//单条数据的路径
        public static final String PATH_MULTIPLE = "words";//多条数据的路径
        // Content Uri
        public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + PATH_MULTIPLE;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    }


    //每个单词的描述
    public static class WordDescription {
        public String id;
        public String word;
        public String meaning;
        public String sample;

        public WordDescription(String id, String word, String meaning, String sample) {
            this.id = id;
            this.word = word;
            this.meaning = meaning;
            this.sample = sample;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }
    }



}
