package com.buzzfactory.dag

case class Position(x: Int, y: Int)
case class Size(width: Int, height: Int)
case class Rectangle(position: Position, size: Size)

trait DungeonElement {
  def position: Position
}
case class Wall(position: Position, material: String = "#") extends DungeonElement
case class Room(position: Position, size: Size, walls: List[Wall], doors: List[Door]) extends DungeonElement
case class Door(position: Position, isOpen: Boolean = false, isSecret: Boolean = false)
case class Corridor(position: Position, shape: List[Position]) extends DungeonElement
case class Trap(position: Position, size: Size, dpr: Int, rounds: Int) extends DungeonElement
case class DungeonFloor(rooms: List[Room], corridors: List[Corridor], traps: List[Trap] = List())

object Room {
  def apply(position: Position, size: Size, doors: List[Door]): Room = {
    // Works for 3x3 or bigger rooms
    val wallN = (position.x until position.x + size.width).map(x => new Wall(new Position(x, position.y))).toList
    val wallS = (position.x until position.x + size.width).map(x => new Wall(new Position(x, position.y+size.height))).toList
    val wallW =  (position.y+1 to position.y+size.height-2).map(y => new Wall(new Position(position.x, y))).toList
    val wallE =  (position.y+1 to position.y+size.height-2).map(y => new Wall(new Position(position.x+size.width, y))).toList
    new Room(position, size, wallN ::: wallS ::: wallW ::: wallE, doors)
  }
}

object Dungeon {

  // TODO: Read dungeon from file

  // dummy floor 0
  val doors = (new Door(new Position(9, 5)) :: Nil)
  val room = Room(new Position(0,0), new Size(10,10), doors)

  val floor0 = DungeonFloor(room::Nil, List(), List())

  def apply() = floor0
}