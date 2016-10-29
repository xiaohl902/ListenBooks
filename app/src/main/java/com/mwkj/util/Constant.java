package com.mwkj.util;

//常量， 放URL等。。。
public interface Constant {

     String HOME = "http://www.mow99.com/";

    //用户登录资料，欢迎页图片,更新的apk地址
    String USER_WELCOME = "MOWLoadingV11?userId=141610&channelNumber=0&appVersion=1.7&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆
   String STORYTELLING =  "PSIndex2?session=1416101477468719685&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //相声
   String CROSSTALK =  "XSIndex2?session=1416101477468719685&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //专题
   String SPECIAL =  "subjectIndex?pageSize=8&pageNumber=1&session=1416101477468719685&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //艺术家 pageNumber=%d
  String ARTIST = "http://www.mow99.com/ArtistIndex?pageSize=10&pageNumber=%d&session=1416101477468719685&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //艺术家-艺术家详情 artistId=%d  pageNumber=%d
    String ARTIST_INFO = "http://www.mow99.com/GetAlbumsByArtist?albumType=0&artistId=%d&pageSize=8&pageNumber=%d&session=1416551477485726424&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=352284040546495";

    //艺术家-艺术家-作品详情 albumId=%d、pageNumber=%d
    String ARTIST_WORK_INFO = "http://www.mow99.com/GetChapters?albumId=%d&pageSize=30&pageNumber=%d&session=1416551477618041415&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=352284040546495";

    //演出
    String SHOW = "ShowIndex?cityName=&showDate=&pageSize=8&pageNumber=1&session=1416101477572317117&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //社区
    String  COMMUNITY= "FourmIndex?pageSize=100&pageNumber=1&session=1416101477572317117&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆 - 经典
    String JINGDIAN="GetAlbumsByContentType?albumType=1&contentType=1&pageSize=8&session=1416101477572317117&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  - 侠义
    String XIAYI = "GetAlbumsByContentType?albumType=1&contentType=2&pageSize=8&session=1416101477572317117&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆   - 公案
    String GONGAN="GetAlbumsByContentType?albumType=1&contentType=3&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  - 历史
    String LISHI="GetAlbumsByContentType?albumType=1&contentType=4&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  -  古龙
    String GULONG="GetAlbumsByContentType?albumType=1&contentType=4&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  - 动漫
    String DONGMAN="GetAlbumsByContentType?albumType=1&contentType=9&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  - 粤语
    String YUEYU="GetAlbumsByContentType?albumType=1&contentType=10&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //听书馆  - 全部  热门评书 - 更多
    String ALL="GetAlbumsByContentType?albumType=1&contentType=0&pageSize=8&session=&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=864394010755355";

    //专题
    String ZHUANTI="subjectIndex?pageSize=8&session=1416561477736880843&SecurityID=mow99_2014_12_30_@~$;&clientVersion=170&OS=1&imei=244001484552444";

}


