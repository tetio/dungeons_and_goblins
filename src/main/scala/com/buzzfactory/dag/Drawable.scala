package com.buzzfactory.dag

import com.buzzfactory.dag.GameView.drawString
import com.googlecode.lanterna.TextColor

import scala.annotation.tailrec

object Drawable {
  def apply(floor: DungeonFloor, view: Rectangle, viewOffset: Position): Unit = {
    val corridors = floor.corridors.filter(c => c.isInside(view))
    val rooms = floor.rooms.filter(r => r.isInside(view))
    drawCorridors(corridors, viewOffset)
    drawRooms(rooms, viewOffset)
  }

  def drawRooms(rooms: List[Room], viewOffset: Position): Unit = {
    rooms.foreach(room => {
      room.walls.foreach(wall => drawString(wall.position.add(viewOffset), wall.material, TextColor.ANSI.WHITE))
      room.doors.foreach(door => drawString(door.position.add(viewOffset), door.material, TextColor.ANSI.YELLOW))
    })
  }

  def drawCorridor(corridor: Corridor, viewOffset: Position, cleanPath: Boolean = false): Unit = {
    @tailrec
    def draw(ini: Position, end: Position, shape: List[Position]): Unit = {
      if (ini.x != end.x) (ini.x to end.x).foreach(x => {
        if (cleanPath) {
          drawString(x+viewOffset.x, ini.y+viewOffset.y, " ", TextColor.ANSI.WHITE)
        } else {
          drawString(x+viewOffset.x, ini.y+viewOffset.y - 1, corridor.material, TextColor.ANSI.WHITE)
          drawString(x+viewOffset.x, ini.y+viewOffset.y + 1, corridor.material, TextColor.ANSI.WHITE)
        }
      })
      if (ini.y != end.y) {
        val y0 = Math.min(ini.y, end.y)
        val y1 = Math.max(ini.y, end.y)
        if (cleanPath) (y0 to y1).foreach(y => {
          drawString(ini.x, y, " ", TextColor.ANSI.WHITE)
        }) else (y0 - 1 to y1 + 1).foreach(y => {
          drawString(ini.x+viewOffset.x - 1, y+viewOffset.y, corridor.material, TextColor.ANSI.WHITE)
          drawString(ini.x+viewOffset.x + 1, y+viewOffset.y, corridor.material, TextColor.ANSI.WHITE)
        })
      }
      shape match {
        case head :: tail => draw(end, head, tail)
        case _ => Unit
      }
    }
    draw(corridor.position, corridor.shape.head, corridor.shape.tail)
  }

  def drawCorridors(corridors: List[Corridor], viewOffset: Position): Unit = {
    corridors.foreach(corridor => {
      drawCorridor(corridor, viewOffset)
      drawCorridor(corridor,  viewOffset, cleanPath = true)
    })
  }
}