// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.lambdaworks.jacks

sealed abstract class JacksOption
object JacksOption {
  case class CaseClassCheckNulls(enabled:Boolean) extends JacksOption
  case class CaseClassSkipNulls(enabled:Boolean) extends JacksOption
  case class CaseClassRequireKnown(enabled:Boolean) extends JacksOption
}


private[jacks] class JacksOptions(opts:Seq[JacksOption]=Seq.empty) {
  import JacksOption._
  def caseClassCheckNulls  =opts contains CaseClassCheckNulls(true)
  def caseClassSkipNulls   =opts contains CaseClassSkipNulls(true)
  def caseClassRequireKnown=opts contains CaseClassRequireKnown(true)
}
private[jacks] object JacksOptions {
  def apply(opts:JacksOption*) =
    new JacksOptions(opts.groupBy(_.getClass()).toSeq.map(_._2.last))
  def defaults=new JacksOptions()
}
