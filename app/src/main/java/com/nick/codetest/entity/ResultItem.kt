package com.nick.codetest.entity

import com.google.gson.annotations.SerializedName

class ResultItem {
    @SerializedName("wrapperType")
    var wrapperType:String?=null
    @SerializedName("collectionType")
    var collectionType:String?=null

    @SerializedName("artistId")
    var artistId:String?=null
    @SerializedName("collectionId")
    var collectionId:String?=null
    @SerializedName("amgArtistId")
    var amgArtistId:String?=null
    @SerializedName("collectionName")
    var collectionName:String?=null
    @SerializedName("collectionCensoredName")
    var collectionCensoredName:String?=null
    @SerializedName("artistViewUrl")
    var artistViewUrl:String?=null
    @SerializedName("collectionViewUrl")
    var collectionViewUrl:String?=null
    @SerializedName("artworkUrl60")
    var artworkUrl60:String?=null
    @SerializedName("artworkUrl100")
    var artworkUrl100:String?=null
    @SerializedName("collectionPrice")
    var collectionPrice:String?=null
    @SerializedName("collectionExplicitness")
    var collectionExplicitness:String?=null
    @SerializedName("trackCount")
    var trackCount:Int?=null
    @SerializedName("copyright")
    var copyright:String?=null
    @SerializedName("country")
    var country:String?=null
    @SerializedName("currency")
    var currency:String?=null
    @SerializedName("releaseDate")
    var releaseDate:String?=null
    @SerializedName("primaryGenreName")
    var primaryGenreName:String?=null
}


/**
{
"wrapperType":"collection",
"collectionType":"Album",
"artistId":909253,
"collectionId":1469577723,
"amgArtistId":468749,
"artistName":"Jack Johnson",
"collectionName":"Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George",
"collectionCensoredName":"Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George",
"artistViewUrl":"https://music.apple.com/us/artist/jack-johnson/909253?uo=4",
"collectionViewUrl":"https://music.apple.com/us/album/jack-johnson-and-friends-sing-a-longs-and/1469577723?uo=4",
"artworkUrl60":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/60x60bb.jpg",
"artworkUrl100":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/100x100bb.jpg",
"collectionPrice":9.99,
"collectionExplicitness":"notExplicit",
"trackCount":15,
"copyright":"℗ 2014 Brushfire Records",
"country":"USA",
"currency":"USD",
"releaseDate":"2006-02-07T08:00:00Z",
"primaryGenreName":"Rock"
}
 */

/**
{
"wrapperType":"track",
"kind":"song",
"artistId":909253,
"collectionId":1469577723,
"trackId":1469577741,
"artistName":"Jack Johnson",
"collectionName":"Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George",
"trackName":"Upside Down",
"collectionCensoredName":"Jack Johnson and Friends: Sing-A-Longs and Lullabies for the Film Curious George",
"trackCensoredName":"Upside Down",
"artistViewUrl":"https://music.apple.com/us/artist/jack-johnson/909253?uo=4",
"collectionViewUrl":"https://music.apple.com/us/album/upside-down/1469577723?i=1469577741&uo=4",
"trackViewUrl":"https://music.apple.com/us/album/upside-down/1469577723?i=1469577741&uo=4",
"previewUrl":"https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/5e/5b/3d/5e5b3df4-deb5-da78-5d64-fe51d8404d5c/mzaf_13341178261601361485.plus.aac.p.m4a",
"artworkUrl30":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/30x30bb.jpg",
"artworkUrl60":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/60x60bb.jpg",
"artworkUrl100":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/ae/4c/d4/ae4cd42a-80a9-d950-16f5-36f01a9e1881/source/100x100bb.jpg",
"collectionPrice":9.99,
"trackPrice":1.29,
"releaseDate":"2005-01-01T12:00:00Z",
"collectionExplicitness":"notExplicit",
"trackExplicitness":"notExplicit",
"discCount":1,
"discNumber":1,
"trackCount":14,
"trackNumber":1,
"trackTimeMillis":208643,
"country":"USA",
"currency":"USD",
"primaryGenreName":"Rock",
"isStreamable":true
}
 */