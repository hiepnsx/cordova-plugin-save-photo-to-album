var cordova = require('cordova');

function SavePhotoToAlbum() {
}

SavePhotoToAlbum.prototype.save = function(url, onSuccess, onError) {
  cordova.exec(onSuccess, onError, "SavePhotoToAlbum", "save", [url]);
}

module.exports = new SavePhotoToAlbum();
