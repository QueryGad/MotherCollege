package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ReadBookBean {
    /**
     * books : [{"bookID":"bk1611070001","bookInfo":"阿斯蒂芬撒旦法","bookName":"让他成为你的骄傲","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120001","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090003","bookInfo":"爱的色放","bookName":"克制冲动","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120004","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120005","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090004","bookInfo":"爱的色放","bookName":"成功需要舍得","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120006","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120007","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090005","bookInfo":"爱的色放","bookName":"我家孩子也可以","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120008","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120028","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090006","bookInfo":"爱的色放","bookName":"都懂女人心","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120009","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090007","bookInfo":"爱的色放","bookName":"学会换位","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120010","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120011","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":3,"sid":"bkp1611120012","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090008","bookInfo":"爱的色放","bookName":"错觉影响感情","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120013","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120014","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090009","bookInfo":"爱的色放","bookName":"女人艺术","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120015","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120016","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":3,"sid":"bkp1611120027","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090010","bookInfo":"爱的色放","bookName":"育儿注意十项","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120017","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120018","title":"章节二","viewCount":"89"}]},{"bookID":"bk1611090011","bookInfo":"爱的色放","bookName":"学会和他相处","pages":[{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120019","title":"章节二","viewCount":"89"},{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":2,"sid":"bkp1611120020","title":"章节二","viewCount":"89"}]}]
     * endNo : 10
     */

    private int endNo;
    /**
     * bookID : bk1611070001
     * bookInfo : 阿斯蒂芬撒旦法
     * bookName : 让他成为你的骄傲
     * pages : [{"date":"2016/11/9 0:00:00","editor":"魏国峰","img":"http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg","isnew":"false","pageIndex":1,"sid":"bkp1611120001","title":"章节二","viewCount":"89"}]
     */

    private List<BooksBean> books;

    public int getEndNo() {
        return endNo;
    }

    public void setEndNo(int endNo) {
        this.endNo = endNo;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }

    public static class BooksBean {
        private String bookID;
        private String bookInfo;
        private String bookName;
        /**
         * date : 2016/11/9 0:00:00
         * editor : 魏国峰
         * img : http://121.42.31.133:8201/upload/image/20161112/20161112182136_3052.jpg
         * isnew : false
         * pageIndex : 1
         * sid : bkp1611120001
         * title : 章节二
         * viewCount : 89
         */

        private List<PagesBean> pages;

        public String getBookID() {
            return bookID;
        }

        public void setBookID(String bookID) {
            this.bookID = bookID;
        }

        public String getBookInfo() {
            return bookInfo;
        }

        public void setBookInfo(String bookInfo) {
            this.bookInfo = bookInfo;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public List<PagesBean> getPages() {
            return pages;
        }

        public void setPages(List<PagesBean> pages) {
            this.pages = pages;
        }

        public static class PagesBean {
            private String date;
            private String editor;
            private String img;
            private String isnew;
            private int pageIndex;
            private String sid;
            private String title;
            private String viewCount;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getEditor() {
                return editor;
            }

            public void setEditor(String editor) {
                this.editor = editor;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIsnew() {
                return isnew;
            }

            public void setIsnew(String isnew) {
                this.isnew = isnew;
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }
        }
    }
}
