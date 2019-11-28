package com.buzzfactory.dag


// 3d6+5
case class Dice(num: Int, sides: Int, plus: Int)

case class LivingBeing(position: Position, material: String, name: String, description: String, hp: Int, exp: Int, level: Int, alignment: Int, str: Int, int: Int, dex: Int, con: Int, wis: Int, cha: Int, thac0: Int, ac: Int, damage: Int) extends DungeonElement

object LivingBeing {
  def apply() = {

  }
}