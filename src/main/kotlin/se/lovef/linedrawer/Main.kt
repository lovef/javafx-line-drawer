package se.lovef.linedrawer

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
            canvas.graphicsContext2D.drawStuffs()
            this.scene = Scene(Pane().apply {
                background = Background(BackgroundFill(Color.BLACK, null, null))
                children.add(canvas)
            })
        }.show()
    }

    private fun GraphicsContext.drawStuffs() {
        fill = Color.BLACK
        stroke = Color.BLUE
        lineWidth = 1.0
        strokeLine(40.0, 10.0, 10.0, 40.0)
        strokeLine(10.0, 40.0, 80.0, 40.0)

        strokeLine(-10.0, -10.0, 2560.0, 1440.0)
    }
}
