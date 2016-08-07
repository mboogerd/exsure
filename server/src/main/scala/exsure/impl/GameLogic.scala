package exsure.impl

import exsure.domain._
import exsure.spec.GameSpec


trait GameLogic extends GameSpec {

  /* API */

  override def updateMove: (Participant, GameAction) ⇒ Game ⇒ Game = (participant, action) ⇒ {
    updateGameState {
      updateWorldState {
        updateParticipant(participant)(_.copy(action = action))
      }
    }
  }

  override def tick: Game ⇒ Game = game ⇒
    game.copy(history = game.state :: game.history, state = computeGameState(game.state))


  /* Convenience methods */

  /* Good opportunity for lenses maybe? */
  def updateGameState(update: GameState ⇒ GameState)(game: Game): Game = game.copy(state = update(game.state))

  def updateWorldState(update: WorldState ⇒ WorldState)(gameState: GameState): GameState = gameState match {
    case PositioningGame(state) ⇒ PositioningGame(update(state))
    case SurvivalGame(state) ⇒ SurvivalGame(update(state))
  }

  def updateParticipant(participant: Participant)(update: Character ⇒ Character)(world: WorldState): WorldState = {
    world.characters.get(participant) match {
      case Some(character) ⇒ world.copy(characters = world.characters.updated(participant, update(character)))
      case None ⇒ world
    }
  }


  def computeGameState(gameState: GameState): GameState = ???
}
