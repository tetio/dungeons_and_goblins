package com.buzzfactory.dag.essentialscala

import org.scalatest.FunSpec

class PairATest extends FunSpec {
  describe("Testing Pairs") {

    it("Create a Pair[Int, String]") {
      val pair = PairA(12, "Hi")
      assert(pair.one === 12)
      assert(pair.two === "Hi")
    }
  }
}
