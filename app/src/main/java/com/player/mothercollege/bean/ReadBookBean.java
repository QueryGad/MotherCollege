package com.player.mothercollege.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ReadBookBean {
    /**
     * books : [{"bookID":"b238445","bookName":"天真的父母","pages":[{"date":"2017-09-13","editor":"张三","img":"http://121.42.31.133:8201/m/img/4.png","isnew":"true","pageIndex":1,"sid":"s43984","title":"章节1","viewCount":"34"},{"date":"2017-09-13","editor":"张三","img":"http://121.42.31.133:8201/m/img/1.png","isnew":"true","pageIndex":2,"sid":"s43984","title":"章节2","viewCount":"34"}]},{"bookID":"b238445","bookName":"亲子成长计划","pages":[{"date":"2017-09-13","editor":"周正","img":"http://121.42.31.133:8201/m/img/2.png","isnew":"true","pageIndex":1,"sid":"s43984","title":"章节1","viewCount":"34"},{"date":"2017-09-13","editor":"周正","img":"http://121.42.31.133:8201/m/img/2.png","isnew":"true","pageIndex":2,"sid":"s43984","title":"章节2","viewCount":"334"}]}]
     * endNo : 9
     */

    private int endNo;
    /**
     * bookID : b238445
     * bookName : 天真的父母
     * pages : [{"date":"2017-09-13","editor":"张三","img":"http://121.42.31.133:8201/m/img/4.png","isnew":"true","pageIndex":1,"sid":"s43984","title":"章节1","viewCount":"34"},{"date":"2017-09-13","editor":"张三","img":"http://121.42.31.133:8201/m/img/1.png","isnew":"true","pageIndex":2,"sid":"s43984","title":"章节2","viewCount":"34"}]
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
        private String bookName;
        /**
         * date : 2017-09-13
         * editor : 张三
         * img : http://121.42.31.133:8201/m/img/4.png
         * isnew : true
         * pageIndex : 1
         * sid : s43984
         * title : 章节1
         * viewCount : 34
         */

        private List<PagesBean> pages;

        public String getBookID() {
            return bookID;
        }

        public void setBookID(String bookID) {
            this.bookID = bookID;
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
