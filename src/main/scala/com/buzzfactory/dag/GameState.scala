package com.buzzfactory.dag

case class Hero(level: Int, str: Int, dex: Int, int: Int, hp: Int) {
  def isAlive: Boolean = (hp > 0)
}
object Hero {
  def apply(): Hero = Hero(1, 10, 10, 10, 8)
}
case class GameState(hero: Hero, dungeonFloor: Int, deepestFloor: Int = 0)

object GameState {
  def apply(): GameState = {
    GameState(Hero(), 0)
  }
}