package com.buzzfactory.dag

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
    drawString(Position(79, 23), "@", new TextColor.RGB(255,255,0))
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

  def update(floor: DungeonFloor, state: GameState): Unit = {

    @tailrec def _update(state: GameState): Unit = {
      clearScreen()
      renderGame(floor)
      screen.refresh()
      //Thread.sleep(50)
      readKey(true) match {
        case "S" => _update(state)
        case "Q" => end()
        case "UP" => update(DungeonFloor(floor, 0, 1), state)
        case "DOWN" => update(DungeonFloor(floor, 0, -1), state)
        case "LEFT" => update(DungeonFloor(floor, 1, 0), state)
        case "RIGHT" => update(DungeonFloor(floor, -1, 0), state)
        case _ => _update(state)
      }
    }

    _update(state)
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

    // Draw hero
    drawString(Position(39, 11), "@", new TextColor.RGB(255,255,255))
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
