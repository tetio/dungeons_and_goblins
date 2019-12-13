package com.buzzfactory.dag.essentialscala

import org.scalatest.FunSpec

class TreeTest extends FunSpec {
  describe("Testing Trees") {

    it("Create a Tree[String]") {
      val result = TestTree.test1()
      assert(result === "To iterate is human, to recurse divine")
    }

    it("Create a Tree[Int]") {
      val result = TestTree.test2()
      assert(result === 28)
    }
  }
}
