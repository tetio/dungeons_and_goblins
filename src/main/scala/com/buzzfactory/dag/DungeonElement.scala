package com.buzzfactory.dag

case class Position(x: Int, y: Int)

case class Size(width: Int, height: Int)

case class Rectangle(position: Position, size: Size)

trait DungeonElement {
  def position: Position

  def material: String
}

case class Wall(position: Position, material: String = "#") extends DungeonElement

case class Room(position: Position, size: Size, walls: List[Wall], doors: List[Door], material: String = "#") extends DungeonElement

case class Door(position: Position, isOpen: Boolean = false, isSecret: Boolean = false, material: String = "+")

case class Corridor(position: Position, shape: List[Position], material: String = "#") extends DungeonElement

case class Trap(position: Position, size: Size, dpr: Int, rounds: Int, material: String = ".") extends DungeonElement

case class DungeonFloor(rooms: List[Room], corridors: List[Corridor], traps: List[Trap] = List(), material: String = " ")

object Room {
  def apply(position: Position, size: Size, doors: List[Door]): Room = {
    // Works for 3x3 or bigger rooms
    val wallN = (position.x until position.x + size.width).map(x => new Wall(new Position(x, position.y))).toList
    val wallS = (position.x until position.x + size.width).map(x => new Wall(new Position(x, position.y + size.height - 1))).toList
    val wallW = (position.y + 1 to position.y + size.height - 2).map(y => new Wall(new Position(position.x, y))).toList
    val wallE = (position.y + 1 to position.y + size.height - 2).map(y => new Wall(new Position(position.x + size.width - 1, y))).toList
    new Room(position, size, wallN ::: wallS ::: wallW ::: wallE, doors)
  }
}

object Dungeon {

  // TODO: Read dungeon from file

  // dummy floor 0
  val rooms = List(Room(new Position(0, 0), new Size(10, 10), List(Door(Position(9, 5)))),
    Room(new Position(20, 0), new Size(10, 10),
      List(Door(Position(20, 5)), Door(Position(29, 8)), Door(Position(5, 9)))),
    Room(new Position(60, 0), new Size(18, 6),
      List(Door(Position(69, 5)))),
    Room(new Position(25, 15), new Size(30, 9),
      List(Door(Position(25, 19)), Door(Position(54, 19)))))
  val corridors = List(Corridor(Position(10, 5), List(Position(19, 5))),
    Corridor(Position(30, 8), List(Position(48, 8), Position(48, 3), Position(60, 3))),
    Corridor(Position(5, 10), List(Position(5, 19), Position(24, 19))),
    Corridor(Position(55, 19), List(Position(69, 19), Position(54, 6))))
  // Position(69, 19) :: Position(54,6) :: Nil

  val floor0 = DungeonFloor(rooms, corridors, List())

  def apply() = floor0
}