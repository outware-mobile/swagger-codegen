#import <Foundation/Foundation.h>
#import "AFHTTPSessionManager.h"


/**
 *  Use on NSError* returned in completion handlers for the response body
 */
extern NSString *const kErrorResponseBodyKey;


@interface SWGApiClient : AFHTTPSessionManager

@property(nonatomic, assign) NSURLRequestCachePolicy cachePolicy;
@property(nonatomic, assign) NSTimeInterval timeoutInterval;
@property(nonatomic, assign) BOOL logRequests;
@property(nonatomic, assign) BOOL logCacheHits;
@property(nonatomic, assign) BOOL logServerResponses;
@property(nonatomic, assign) BOOL logJSON;
@property(nonatomic, assign) BOOL logHTTP;
@property(nonatomic, readonly) NSOperationQueue* queue;

+(SWGApiClient *)sharedClientFromPool:(NSString *)baseUrl;

+(NSOperationQueue*) sharedQueue;

+(void)setLoggingEnabled:(bool) state;

+(void)clearCache;

+(void)setCacheEnabled:(BOOL) enabled;

+(unsigned long)requestQueueSize;

+(void) setOfflineState:(BOOL) state;

+(AFNetworkReachabilityStatus) getReachabilityStatus;

+(NSNumber*) nextRequestId;

+(NSNumber*) queueRequest;

+(void) cancelRequest:(NSNumber*)requestId;

+(NSString*) escape:(id)unescaped;

+(void) setReachabilityChangeBlock:(void(^)(int))changeBlock;

+(void) configureCacheReachibilityForHost:(NSString*)host;

-(void)setHeaderValue:(NSString*) value
                forKey:(NSString*) forKey;

-(NSNumber*)  dictionary:(NSString*) path
                  method:(NSString*) method
             queryParams:(NSDictionary*) queryParams
                    body:(id) body
            headerParams:(NSDictionary*) headerParams
      requestContentType:(NSString*) requestContentType
     responseContentType:(NSString*) responseContentType
         completionBlock:(void (^)(NSDictionary*, NSError *))completionBlock;

@end
