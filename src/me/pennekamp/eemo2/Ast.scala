package me.pennekamp.eemo2

trait Statement

case class Conversation(statements: List[Statement]) extends Statement

trait Command extends Statement

object Command {
  case class Add(a: Int) extends Command
  case class Mul(a: Int) extends Command
  case class Halve() extends Command
  case class StackAdd() extends Command
  case class StackSub() extends Command
  case class Reset() extends Command
  case class MoveToLeft() extends Command
  case class MoveToRight() extends Command
  case class MoveToRegister() extends Command
  case class MoveToStack() extends Command
  case class CopyToLeftStack() extends Command
  case class Output() extends Command
  case class Input() extends Command
  case class IfPositive(conversation: Conversation) extends Command
  case class WhilePositive(conversation: Conversation) extends Command
  case class Nop() extends Command
}
