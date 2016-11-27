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
            canvas.graphicsContext2D.draw(Polygon(
                    center = Vec2d(canvas.width / 2, canvas.height / 2),
                    radius = Math.min(canvas.width, canvas.height) / 2,
                    pointsCount = 42))
            this.scene = Scene(Pane().apply {
                background = Background(BackgroundFill(Color.BLACK, null, null))
                children.add(canvas)
            })
        }.show()
    }

    private fun GraphicsContext.draw(polygon: Polygon) {
        stroke = Color.GREEN
        lineWidth = 1.0
        polygon.getLevelIterator().forEach { level ->
            level.forEach {
                strokeLine(it.start.x, it.start.y, it.end.x, it.end.y)
            }
        }
    }
}
