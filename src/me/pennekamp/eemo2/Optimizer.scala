package me.pennekamp.eemo2

import me.pennekamp.eemo2.Command.{Add, Mul, Reset}

object Optimizer {

  def optimize(ast: Conversation): Conversation = optimizeConversation(ast)

  private def optimizeConversation(conversation: Conversation): Conversation = {
    Conversation(mergeAdjacentStatements(conversation.statements))
  }

  private def mergeAdjacentStatements(statements: List[Statement]): List[Statement] = {
    statements match {
      case (s1 :: s2 :: rest) =>
        val mergedOpt = (s1, s2) match {
          // Multiple additions can be merged, obviously.
          // This also includes subtractions.
          case (Add(x), Add(y)) => Some(Add(x + y))

          // Multiple multiplications can be merged.
          case (Mul(x), Mul(y)) => Some(Mul(x * y))

          // Resets are idempotent.
          case (Reset(), Reset()) => Some(Reset())

          // In other cases, we don't optimize, yet.
          case _ => None
        }

        mergedOpt match {
          case Some(s) => mergeAdjacentStatements(s :: rest)
          case None => s1 :: mergeAdjacentStatements(s2 :: rest)
        }
      case _ => statements // Nothing to merge here.
    }
  }

}
