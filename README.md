# cordova-plugin-save-photo-to-album
A cordova plugin for iOS &amp; android to save image to photo album
```js
document.addEventListener("deviceready", function(){
    var url = "http://example.com/images/1.gif"
             || "file:///var/mobile/Applications/1.gif";
    cordova.plugins.savePhotoToAlbum.save(url, function(nativeURL) {
        console.log(nativeURL);
    }, function(err) {
        console.error(err);
    });
});
```