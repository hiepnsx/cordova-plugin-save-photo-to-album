#import <Cordova/CDVPlugin.h>
#import <objc/message.h>
#import <AssetsLibrary/AssetsLibrary.h>

@interface SavePhotoToAlbum : CDVPlugin {}

- (void)save:(CDVInvokedUrlCommand*)command;

@end
