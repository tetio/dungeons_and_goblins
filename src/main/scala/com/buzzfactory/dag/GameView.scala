package com.buzzfactory.dag

import com.buzzfactory.dag.GameView.drawString
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.{KeyType}
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.{DefaultTerminalFactory, Terminal}

import scala.annotation.tailrec

object GameView {
  val terminal: Terminal = new DefaultTerminalFactory().createTerminal()
  val screen = new TerminalScreen(terminal)
  val tg: TextGraphics = screen.newTextGraphics()
  //  screen.setCursorPosition(null)
  //  screen.startScreen()


  def init(): Unit = {
    screen.setCursorPosition(null)
    screen.startScreen()
  }

  def end(): Unit = {
    screen.stopScreen()
    terminal.exitPrivateMode()
  }

  def clearScreen(): Unit = {
    screen.clear()
  }

  def main(args: Array[String]): Unit = {
    init()
    openMainMenu()
  }


  def drawString(x: Int, y: Int, text: String, fgColor: TextColor = TextColor.ANSI.WHITE): Unit = {
    tg.setForegroundColor(fgColor)
    tg.putString(x, y, text)
  }

  def drawString(position: Position, text: String, fgColor: TextColor): Unit = {
    drawString(position.x, position.y, text, fgColor)
  }

  def renderMainMenu(): Unit = {
    val x = 10
    val y = 2
    drawString(x, y, "************************************************************")
    drawString(x, y + 1, "*                                                          *")
    drawString(x, y + 2, "*                  DUNGEONS AND GOBLINS                    *")
    drawString(x, y + 3, "*                                                          *")
    drawString(x, y + 4, "************************************************************")

    drawString(x + 27, y + 12, "Press")
    drawString(x + 20, y + 13, "'S' to start the game", TextColor.ANSI.CYAN)
    drawString(x + 20, y + 14, "'Q' to quit the game", TextColor.ANSI.MAGENTA)
  }


  def openMainMenu(): Unit = {
    renderMainMenu()
    drawString(Position(2, 20), "Hello DM!", TextColor.ANSI.RED)
    screen.refresh()
    mainMenuAction()
  }

  @tailrec def mainMenuAction(): Unit = {
    readKey(true) match {
      case "S" => startGame(GameState())
      case "Q" => end()
      case "UP" => mainMenuAction()
      case "DOWN" => mainMenuAction()
      case "LEFT" => mainMenuAction()
      case "RIGHT" => mainMenuAction()
      case _ => mainMenuAction()
    }
  }

  @tailrec def update(floor: DungeonFloor, state: GameState): Unit = {
    renderGame(floor)
    screen.refresh()
    Thread.sleep(100)
    readKey() match {
      case "S" => update(floor, state)
      case "Q" => end()
      case "UP" => update(floor, state)
      case "DOWN" => update(floor, state)
      case "LEFT" => update(floor, state)
      case "RIGHT" => update(floor, state)
      case _ => update(floor, state)
    }
  }


  def initFloor(floor: Int) = Dungeon.floor0

  def startGame(state: GameState): Unit = {
    clearScreen()
    val floor = initFloor(state.dungeonFloor)
    update(floor, state)
  }

  def renderGame(floor: DungeonFloor): Unit = {
    // TODO rooms in visible area
    // TODO rooms in visited area

    // Test data
    Drawable(floor)
  }

  /////////////////////////////////////////////////////////
  // Input management
  //  def readKey(): Character = {
  //    terminal.readInput().getCharacter
  //  }

  def readKey(blocking: Boolean = false): String = {
    val key = if (blocking) terminal.readInput() else terminal.pollInput()
    if (key != null)
      key.getKeyType match {
        case KeyType.Character => key.getCharacter.toString.toUpperCase
        case KeyType.ArrowDown => "DOWN"
        case KeyType.ArrowUp => "UP"
        case KeyType.ArrowLeft => "LEFT"
        case KeyType.ArrowRight => "RIGHT"
        case _ => "UNKNOWN"
      }
    else
      "NONE"
  }

  def readLastKey(): Character = {
    terminal.pollInput().getCharacter
  }

}

object Drawable {

  def apply(floor: DungeonFloor): Unit = {
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
      drawCorridor(corridor, true)
    })
  }
}
