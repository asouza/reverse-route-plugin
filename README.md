#Reason

I would like to get the configured route based on the requested URI. This project uses 
internal classes of Play to achieve this object. So, using the Global object of
this project, all url parameters will be available as request parameters in
querystring :).

If you have, for example this configuration: 

```
GET		/path/:id/resource/:resourceId controllers.Application.test(id:Long,resourceId:Long)
```
You can request.queryString("id") and request.queryString("resourceId") to get those
values in your application.