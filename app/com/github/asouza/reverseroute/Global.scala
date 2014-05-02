package com.github.asouza.reverseroute

import play.GlobalSettings
import play.api.{ GlobalSettings => ScalaGlobalSettings }
import play.api.mvc.RequestHeader
import play.api.mvc.Handler
import play.mvc.Http


object Global extends GlobalSettings with ScalaGlobalSettings {

  //conflict method between the java Global and Scala global
  override def getControllerInstance[A](controllerClass: Class[A]) = controllerClass.newInstance();

  override def onRequestReceived(request: RequestHeader): (RequestHeader, Handler) = {
    val (rh, handler) = super.onRequestReceived(request)
    val route = rh.tags("ROUTE_PATTERN")
    val urlParams = UrlParametersExtractor(route,request.uri)        
    
    val newParams = urlParams.foldLeft(rh.queryString)((paramsMap,tuple) => paramsMap.+(tuple._1 -> List(tuple._2)))
    
    val newRequestHeader = rh.copy(rh.id,rh.tags,rh.uri,rh.path,rh.method,rh.version,newParams,rh.headers,rh.remoteAddress)
   (newRequestHeader, handler)
  }
  
  
}