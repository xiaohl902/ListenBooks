package com.mwkj.entity;

/**
 * Created by lenovo on 2016/10/30.
 * 演出实体类
 */
public class ShowEntity {

    /**
     * showId : 4838
     * showTitle : 【上海】戏从天降 上海九乐堂周末相声专场
     * showCover : http://www.mow99.com/img/show/95696.png
     * showTime : 1458316800000
     * showTimeStr : 2016.03.19-2017.01.14
     * showCity : 上海
     * showDesc : 戏从天降 上海九乐堂周末相声专场
     * showVenue : 徐汇影剧场 - 上海
     * showStatus : 售票中
     * showTicketPrice : 50-80元
     * showBuyUrl : http://item.damai.cn/95696.html
     * showType : 2
     * viewNumber : 236
     * buyNumber : 28
     */

    private int showId;
    private String showTitle;
    private String showCover;
    private long showTime;
    private String showTimeStr;
    private String showCity;
    private String showDesc;
    private String showVenue;
    private String showStatus;
    private String showTicketPrice;
    private String showBuyUrl;
    private int showType;
    private int viewNumber;
    private int buyNumber;

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public String getShowCover() {
        return showCover;
    }

    public void setShowCover(String showCover) {
        this.showCover = showCover;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    public String getShowTimeStr() {
        return showTimeStr;
    }

    public void setShowTimeStr(String showTimeStr) {
        this.showTimeStr = showTimeStr;
    }

    public String getShowCity() {
        return showCity;
    }

    public void setShowCity(String showCity) {
        this.showCity = showCity;
    }

    public String getShowDesc() {
        return showDesc;
    }

    public void setShowDesc(String showDesc) {
        this.showDesc = showDesc;
    }

    public String getShowVenue() {
        return showVenue;
    }

    public void setShowVenue(String showVenue) {
        this.showVenue = showVenue;
    }

    public String getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(String showStatus) {
        this.showStatus = showStatus;
    }

    public String getShowTicketPrice() {
        return showTicketPrice;
    }

    public void setShowTicketPrice(String showTicketPrice) {
        this.showTicketPrice = showTicketPrice;
    }

    public String getShowBuyUrl() {
        return showBuyUrl;
    }

    public void setShowBuyUrl(String showBuyUrl) {
        this.showBuyUrl = showBuyUrl;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }
}
