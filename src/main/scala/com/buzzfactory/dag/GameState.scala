package com.buzzfactory.dag

case class Hero(level: Int, str: Int, dex: Int, int: Int, hp: Int) {
  def isAlive: Boolean = (hp > 0)
}
object Hero {
  def apply(): Hero = Hero(1, 10, 10, 10, 8)
}
case class GameState(hero: Hero, dungeonFloor: Int, view: Rectangle, viewOffset: Position = Position(0, 0), deepestFloor: Int = 0)

object GameState {
  def apply(): GameState = {
    GameState(Hero(), 0, Rectangle(Position(0, 0), Size(80, 24)))
  }
  def apply(state: GameState, incPos: Position): GameState =
    state.copy(viewOffset = state.viewOffset.add(incPos))

  def apply(state: GameState, initialView: Rectangle): GameState =
    state.copy(view = initialView)
}