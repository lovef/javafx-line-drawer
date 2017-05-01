package se.lovef.linedrawer

import javafx.application.Application
import javafx.application.Platform
import javafx.beans.InvalidationListener
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import se.lovef.log.log
import se.lovef.util.Vector2d
import se.lovef.util.squared
import tornadofx.hbox
import tornadofx.label

/**
 * Date: 2016-02-20
 * @author Love
 */
class Main : Application() {

    override fun start(stage: Stage) {
        log("Params: " + parameters.raw)
        val startParam = parameters.raw?.firstOrNull() ?: ""
        when {
            startParam.startsWith("/c", ignoreCase = true) -> configStart(stage)
            startParam.startsWith("/s", ignoreCase = true) -> screenSaverStart(stage)
            else -> normalStart(stage)
        }
    }

    private fun configStart(stage: Stage) {
        stage.apply {
            scene = Scene(hbox { label("TODO: Config") }).apply {
                setOnKeyReleased { if (it.code == KeyCode.ESCAPE) Platform.exit() }
            }
            show()
        }
    }

    private fun normalStart(stage: Stage) {
        stage.apply {
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
                            stage.isFullScreen = !stage.isFullScreen
                            draw(canvas)
                        }
                        else -> { }
                    }
                }
            }
            show()
        }
    }

    private fun screenSaverStart(stage: Stage) {
        stage.apply {
            initStyle(StageStyle.UNDECORATED)
            isResizable = false
            isIconified = false

            val screenBounds = Screen.getScreens().map { it.bounds }
                    .sortedWith(compareBy({ it.minX }, { it.minY }))
            x = screenBounds.map { it.minX }.reduce { a, b -> minOf(a, b) }
            y = screenBounds.map { it.minY }.reduce { a, b -> minOf(a, b)}
            width = screenBounds.map { it.maxX }.reduce { a, b -> maxOf(a, b) } - x
            height = screenBounds.map { it.maxY }.reduce { a, b -> maxOf(a, b) } - y

            val pane = Pane()
            scene = Scene(pane).apply {
                fill = Color.BLACK
                cursor = Cursor.NONE
                setOnKeyReleased {
                    when (it.code) {
                        KeyCode.ESCAPE -> Platform.exit()
                        else -> { }
                    }
                }
                var moveStart = 0L
                var origin = Vector2d.ZERO
                setOnMouseMoved {
                    val time = System.currentTimeMillis() - moveStart
                    val position = Vector2d(it.x, it.y)
                    if (time > 300) {
                        moveStart = System.currentTimeMillis()
                        origin = position
                    } else if ((position - origin).lengthSquared > 300.squared) {
                        Platform.exit()
                    }
                }
                setOnMouseClicked { Platform.exit() }
            }
            screenBounds.map { bound ->
                val canvas = Canvas().apply {
                    layoutX = bound.minX
                    layoutY = bound.minY
                    width = bound.width
                    height = bound.height
                }

                draw(canvas)
                pane.children.add(canvas)
            }
            show()
        }
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
