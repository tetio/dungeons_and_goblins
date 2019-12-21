package com.buzzfactory.dag

case class Hero(level: Int, str: Int, dex: Int, int: Int, hp: Int, pos: Position) {
  def isAlive: Boolean = (hp > 0)
}
object Hero {
  def apply(): Hero = Hero(1, 10, 10, 10, 8, Position(39, 11))
}
case class GameState(hero: Hero, dungeonFloor: Int, viewPos: Position, viewSize: Position, deepestFloor: Int = 0)

object GameState {
  def apply(): GameState = {
    GameState(Hero(), 0, Position(0, 0), Position(80, 24))
  }
  def apply(state: GameState, pos: Position): GameState = {
    state.copy(viewPos = state.viewPos.add(pos.x,pos.y), hero = state.hero.copy(pos = state.hero.pos.add(pos.x,pos.y)))
  }
}