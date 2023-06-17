package io.typecraft.bukkit.scala.plugin

import io.circe.generic.AutoDerivation
import io.typecraft.command.scala.ScalaCommand
import io.typecraft.ender.AllExtension
import io.typecraft.ender.bukkit.AllBukkitExtension

object implicits
    extends AutoDerivation
    with AllExtension
    with AllBukkitExtension
    with ScalaCommand