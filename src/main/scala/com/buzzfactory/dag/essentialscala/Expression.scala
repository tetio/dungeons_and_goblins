package com.buzzfactory.dag.essentialscala


sealed trait Calculation
case class Success(result: Double) extends Calculation
case class Failure(reason: String) extends Calculation

sealed trait Expression {
//  def eval: Calculation = this match {
//    case addition(left, right) => left.eval match {
//      case Success(l) => right.eval match {
//        case Success(r) => Success(r + l)
//        case Failure(r) => Failure(r)
//      }
//      case Failure(r) => Failure(r)
//    }
//    case subtraction(left, right) => left.eval - right.eval
//    case division(left, right) => left.eval / right.eval
//    case squareRoot(expression) => Math.sqrt(expression.eval)
//    case Number(value) => value
//  }
}
case class addition(left: Expression, right: Expression) extends Expression
case class subtraction(left: Expression, right: Expression) extends Expression
case class Number(value: Double) extends Expression
case class division(left: Expression, right: Expression) extends Expression
case class squareRoot(value: Expression) extends Expression
