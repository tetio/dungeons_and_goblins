package com.buzzfactory.dag

import com.buzzfactory.dag.GameView.drawString
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.{DefaultTerminalFactory, Terminal}

import scala.annotation.tailrec

object GameView {
  val terminal: Terminal = new DefaultTerminalFactory().createTerminal
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
    openManiMenu()
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



  def openManiMenu(): Unit = {
    renderMainMenu()
    drawString(Position(2, 20), "Hello DM!", TextColor.ANSI.RED)
    screen.refresh()
    mainMenuAction()
  }

  @tailrec def mainMenuAction(): Unit = {
    val k = readKey().toString.toUpperCase
    k match {
      case "S" => startGame(GameState())
      case "Q" => end()
      case _ => mainMenuAction()
    }
  }

  def initFloor(floor: Int) = Dungeon.floor0

  def startGame(state: GameState): Unit = {
    val floor = initFloor(state.dungeonFloor)
    // TODO
    renderGame(floor)
    screen.refresh()
    // updateGame()
  }

  def renderGame(floor: DungeonFloor): Unit = {
    // TODO rooms in visible area
    // TODO rooms in visited area

    // Test data
    Drawable(floor.rooms)
  }

  /////////////////////////////////////////////////////////
  // Input management
  def readKey(): Character = {
    terminal.readInput().getCharacter
  }

  def readLastKey(): Character = {
    terminal.pollInput().getCharacter
  }

}

object Drawable {
  def apply(rooms: List[Room]): Unit = {
    rooms.foreach(room => room.walls.foreach(wall => drawString(wall.position, wall.material, TextColor.ANSI.WHITE)))
  }
}
