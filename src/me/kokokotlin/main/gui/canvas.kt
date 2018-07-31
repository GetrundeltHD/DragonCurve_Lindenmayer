package me.kokokotlin.main.gui

import java.awt.Canvas
import java.awt.Dimension
import javax.swing.JFrame

class DrawArea : Canvas {

    /**
     *  simple canvas class which contains a jframe
     *  and allows the user to draw on it
     */

    private val window = JFrame("dragon curve")

    val drawGraphics
        get() = bufferStrategy.drawGraphics

    constructor() {
        window.isVisible = false
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        window.size = Dimension(1000, 1000)
        window.isResizable = true
        window.setLocationRelativeTo(null)

        window.add(this)

        window.isVisible = true

        createBufferStrategy(1)
    }

}
