package com.mwkj.util;

//常量， 放URL等。。。
public interface Constant {

    String HOME = "http://www.mow99.com/";

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

}
