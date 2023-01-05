package io.typecraft.bukkit.scala.plugin

import io.circe.generic.AutoDerivation
import io.typecraft.command.scala.vavr.{EitherInstances, TupleInstances}
import io.typecraft.ender.AllExtension
import io.typecraft.ender.bukkit.AllBukkitExtension

object implicits
    extends AutoDerivation
    with AllExtension
    with AllBukkitExtension
    with TupleInstances
    with EitherInstances
