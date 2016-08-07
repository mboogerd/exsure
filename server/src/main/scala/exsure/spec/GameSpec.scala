package exsure.spec

import exsure.domain._

/**
  *
  */
trait GameSpec {
  def updateMove: (Participant, GameAction) ⇒ Game ⇒ Game
  def tick: Game ⇒ Game
}
