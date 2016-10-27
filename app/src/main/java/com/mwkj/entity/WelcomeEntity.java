package com.mwkj.entity;

//欢迎页实体类
public class WelcomeEntity {

    /**
     * version : 0.1
     * sid : mow99_2014_12_30_@~$;
     * page : null
     * result : 100
     * resultString :
     * advPath :
     * showAdv : false
     * showHelp : false
     * user : {"mowuserId":141610,"mowuserName":"魔用户864394010755355","mowuserPwd":"","mowuserImei":"864394010755355","mowuserCellphoneType":"1","mowuserRegTime":1477402478000,"mowuserNikeName":"魔用户1477402478725","mowuserPersonImg":"http://www.mow99.com/img/user/default_person_img.png","mowuserCellphone":"","mowuserCity":0,"mowuserSex":0,"mowuserQq":"","mowuserWeixin":"","mowuserWeibo":"","sessionId":""}
     * session : {"mowsessionId":1143639,"userId":141610,"mowsessionCode":"1416101477561101340","createTime":1477561101000}
     * adv : {"advId":0,"advImg":null,"advIdx":0,"advLinkType":0,"advLinkValue":null,"advLocation":0,"album":null}
     * appUpgrade : {"appUpgradeId":5,"appVersion":1.2,"appDownloadURL":"http://www.mow99.com/release/MOWTingShu_1.2.apk","appReleaseDate":1430413368000}
     * loadingAdv : {"loadingAdvId":5,"advType":1,"advImgUrl":"http://www.mow99.com/img/loading/5.jpg","advForwardUrl":null,"advForwardUrlIos":null,"advForwardUrlAndroid":null,"publishDate":1430465812000}
     */

    private String version;
    private String sid;
    private Object page;
    private int result;
    private String resultString;
    private String advPath;
    private boolean showAdv;
    private boolean showHelp;
    /**
     * mowuserId : 141610
     * mowuserName : 魔用户864394010755355
     * mowuserPwd :
     * mowuserImei : 864394010755355
     * mowuserCellphoneType : 1
     * mowuserRegTime : 1477402478000
     * mowuserNikeName : 魔用户1477402478725
     * mowuserPersonImg : http://www.mow99.com/img/user/default_person_img.png
     * mowuserCellphone :
     * mowuserCity : 0
     * mowuserSex : 0
     * mowuserQq :
     * mowuserWeixin :
     * mowuserWeibo :
     * sessionId :
     */

    private UserBean user;
    /**
     * mowsessionId : 1143639
     * userId : 141610
     * mowsessionCode : 1416101477561101340
     * createTime : 1477561101000
     */

    private SessionBean session;
    /**
     * advId : 0
     * advImg : null
     * advIdx : 0
     * advLinkType : 0
     * advLinkValue : null
     * advLocation : 0
     * album : null
     */

    private AdvBean adv;
    /**
     * appUpgradeId : 5
     * appVersion : 1.2
     * appDownloadURL : http://www.mow99.com/release/MOWTingShu_1.2.apk
     * appReleaseDate : 1430413368000
     */

    private AppUpgradeBean appUpgrade;
    /**
     * loadingAdvId : 5
     * advType : 1
     * advImgUrl : http://www.mow99.com/img/loading/5.jpg
     * advForwardUrl : null
     * advForwardUrlIos : null
     * advForwardUrlAndroid : null
     * publishDate : 1430465812000
     */

    private LoadingAdvBean loadingAdv;

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

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
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

    public String getAdvPath() {
        return advPath;
    }

    public void setAdvPath(String advPath) {
        this.advPath = advPath;
    }

    public boolean isShowAdv() {
        return showAdv;
    }

    public void setShowAdv(boolean showAdv) {
        this.showAdv = showAdv;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public void setShowHelp(boolean showHelp) {
        this.showHelp = showHelp;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public AdvBean getAdv() {
        return adv;
    }

    public void setAdv(AdvBean adv) {
        this.adv = adv;
    }

    public AppUpgradeBean getAppUpgrade() {
        return appUpgrade;
    }

    public void setAppUpgrade(AppUpgradeBean appUpgrade) {
        this.appUpgrade = appUpgrade;
    }

    public LoadingAdvBean getLoadingAdv() {
        return loadingAdv;
    }

    public void setLoadingAdv(LoadingAdvBean loadingAdv) {
        this.loadingAdv = loadingAdv;
    }

    public static class UserBean {
        private int mowuserId;
        private String mowuserName;
        private String mowuserPwd;
        private String mowuserImei;
        private String mowuserCellphoneType;
        private long mowuserRegTime;
        private String mowuserNikeName;
        private String mowuserPersonImg;
        private String mowuserCellphone;
        private int mowuserCity;
        private int mowuserSex;
        private String mowuserQq;
        private String mowuserWeixin;
        private String mowuserWeibo;
        private String sessionId;

        public int getMowuserId() {
            return mowuserId;
        }

        public void setMowuserId(int mowuserId) {
            this.mowuserId = mowuserId;
        }

        public String getMowuserName() {
            return mowuserName;
        }

        public void setMowuserName(String mowuserName) {
            this.mowuserName = mowuserName;
        }

        public String getMowuserPwd() {
            return mowuserPwd;
        }

        public void setMowuserPwd(String mowuserPwd) {
            this.mowuserPwd = mowuserPwd;
        }

        public String getMowuserImei() {
            return mowuserImei;
        }

        public void setMowuserImei(String mowuserImei) {
            this.mowuserImei = mowuserImei;
        }

        public String getMowuserCellphoneType() {
            return mowuserCellphoneType;
        }

        public void setMowuserCellphoneType(String mowuserCellphoneType) {
            this.mowuserCellphoneType = mowuserCellphoneType;
        }

        public long getMowuserRegTime() {
            return mowuserRegTime;
        }

        public void setMowuserRegTime(long mowuserRegTime) {
            this.mowuserRegTime = mowuserRegTime;
        }

        public String getMowuserNikeName() {
            return mowuserNikeName;
        }

        public void setMowuserNikeName(String mowuserNikeName) {
            this.mowuserNikeName = mowuserNikeName;
        }

        public String getMowuserPersonImg() {
            return mowuserPersonImg;
        }

        public void setMowuserPersonImg(String mowuserPersonImg) {
            this.mowuserPersonImg = mowuserPersonImg;
        }

        public String getMowuserCellphone() {
            return mowuserCellphone;
        }

        public void setMowuserCellphone(String mowuserCellphone) {
            this.mowuserCellphone = mowuserCellphone;
        }

        public int getMowuserCity() {
            return mowuserCity;
        }

        public void setMowuserCity(int mowuserCity) {
            this.mowuserCity = mowuserCity;
        }

        public int getMowuserSex() {
            return mowuserSex;
        }

        public void setMowuserSex(int mowuserSex) {
            this.mowuserSex = mowuserSex;
        }

        public String getMowuserQq() {
            return mowuserQq;
        }

        public void setMowuserQq(String mowuserQq) {
            this.mowuserQq = mowuserQq;
        }

        public String getMowuserWeixin() {
            return mowuserWeixin;
        }

        public void setMowuserWeixin(String mowuserWeixin) {
            this.mowuserWeixin = mowuserWeixin;
        }

        public String getMowuserWeibo() {
            return mowuserWeibo;
        }

        public void setMowuserWeibo(String mowuserWeibo) {
            this.mowuserWeibo = mowuserWeibo;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }

    public static class SessionBean {
        private int mowsessionId;
        private int userId;
        private String mowsessionCode;
        private long createTime;

        public int getMowsessionId() {
            return mowsessionId;
        }

        public void setMowsessionId(int mowsessionId) {
            this.mowsessionId = mowsessionId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMowsessionCode() {
            return mowsessionCode;
        }

        public void setMowsessionCode(String mowsessionCode) {
            this.mowsessionCode = mowsessionCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

    public static class AdvBean {
        private int advId;
        private Object advImg;
        private int advIdx;
        private int advLinkType;
        private Object advLinkValue;
        private int advLocation;
        private Object album;

        public int getAdvId() {
            return advId;
        }

        public void setAdvId(int advId) {
            this.advId = advId;
        }

        public Object getAdvImg() {
            return advImg;
        }

        public void setAdvImg(Object advImg) {
            this.advImg = advImg;
        }

        public int getAdvIdx() {
            return advIdx;
        }

        public void setAdvIdx(int advIdx) {
            this.advIdx = advIdx;
        }

        public int getAdvLinkType() {
            return advLinkType;
        }

        public void setAdvLinkType(int advLinkType) {
            this.advLinkType = advLinkType;
        }

        public Object getAdvLinkValue() {
            return advLinkValue;
        }

        public void setAdvLinkValue(Object advLinkValue) {
            this.advLinkValue = advLinkValue;
        }

        public int getAdvLocation() {
            return advLocation;
        }

        public void setAdvLocation(int advLocation) {
            this.advLocation = advLocation;
        }

        public Object getAlbum() {
            return album;
        }

        public void setAlbum(Object album) {
            this.album = album;
        }
    }

    public static class AppUpgradeBean {
        private int appUpgradeId;
        private double appVersion;
        private String appDownloadURL;
        private long appReleaseDate;

        public int getAppUpgradeId() {
            return appUpgradeId;
        }

        public void setAppUpgradeId(int appUpgradeId) {
            this.appUpgradeId = appUpgradeId;
        }

        public double getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(double appVersion) {
            this.appVersion = appVersion;
        }

        public String getAppDownloadURL() {
            return appDownloadURL;
        }

        public void setAppDownloadURL(String appDownloadURL) {
            this.appDownloadURL = appDownloadURL;
        }

        public long getAppReleaseDate() {
            return appReleaseDate;
        }

        public void setAppReleaseDate(long appReleaseDate) {
            this.appReleaseDate = appReleaseDate;
        }
    }

    public static class LoadingAdvBean {
        private int loadingAdvId;
        private int advType;
        private String advImgUrl;
        private Object advForwardUrl;
        private Object advForwardUrlIos;
        private Object advForwardUrlAndroid;
        private long publishDate;

        public int getLoadingAdvId() {
            return loadingAdvId;
        }

        public void setLoadingAdvId(int loadingAdvId) {
            this.loadingAdvId = loadingAdvId;
        }

        public int getAdvType() {
            return advType;
        }

        public void setAdvType(int advType) {
            this.advType = advType;
        }

        public String getAdvImgUrl() {
            return advImgUrl;
        }

        public void setAdvImgUrl(String advImgUrl) {
            this.advImgUrl = advImgUrl;
        }

        public Object getAdvForwardUrl() {
            return advForwardUrl;
        }

        public void setAdvForwardUrl(Object advForwardUrl) {
            this.advForwardUrl = advForwardUrl;
        }

        public Object getAdvForwardUrlIos() {
            return advForwardUrlIos;
        }

        public void setAdvForwardUrlIos(Object advForwardUrlIos) {
            this.advForwardUrlIos = advForwardUrlIos;
        }

        public Object getAdvForwardUrlAndroid() {
            return advForwardUrlAndroid;
        }

        public void setAdvForwardUrlAndroid(Object advForwardUrlAndroid) {
            this.advForwardUrlAndroid = advForwardUrlAndroid;
        }

        public long getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(long publishDate) {
            this.publishDate = publishDate;
        }
    }
}
