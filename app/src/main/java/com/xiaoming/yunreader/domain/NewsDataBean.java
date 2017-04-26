package com.xiaoming.yunreader.domain;

import java.util.ArrayList;

/**
 * 新闻数据对象
 * @author Slience_Manager
 * @time 2017/4/18 18:27
 */

public class NewsDataBean {
    public ResultData result;

    public class ResultData{
        public int stat;
        public ArrayList<NewData> data;
    }

    public class NewData{
        public String category;
        public String title;
        public String date;
        public String author_name;
        public String url;
        public String thumbnail_pic_s;
        public String thumbnail_pic_s02;
        public String thumbnail_pic_s03;

    }
}
