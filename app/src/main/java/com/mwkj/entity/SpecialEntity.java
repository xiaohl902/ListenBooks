package com.mwkj.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${WU} on 2016/10/29.
 */
public class SpecialEntity {

    /**
     * version : 0.1
     * sid : mow99_2014_12_30_@~$;
     * page : {"pageSize":8,"pageNumber":1,"endNumber":8,"beginNumber":0,"pageCount":3,"query":null}
     * result : 0
     * resultString :
     * subject : {"subjectId":0,"subjectTitle":null,"subjectDesc":null,"subjectImg":null,"createTime":null,"subjectViewNumber":0,"subjectOrderIndex":0,"albums":null}
     * subjects : [{"subjectId":18,"subjectTitle":"儿童节专题-小朋友最开心的日子","subjectDesc":"吃的好、玩的美、美美哒！","subjectImg":"http://www.mow99.com/img/subject/20160531_1.jpg","createTime":1464648729000,"subjectViewNumber":943,"subjectOrderIndex":18,"albums":null},{"subjectId":17,"subjectTitle":"二次元专题-看过说明你不年轻了","subjectDesc":"那时候有点懵懂、有点怒火、有点爱情、有点冲动、有点任性、有点不知道天高地厚，还有那么点时间来看这些...","subjectImg":"http://www.mow99.com/img/subject/20151126.jpg","createTime":1448370735000,"subjectViewNumber":2740,"subjectOrderIndex":17,"albums":null},{"subjectId":16,"subjectTitle":"相声老段子-经典包袱铺","subjectDesc":"那些你也能倒背如流的包袱，听与不听，它们就在那里，不声不吭，等着耳朵光顾。","subjectImg":"http://www.mow99.com/img/subject/20151111.jpg","createTime":1447259502000,"subjectViewNumber":3074,"subjectOrderIndex":16,"albums":null},{"subjectId":15,"subjectTitle":"人物专题-有关人生的干货","subjectDesc":"不平凡的人总有不平凡的经历，有些感悟值得我们去思考。成功不能复制，复制的只能是那种积极坚毅向上的人生态度！","subjectImg":"http://www.mow99.com/img/subject/20151028.jpg","createTime":1446002729000,"subjectViewNumber":2148,"subjectOrderIndex":15,"albums":null},{"subjectId":14,"subjectTitle":"古龙武侠小说专题-武侠宗师","subjectDesc":"著名武侠小说家，新派武侠小说泰斗，与金庸、梁羽生并称为中国武侠小说三大宗师。代表作有《多情剑客无情剑》、《绝代双骄》、《英雄无泪》等。古龙把武侠小说引入了经典文学的殿堂，将戏剧、推理、诗歌等元素带入传统武侠，又将自己独特的人生哲学融入其中，使中外经典镕铸一炉，开创了近代武侠小说新纪元，将武侠文学推上了一个新的高峰。\r\n1985年9月21日，因肝硬化、静脉出血，古龙在台湾去世，终年48岁。","subjectImg":"http://www.mow99.com/img/subject/20151020.jpg","createTime":1445325272000,"subjectViewNumber":2273,"subjectOrderIndex":14,"albums":null},{"subjectId":13,"subjectTitle":"鬼故事专题-吓死人不偿命","subjectDesc":"重要的事情说三遍：吓死人不偿命！吓死人不偿命！吓死人不偿命！","subjectImg":"http://www.mow99.com/img/subject/20150927.jpg","createTime":1443401811000,"subjectViewNumber":4748,"subjectOrderIndex":13,"albums":null},{"subjectId":12,"subjectTitle":"四大名著专题-文学巨著","subjectDesc":"中国的四大名著是《三国演义》、《水浒传》、《西游记》、《红楼梦》。这四部著作历久不衰，是汉语文学史中不可多得的经典作品。其中的故事、场景、人物已经深深地影响了中国人的思想观念、价值取向。四部著作都有很高的文学水平和艺术成就。细致的刻画和所蕴含的深刻思想都为历代读者所称道。","subjectImg":"http://www.mow99.com/img/subject/20150921.jpg","createTime":1442870675000,"subjectViewNumber":3113,"subjectOrderIndex":12,"albums":null},{"subjectId":11,"subjectTitle":"奇闻异事专题-脑洞大开","subjectDesc":"世界奇闻异事大全，总有让你震撼的！","subjectImg":"http://www.mow99.com/img/subject/20150913_1.jpg","createTime":1442128042000,"subjectViewNumber":3476,"subjectOrderIndex":11,"albums":null}]
     */

    private String version;
    private String sid;
    /**
     * pageSize : 8
     * pageNumber : 1
     * endNumber : 8
     * beginNumber : 0
     * pageCount : 3
     * query : null
     */

    private PageBean page;
    private int result;
    private String resultString;
    /**
     * subjectId : 0
     * subjectTitle : null
     * subjectDesc : null
     * subjectImg : null
     * createTime : null
     * subjectViewNumber : 0
     * subjectOrderIndex : 0
     * albums : null
     */

    private SubjectBean subject;
    /**
     * subjectId : 18
     * subjectTitle : 儿童节专题-小朋友最开心的日子
     * subjectDesc : 吃的好、玩的美、美美哒！
     * subjectImg : http://www.mow99.com/img/subject/20160531_1.jpg
     * createTime : 1464648729000
     * subjectViewNumber : 943
     * subjectOrderIndex : 18
     * albums : null
     */

    private List<SubjectsBean> subjects;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class PageBean {
        private int pageSize;
        private int pageNumber;
        private int endNumber;
        private int beginNumber;
        private int pageCount;
        private Object query;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getEndNumber() {
            return endNumber;
        }

        public void setEndNumber(int endNumber) {
            this.endNumber = endNumber;
        }

        public int getBeginNumber() {
            return beginNumber;
        }

        public void setBeginNumber(int beginNumber) {
            this.beginNumber = beginNumber;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public Object getQuery() {
            return query;
        }

        public void setQuery(Object query) {
            this.query = query;
        }
    }

    public static class SubjectBean {
        private int subjectId;
        private Object subjectTitle;
        private Object subjectDesc;
        private Object subjectImg;
        private Object createTime;
        private int subjectViewNumber;
        private int subjectOrderIndex;
        private Object albums;

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public Object getSubjectTitle() {
            return subjectTitle;
        }

        public void setSubjectTitle(Object subjectTitle) {
            this.subjectTitle = subjectTitle;
        }

        public Object getSubjectDesc() {
            return subjectDesc;
        }

        public void setSubjectDesc(Object subjectDesc) {
            this.subjectDesc = subjectDesc;
        }

        public Object getSubjectImg() {
            return subjectImg;
        }

        public void setSubjectImg(Object subjectImg) {
            this.subjectImg = subjectImg;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getSubjectViewNumber() {
            return subjectViewNumber;
        }

        public void setSubjectViewNumber(int subjectViewNumber) {
            this.subjectViewNumber = subjectViewNumber;
        }

        public int getSubjectOrderIndex() {
            return subjectOrderIndex;
        }

        public void setSubjectOrderIndex(int subjectOrderIndex) {
            this.subjectOrderIndex = subjectOrderIndex;
        }

        public Object getAlbums() {
            return albums;
        }

        public void setAlbums(Object albums) {
            this.albums = albums;
        }
    }

    public static class SubjectsBean implements Serializable{
        private int subjectId;
        private String subjectTitle;
        private String subjectDesc;
        private String subjectImg;
        private long createTime;
        private int subjectViewNumber;
        private int subjectOrderIndex;
        private Object albums;

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectTitle() {
            return subjectTitle;
        }

        public void setSubjectTitle(String subjectTitle) {
            this.subjectTitle = subjectTitle;
        }

        public String getSubjectDesc() {
            return subjectDesc;
        }

        public void setSubjectDesc(String subjectDesc) {
            this.subjectDesc = subjectDesc;
        }

        public String getSubjectImg() {
            return subjectImg;
        }

        public void setSubjectImg(String subjectImg) {
            this.subjectImg = subjectImg;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getSubjectViewNumber() {
            return subjectViewNumber;
        }

        public void setSubjectViewNumber(int subjectViewNumber) {
            this.subjectViewNumber = subjectViewNumber;
        }

        public int getSubjectOrderIndex() {
            return subjectOrderIndex;
        }

        public void setSubjectOrderIndex(int subjectOrderIndex) {
            this.subjectOrderIndex = subjectOrderIndex;
        }

        public Object getAlbums() {
            return albums;
        }

        public void setAlbums(Object albums) {
            this.albums = albums;
        }
    }
}
