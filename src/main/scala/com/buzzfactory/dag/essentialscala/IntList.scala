package com.buzzfactory.dag.essentialscala

sealed trait IntList {
  def fold(end: Int, f: (Int, Int) => Int): Int = this match {
    case End() => end
    case Pair(hd, tl) => f(hd, tl.fold(end, f))
  }
  def sum: Int = fold(0, (hd, tail) => hd + tail)
  def prod: Int = fold(1, (hd, tail) => hd * tail)
}

case class End() extends IntList

case class Pair(hd: Int, tl: IntList) extends IntList
