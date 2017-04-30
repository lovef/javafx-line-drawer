package se.lovef.linedrawer

import se.lovef.util.Vector2d
import spock.lang.Specification

/**
 * Date: 2016-11-27
 * @author Love
 */
class PolygonTest extends Specification {

    def "Levels"() {
        given:
        def polygon = new Polygon(Vector2d.ZERO, 1, points, 0)

        expect:
        polygon.levelCount == levels
        polygon.getLevelIterator(0, polygon.levelCount -1).size() == levels

        where:
        points | levels
        3      | 1
        4      | 2
        8      | 4
    }

    def "Iterate level indexes"() {
        given:
        def polygon = new Polygon(Vector2d.ZERO, 1, points, 0)

        expect:
        polygon.getLevel(level).collect { [it.startIndex, it.endIndex] } == levelIndexes
        polygon.getLevelIterator(0, polygon.levelCount - 1).collect {it}[level]
                .collect { [it.startIndex, it.endIndex] } == levelIndexes

        where:
        points | level | levelIndexes
        3      | 0     | [[0, 1], [1, 2], [2, 0]]
        // Multilevel, inner level has index 0
        4      | 0     | [[0, 2], [1, 3], [2, 0], [3, 1]]
        4      | 1     | [[0, 1], [1, 2], [2, 3], [3, 0]]
    }

    def "Points"() {
        expect:
        new Polygon(vect(center), 1, pointsCount, 0).points.collect { round(it) } == points.collect { vect(it) }

        where:
        pointsCount | center | points
        4           | [0, 0] | [[1, 0], [0, 1], [-1, 0], [0, -1]]
        4           | [1, 0] | [[2, 0], [1, 1], [0, 0], [1, -1]]
        4           | [0, 1] | [[1, 1], [0, 2], [-1, 1], [0, 0]]
    }

    Vector2d vect(List<Double> list) {
        return new Vector2d(list.first(), list.last())
    }

    def round(Vector2d v) {
        new Vector2d(
                Math.round(v.x * 1e10) * 1e-10,
                Math.round(v.y * 1e10) * 1e-10)
    }
}
