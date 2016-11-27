package se.lovef.linedrawer

import com.sun.javafx.geom.Vec2d
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import se.lovef.math.length

/**
 * Date: 2016-02-20
 * @author Love
 */
class Main : Application() {

    override fun start(primaryStage: Stage) {
        primaryStage.apply {
            this.title = "LineDrawer"

            this.icons.add(Image("icons/RainbowCenter.circle.128.png"))

            val canvas = Canvas(1920.0, 1080.0)
            val graphicsContext = canvas.graphicsContext2D

            val polygonLevelIterator = Polygon(
                    center = Vec2d(canvas.width / 2, canvas.height / 2),
                    radius = Vec2d(canvas.width, canvas.height).length / 2,
                    pointsCount = 87).getLevelIterator()

            /* Rainbow */
            arrayListOf(
                    "#FF0000",
                    "#FF8000",
                    "#FFFF00",
                    "#008000",
                    "#0000FF",
                    "#A000C0")
                    .reversed()
                    .forEach { color ->
                graphicsContext.draw(Color.web(color), polygonLevelIterator.next())
            }

            this.scene = Scene(Pane().apply {
                background = Background(BackgroundFill(Color.BLACK, null, null))
                children.add(canvas)
            })
        }.show()
    }

    private fun GraphicsContext.draw(color: Color, polygonLevel: Polygon.Level) {
        stroke = color
        lineWidth = 1.0
        polygonLevel.forEach {
            strokeLine(it.start.x, it.start.y, it.end.x, it.end.y)
        }
    }

    companion object {
        @JvmStatic fun main(vararg args: String) {
            Application.launch(Main::class.java, *args)
        }
    }
}
