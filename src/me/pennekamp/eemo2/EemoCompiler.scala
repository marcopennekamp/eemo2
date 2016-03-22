package me.pennekamp.eemo2

import java.io.Writer

import me.pennekamp.eemo2.parser.{EemoLexer, EemoParser}
import org.antlr.v4.runtime.{ANTLRInputStream, CommonTokenStream}

class EemoCompiler(val source: String) {
  val sourceStream = new ANTLRInputStream(source)
  val lexer = new EemoLexer(sourceStream)
  val tokenStream = new CommonTokenStream(lexer)
  val parser = new EemoParser(tokenStream)
  val entryContext = parser.discussion()
  lazy val ast = new ToAstVisitor().visitDiscussion(entryContext)

  def printParseTree(): Unit = {
    println(entryContext.toStringTree)
  }

  def printAst(): Unit = {
    println(ast.toString)
  }

  def compileToAsmJs(name: String, writer: Writer): Unit = {
    val optimizedAst = Optimizer.optimize(ast)
    val assembler: Assembler = new Assembler(optimizedAst, writer, name)
    assembler.compile()
  }
}
