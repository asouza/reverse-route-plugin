package play.router

import play.router.RoutesCompiler._

object RoutesCompilerHack {

	lazy val parser = new RouteFileParser
	
	def transform(verb:String,path:String):PathPattern = parser.parse(s"$verb $path  fakepackage.fakecontroller.fakemethod").get.head.asInstanceOf[Route].path
}