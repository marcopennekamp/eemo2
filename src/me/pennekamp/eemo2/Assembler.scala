package me.pennekamp.eemo2

import java.io.Writer

class Assembler(ast: Conversation, writer: Writer, name: String) {

  val header =
    s"""function $name(input) {
      |var ls = [];
      |var rs = [];
      |var r = 0;
      |var out = "";
      |var i = 0;
      |function get_ltop() {
      |  if (ls.length == 0) {
      |     return 0;
      |  }
      |  return ls[ls.length - 1];
      |}
      |function read() {
      |  if (i < input.length) {
      |    r = input.charCodeAt(i);
      |    i += 1;
      |  } else {
      |    r = 0;
      |  }
      |}
      |""".stripMargin


  val footer =
    """return out;
      |}
      |""".stripMargin

  def compile(): Unit = {
    writer.write(header)
    compileConversation(ast)
    writer.write(footer)
  }

  def compileConversation(conversation: Conversation): Unit = {
    for (statement <- conversation.statements) {
      compileStatement(statement)
    }
  }

  def compileStatement(statement: Statement): Unit = {
    statement match {
      case conversation: Conversation => compileConversation(conversation)
      case command: Command => compileCommand(command)
    }
  }

  def compileCommand(command: Command): Unit = {
    import Command._

    val js = command match {
      case Add(a) => s"r += $a;"
      case Mul(a) => s"r *= $a;"
      case Halve() => "r = (r / 2) | 0;"
      case StackAdd() => "r += get_ltop();"
      case StackSub() => "r -= get_ltop();"
      case Reset() => "r = 0;"
      case MoveToLeft() => "ls.push(rs.pop());"
      case MoveToRight() => "rs.push(ls.pop());"
      case MoveToRegister() => "r = ls.pop();"
      case MoveToStack() => "ls.push(r); r = 0;"
      case CopyToLeftStack() => "ls.push(r);"
      case Output() => "out += String.fromCharCode(r);"
      case Input() => "read();"
      case IfPositive(conversation) =>
        writer.write("if (r > 0) {\n")
        compileConversation(conversation)
        writer.write("}\n")
        ""
      case WhilePositive(conversation) =>
        writer.write("while (r > 0) {\n")
        compileConversation(conversation)
        writer.write("}\n")
        ""
      case Nop() => "" // no operation
      case _ => "" // treat as no operation
    }

    if (js != "") {
      writer.write(s"$js\n")
    }
  }

}
