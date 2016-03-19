package me.pennekamp.eemo2

import java.io.{File, FileWriter}
import java.nio.file.Paths

object Main {

  def main(args: Array[String]) {
    if (args.length < 2) {
      println("Please specify an output file and a source folder.")
      return
    }

    val debug = false

    val outPath = args(0)
    val srcFolder = args(1)
    val sourcePaths = new File(srcFolder)
      .listFiles()
      .filter(f => f.isFile && f.getName.endsWith(".ee"))
      .map(_.getPath) // Get files.
      .map(p => p.substring(0, p.lastIndexOf('.'))) // Remove the .ee ending.

    val writer = new FileWriter(s"$outPath.js")

    sourcePaths.foreach { sourcePath =>
      val fileName = Paths.get(sourcePath).getFileName.toString
      val source = io.Source.fromFile(s"$sourcePath.ee").mkString

      if (debug) {
        println("Source:")
        println(source)
        println()
      }

      val compiler = new EemoCompiler(source)

      if (debug) {
        compiler.printParseTree()
        println()
        compiler.printAst()
        println()
      }

      compiler.compileToAsmJs(fileName, writer)
    }

    writer.flush()
  }

}
