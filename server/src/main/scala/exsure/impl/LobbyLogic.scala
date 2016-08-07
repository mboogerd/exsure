package exsure.impl

import exsure.domain._
import exsure.spec.LobbySpec


trait LobbyLogic extends LobbySpec {

  override def subscribe: Participant ⇒ Lobby ⇒ Lobby = participant ⇒ lobby ⇒
    lobby.copy(subscriptions = lobby.subscriptions.updated(participant.account, participant.buyIn))


  override def unsubscribe: Participant ⇒ Lobby ⇒ Lobby = participant ⇒ lobby ⇒
    lobby.copy(subscriptions = lobby.subscriptions - participant.account)

  override def startGame: WorldDefinition ⇒ Lobby ⇒ Game = world ⇒ lobby ⇒ Game(
    participants = lobby.subscriptions.keySet.map(act ⇒ Participant(act, lobby.subscriptions(act))),
    world = world,
    state = PositioningGame(createWorldState)
  )

  def createWorldState: WorldState = WorldState(Set.empty, Map.empty) // TODO: Requires proper setup
}