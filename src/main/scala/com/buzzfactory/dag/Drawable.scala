package com.buzzfactory.dag

import com.buzzfactory.dag.GameView.drawString
import com.googlecode.lanterna.TextColor

import scala.annotation.tailrec

object Drawable {
  def apply(floor: DungeonFloor, viewPos: Position, viewSize: Position): Unit = {
    val corridors = floor.corridors.filter(c => c.isInside(viewPos, viewSize))
    drawCorridors(floor.corridors)
    drawRooms(floor.rooms)
  }

  def drawRooms(rooms: List[Room]): Unit = {
    rooms.foreach(room => {
      room.walls.foreach(wall => drawString(wall.position, wall.material, TextColor.ANSI.WHITE))
      room.doors.foreach(door => drawString(door.position, door.material, TextColor.ANSI.YELLOW))
    })
  }

  def drawCorridor(corridor: Corridor, cleanPath: Boolean = false): Unit = {
    @tailrec
    def draw(ini: Position, end: Position, shape: List[Position]): Unit = {
      if (ini.x != end.x) (ini.x to end.x).foreach(x => {
        if (cleanPath) {
          drawString(x, ini.y, " ", TextColor.ANSI.WHITE)
        } else {
          drawString(x, ini.y - 1, corridor.material, TextColor.ANSI.WHITE)
          drawString(x, ini.y + 1, corridor.material, TextColor.ANSI.WHITE)
        }
      })
      if (ini.y != end.y) {
        val y0 = Math.min(ini.y, end.y)
        val y1 = Math.max(ini.y, end.y)
        if (cleanPath) (y0 to y1).foreach(y => {
          drawString(ini.x, y, " ", TextColor.ANSI.WHITE)
        }) else (y0 - 1 to y1 + 1).foreach(y => {
          drawString(ini.x - 1, y, corridor.material, TextColor.ANSI.WHITE)
          drawString(ini.x + 1, y, corridor.material, TextColor.ANSI.WHITE)
        })
      }
      shape match {
        case head :: tail => draw(end, head, tail)
        case _ => Unit
      }
    }
    draw(corridor.position, corridor.shape.head, corridor.shape.tail)
  }

  def drawCorridors(corridors: List[Corridor]): Unit = {
    corridors.foreach(corridor => {
      drawCorridor(corridor)
      drawCorridor(corridor, cleanPath = true)
    })
  }
}