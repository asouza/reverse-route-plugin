package com.github.asouza.reverseroute

import play.router.RoutesCompilerHack
import play.core.{ PathPattern => PlayCorePathPattern }
import play.router.PathPart
import play.router.DynamicPart
import play.router.StaticPart
import play.core.{ DynamicPart => CoreDynamicPart }
import play.core.{ StaticPart => CoreStaticPart }

object UrlParametersExtractor {

  def apply(confRoute: String, uri: String): Map[String,String] = {
    val pathPattern: play.router.PathPattern = RoutesCompilerHack.transform("GET", confRoute)

    val routerCompilerParts = pathPattern.parts

    val coreParts = routerCompilerParts.map { part =>
      part match {
        case DynamicPart(name, constraint, _) => CoreDynamicPart(name, constraint, true)
        case StaticPart(name) => {
          //first slash was not being placed
          if (name.startsWith("/")) CoreStaticPart(name) else CoreStaticPart(s"/$name")
        }
      }
    }

    val corePathPattern = PlayCorePathPattern(coreParts)
    val result: Option[Map[String, Either[Throwable, String]]] = corePathPattern(uri)
    
    result.map { monsterMap =>
    	monsterMap.map { tuple =>
    	  val (key,tried) = tuple
    	  tried match {
    	    case Right(value) => (key,value)
    	    case Left(exception) => throw exception
    	  }
    	}
    }.getOrElse(Map[String,String]())


  }
}