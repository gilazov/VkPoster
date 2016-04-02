# VkPoster- my android MVP example
VkPoster - simple app that shows vk posts and previews and attempt to use mvp architecture pattern (It's just beta)
## User interface
### The main screen
Shows user posts includes: text, date, owner, image (if postType=Photo)

<img src="/Screenshot_2016-04-02-11-03-21.png" width="320">

###The second screen
Shows preview of post: image, owner, date, likes and text

<img src="/Screenshot_2016-04-02-11-25-22.png" width="320">

## Data source

There are two levels of data persistence:
+ In-memory cache
+ Network

Cache logic (PostRepository)
+ Default data gets all posts form cash. If is empty, cache update from vk
+ Pull to refresh clear cache and update data
+ Get more data gives new portion from vk server and save changes to cache

##Testing
###Tools 
Mockito + junit+ robolectric
###View
Mock presenter and verify destroy
###Presenter :
Mock PostRepository, View, verify thats view shows proper view for other data
###Mapper:
Verify proper mapping from VK objects to View objects

##Structure

+ Activity manages fragments showing
+ Post fragment is VIew of MVP
+ Presenter gets posts from postRepositories
+ PostRepository manage caching
+ VKService gets VKpost, maps to VO and get VO to PostRepository
+ Using callbacks for interaction between layer

