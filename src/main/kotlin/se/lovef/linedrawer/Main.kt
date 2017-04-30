package se.lovef.linedrawer

import javafx.application.Application
import javafx.application.Platform
import javafx.beans.InvalidationListener
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import se.lovef.util.Vector2d

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

            draw(canvas)

            this.scene = Scene(Pane(canvas).apply {
                background = Background(BackgroundFill(Color.BLACK, null, null))
            }).apply {
                widthProperty().addListener(InvalidationListener {
                    canvas.width = (it as ReadOnlyDoubleProperty).value
                })
                heightProperty().addListener(InvalidationListener {
                    canvas.height = (it as ReadOnlyDoubleProperty).value
                })
                setOnKeyReleased {
                    when (it.code) {
                        KeyCode.ESCAPE -> Platform.exit()
                        KeyCode.R -> draw(canvas)
                        KeyCode.F11 -> {
                            primaryStage.isFullScreen = !primaryStage.isFullScreen
                            draw(canvas)
                        }
                        else -> { }
                    }
                }
            }
        }.show()
    }

    private fun draw(canvas: Canvas) {
        val graphicsContext = canvas.graphicsContext2D
        graphicsContext.clearRect(0.0, 0.0, canvas.width, canvas.height)
        val polygonLevelIterator = Polygon(
                center = Vector2d(canvas.width / 2, canvas.height / 2),
                radius = Vector2d(canvas.width, canvas.height).length / 2,
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
