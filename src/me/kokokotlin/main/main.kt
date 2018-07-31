package me.kokokotlin.main

import me.kokokotlin.main.gui.DrawArea
import me.kokokotlin.main.ls.Lindenmayer
import me.kokokotlin.main.ls.Rule
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Point

/**
 *  @author: kokokotlin
 *  https://www.youtube.com/channel/UC7nacURgPqTgcR074eABZJg
 *
 *  This example is open source and everybody can modify it
 *  or use it in any way he wants to.
 *
 *  A little example of the dragon curve generated using a lindenmayer
 *  system or l-system.
 *
 *  Useful information:
 *      - https://en.wikipedia.org/wiki/Dragon_curve
 *      - https://en.wikipedia.org/wiki/L-system
 *
 */


// number of iterations the l-system will make
const val ITERATIONS = 10
// length of the single sections of the dragon curve
const val LENGTH = 7
// step in which the angle increases or decreases
const val ANGLE_STEP = 90

fun main(args : Array<String>) {

    // create the rules for the lindenmayer system
    val rules = arrayOf(Rule({ c -> c == 'x'}, "x+yf+"),
                        Rule({ c -> c == 'y'}, "-fx-y"))

    // create the l-system
    val dragonCurve = Lindenmayer("fx", rules)
    val drawArea = DrawArea()

    val g = drawArea.drawGraphics
    val g2d = g as Graphics2D

    for( i in 0..ITERATIONS) {
        dragonCurve.makeIteration()
    }

    while (true) {

        // current angle for drawing
        var angle = 0
        // current position to start the section of the curve
        // starts in the middle of the screen
        var currPos = Point(drawArea.width / 2, drawArea.height / 2)

        g.color = Color.BLACK
        g2d.stroke = BasicStroke(2f)

        // iterate over all chars in the systems state
        for(c in dragonCurve.state) {
            when(c) {
                // turn angle clockwise
                '+' -> angle = (angle + ANGLE_STEP) % 360
                // turn angle counterclockwise
                '-' -> angle = if(angle - ANGLE_STEP >= 0) angle - ANGLE_STEP else 360 + angle - ANGLE_STEP
                // draw a line according to the current length and angle
                'f' -> {
                    val destX = when(angle) {
                        90 -> currPos.x + LENGTH
                        270 -> currPos.x - LENGTH
                        else -> currPos.x
                    }
                    val destY = when(angle) {
                        0 -> currPos.y - LENGTH
                        180 -> currPos.y + LENGTH
                        else -> currPos.y
                    }
                    g.drawLine(currPos.x, currPos.y, destX, destY)
                    currPos = Point(destX, destY)
                }
            }
        }

        drawArea.bufferStrategy.show()
        Thread.sleep(250)
    }

}