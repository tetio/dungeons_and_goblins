package com.buzzfactory.dag.essentialscala

sealed trait Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B
}

case class Leaf[A](value: A) extends Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B = leaf(value)
}

case class Node[A](left: Tree[A], right: Tree[A]) extends Tree[A] {
  def fold[B](node: (B, B) => B, leaf: A => B): B = node(left.fold(node, leaf), right.fold(node, leaf))
}

object TestTree {
  def test1(): String = {
    val tree: Tree[String] =
      Node(Node(Leaf("To"), Leaf("iterate")),
        Node(Node(Leaf("is"), Leaf("human,")),
          Node(Leaf("to"), Node(Leaf("recurse"), Leaf("divine")))))

    tree.fold[String]((a, b) => a + " " + b, a => a)

  }

  def test2(): Int = {
    val treeInt: Tree[Int] =
      Node(Node(Leaf(1), Leaf(2)),
        Node(Node(Leaf(3), Leaf(4)),
          Node(Leaf(5), Node(Leaf(6), Leaf(7)))))

    treeInt.fold[Int]((a, b) => a + b, a => a)
  }
}