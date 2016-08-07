package exsure.impl

import java.util.Date

import exsure.domain._
import exsure.spec.ServerSpec

/**
  *
  */
trait ServerLogic extends ServerSpec {

  override def createLobby: StartCondition ⇒ Lobby = Lobby(new Date, Map.empty, _)
}
