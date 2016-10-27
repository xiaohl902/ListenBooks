package com.mwkj.entity;

/**
 * Created by lenovo on 2016/10/26.
 */
public class ArtEntity {

    /**
     * artistId : 231
     * artistName : 煎饺
     * artistTitle : 有声主播
     * artistWork : 《中国最具影响力童话大奖全集金色阳光卷》
     * artistResume : 《中国最具影响力童话大奖全集金色阳光卷》
     * artistIcon : null
     * artistImg : http://www.mow99.com/img/artist/224.jpg
     * workNumber : 0
     * playNumber : 0
     */

    private int artistId;
    private String artistName;
    private String artistTitle;
    private String artistWork;
    private String artistResume;
    private Object artistIcon;
    private String artistImg;
    private int workNumber;
    private int playNumber;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistTitle() {
        return artistTitle;
    }

    public void setArtistTitle(String artistTitle) {
        this.artistTitle = artistTitle;
    }

    public String getArtistWork() {
        return artistWork;
    }

    public void setArtistWork(String artistWork) {
        this.artistWork = artistWork;
    }

    public String getArtistResume() {
        return artistResume;
    }

    public void setArtistResume(String artistResume) {
        this.artistResume = artistResume;
    }

    public Object getArtistIcon() {
        return artistIcon;
    }

    public void setArtistIcon(Object artistIcon) {
        this.artistIcon = artistIcon;
    }

    public String getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(String artistImg) {
        this.artistImg = artistImg;
    }

    public int getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(int workNumber) {
        this.workNumber = workNumber;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }
}
