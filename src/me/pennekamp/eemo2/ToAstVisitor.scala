package me.pennekamp.eemo2

import collection.JavaConversions._
import me.pennekamp.eemo2.parser.EemoBaseVisitor
import me.pennekamp.eemo2.parser.EemoParser._
import org.antlr.v4.runtime.tree.TerminalNode

class ToAstVisitor extends EemoBaseVisitor[Statement] {

  override def visitDiscussion(ctx: DiscussionContext): Conversation = {
    visitConversation(ctx.conversation())
  }

  override def visitConversation(ctx: ConversationContext): Conversation = {
    val statementContexts: Seq[StatementContext] = ctx.statement()
    Conversation(statementContexts.map(visitStatement))
  }

  override def visitStatement(ctx: StatementContext): Statement = {
    ctx.children.head match {
      // Simple statement.
      case n: TerminalNode =>
        n.getSymbol.getType match {
          case Increment => Command.Increment()
          case Double => Command.Double()
          case Add => Command.Add()
          case Decrement => Command.Decrement()
          case Halve => Command.Halve()
          case Sub => Command.Sub()
          case Reset => Command.Reset()
          case MoveToLeft => Command.MoveToLeft()
          case MoveToRight => Command.MoveToRight()
          case MoveToRegister => Command.MoveToRegister()
          case MoveToStack => Command.MoveToStack()
          case CopyToLeftStack => Command.CopyToLeftStack()
          case Output => Command.Output()
          case Input => Command.Input()
          case Nop => Command.Nop()
        }

      // No special handling for that context.
      case childCtx => visit(childCtx)
    }
  }

  override def visitIfPositive(ctx: IfPositiveContext): Command.IfPositive = {
    Command.IfPositive(visitConversation(ctx.conversation()))
  }

  override def visitWhilePositive(ctx: WhilePositiveContext): Statement = {
    Command.WhilePositive(visitConversation(ctx.conversation()))
  }

}
