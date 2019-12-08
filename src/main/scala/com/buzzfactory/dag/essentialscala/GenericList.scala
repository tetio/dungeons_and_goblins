package com.buzzfactory.dag.essentialscala

sealed trait LinkedList[A] {
  def length: Int = this match {
    case End() => 0
    case Pair(hd, tl) => 1 + tl.length
  }

  def contains(x: A): Boolean = this match {
    case End() => false
    case Pair(hd, tl) =>
      if (hd == x)
        true
      else
        tl.contains(x)
  }
}


final case class End[A]() extends LinkedList[A]

final case class Pair[A](head: A, tail: LinkedList[A]) extends LinkedList[A]

