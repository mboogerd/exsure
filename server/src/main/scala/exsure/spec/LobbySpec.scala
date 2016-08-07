package exsure.spec

import exsure.domain._
/**
  *
  */
trait LobbySpec {

  def subscribe: Participant ⇒ Lobby ⇒ Lobby
  def unsubscribe: Participant ⇒ Lobby ⇒ Lobby
  def startGame: WorldDefinition ⇒ Lobby ⇒ Game
}
