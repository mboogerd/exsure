package exsure.domain

import java.util.Date

/**
  *
  */
trait DomainEntities {

  /**
    * Represents a human agent its contributed fees
    */
  case class Account(firstName: String, lastName: String, money: Double) {
    def credit: Int = ???
  }

  /**
    * Represents all possible actions a character may perform
    */
  sealed trait Action

  sealed trait GameSetupAction extends Action
  case class Spawn(position: Position) extends GameSetupAction

  sealed trait GameAction extends Action
  case object Defend extends GameAction
  case class Move(position: Position) extends GameAction
  case class Attack(character: Character) extends GameAction

  /**
    * Represents a character contributed fee allowing access to the game.
    */
  case class BuyIn(credits: Int) {

  }

  /**
    * Represents a participant in-game
    * @param participant
    * @param position The location of the character in the world
    * @param strength The accumulated power of this character
    */
  case class Character(participant: Participant, strength: Int, position: Position = Position(0, 0), action: GameAction = Defend) extends WorldEntity

  /**
    * Represents a single game
    * @param participants Those that have contributed a positive buyin in order to participate in the game
    * @param world Represents the immutable characteristics of the game
    * @param state Represents the current state of affairs
    * @param history previous GameStates, in anti-chonological order
    */
  case class Game(participants: Set[Participant], world: WorldDefinition, state: GameState, history: List[GameState] = List.empty)

  /**
    * Represents the different phases of the game
    */
  sealed trait GameState
  case class PositioningGame(worldState: WorldState) extends GameState
  case class SurvivalGame(worldState: WorldState) extends GameState


  trait StartCondition {

    /**
      * Evaluates whether the game can start based on the state of the lobby
      * @return true if the game can start, false otherwise
      */
    def evaluate: Lobby ⇒ Boolean
  }


  /**
    * Represents the game-setup, accounts may subscribe by posting a positive buyIn.
    * @param subscriptions
    */
  case class Lobby(createdDate: Date, subscriptions: Map[Account, BuyIn], startCondition: StartCondition)

  sealed trait WorldEntity {
    def position: Position
  }
  /**
    * Represents a participation of an account, backed by a positive buyIn
    */
  case class Participant(account: Account, buyIn: BuyIn)

  /**
    * A PayoutPoint represents a location in the World that donates credit to players proportional to their distance to
    * the point
    * @param position
    * @param value
    */
  case class PayoutPoint(position: Position, value: Int) extends WorldEntity

  /**
    * The position of an entity in the world
    * @param x
    * @param y
    */
  case class Position(x: Float, y: Float) {

  }
  /**
    * Defines immutable characteristics of a world, i.e. those characteristics that won't change during the course of a game
    */
  case class WorldDefinition(width: Int, height: Int)

  /**
    * Represents
    * @param payoutPoints
    * @param characters
    */
  case class WorldState(payoutPoints: Set[PayoutPoint], characters: Map[Participant, Character])
}
