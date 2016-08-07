package exsure.impl

import exsure.domain._

/**
  *
  */
object StartConditions {

  case class MaxPlayers(count: Int) extends StartCondition {
    /**
      * Evaluates whether the game can start based on the state of the lobby
      *
      * @return true if the game can start, false otherwise
      */
    override def evaluate: Lobby ⇒ Boolean = _.subscriptions.size == count
  }
}
