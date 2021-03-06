package it.polimi.carcassonne.test;

import static it.polimi.carcassonne.server.model.Settings.MAX_MARKERS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.polimi.carcassonne.server.model.Coordinate;
import it.polimi.carcassonne.server.model.Grid;
import it.polimi.carcassonne.server.model.Marker;
import it.polimi.carcassonne.server.model.Player;
import it.polimi.carcassonne.server.model.Tile;
import it.polimi.carcassonne.server.model.enums.CardinalPoint;
import it.polimi.carcassonne.server.model.enums.PlayersColor;

import org.junit.Before;
import org.junit.Test;

public class GridCheckIncompleteBuildings {

	private Grid grid;

	private Player redPlayer;
	private Player bluePlayer;
	private Player greenPlayer;
	private Player yellowPlayer;
	private Player blackPlayer;

	private Tile elbowStreetNEconn;
	private Tile elbowStreetNWconn;
	private Tile elbowStreetSWconn;
	private Tile elbowStreetNEnotConn;
	private Tile straightStreetWEconn;
	private Tile straightStreetNSconn;
	private Tile straightStreetWEnotConn;
	private Tile elbowCityNEconn;
	private Tile elbowCityNWconn;
	private Tile elbowCitySWconn;
	private Tile elbowCitySEconn;
	private Tile elbowCityNEnotConn;
	private Tile straightCityWEconn;
	private Tile straightCityNSconn;
	private Tile straightCityWEnotConn;
	private Tile threeWayCityNWE;
	private Tile threeWayCitySWE;
	private Tile threeWayCityNSW;
	private Tile singleCityN;
	private Tile singleCityE;
	private Tile singleCityS;

	@Before
	public void setUp() {

		redPlayer = new Player(PlayersColor.RED);
		bluePlayer = new Player(PlayersColor.BLUE);
		greenPlayer = new Player(PlayersColor.GREEN);
		yellowPlayer = new Player(PlayersColor.YELLOW);
		blackPlayer = new Player(PlayersColor.BLACK);

		elbowStreetNEconn = new Tile(
				"N=S E=S S=N W=N NE=1 NW=0 NS=0 SE=0 SW=0 EW=0");
		elbowStreetNWconn = new Tile(
				"N=S E=N S=N W=S NE=0 NW=1 NS=0 SE=0 SW=0 EW=0");
		elbowStreetSWconn = new Tile(
				"N=N E=N S=S W=S NE=0 NW=0 NS=0 SE=0 SW=1 EW=0");
		elbowStreetNEnotConn = new Tile(
				"N=S E=S S=N W=N NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		straightStreetWEconn = new Tile(
				"N=N E=S S=N W=S NE=0 NW=0 NS=0 SE=0 SW=0 EW=1");
		straightStreetNSconn = new Tile(
				"N=S E=N S=S W=N NE=0 NW=0 NS=1 SE=0 SW=0 EW=0");

		straightStreetWEnotConn = new Tile(
				"N=N E=S S=N W=S NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		elbowCityNEconn = new Tile(
				"N=C E=C S=N W=N NE=1 NW=0 NS=0 SE=0 SW=0 EW=0");
		elbowCityNWconn = new Tile(
				"N=C E=N S=N W=C NE=0 NW=1 NS=0 SE=0 SW=0 EW=0");
		elbowCitySWconn = new Tile(
				"N=N E=N S=C W=C NE=0 NW=0 NS=0 SE=0 SW=1 EW=0");
		elbowCitySEconn = new Tile(
				"N=N E=C S=C W=N NE=0 NW=0 NS=0 SE=1 SW=0 EW=0");

		elbowCityNEnotConn = new Tile(
				"N=C E=C S=N W=N NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		straightCityWEconn = new Tile(
				"N=N E=C S=N W=C NE=0 NW=0 NS=0 SE=0 SW=0 EW=1");
		straightCityNSconn = new Tile(
				"N=C E=N S=C W=N NE=0 NW=0 NS=1 SE=0 SW=0 EW=0");

		straightCityWEnotConn = new Tile(
				"N=N E=C S=N W=C NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		threeWayCityNWE = new Tile(
				"N=C E=C S=N W=C NE=1 NW=1 NS=0 SE=0 SW=0 EW=1");
		threeWayCitySWE = new Tile(
				"N=N E=C S=C W=C NE=0 NW=0 NS=0 SE=1 SW=1 EW=1");
		threeWayCityNSW = new Tile(
				"N=C E=N S=C W=C NE=0 NW=1 NS=1 SE=0 SW=1 EW=0");

		singleCityN = new Tile("N=C E=N S=N W=N NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		singleCityE = new Tile("N=N E=C S=N W=N NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");
		singleCityS = new Tile("N=N E=N S=C W=N NE=0 NW=0 NS=0 SE=0 SW=0 EW=0");

		grid = new Grid(singleCityS);
	}

	@Test
	public void checkCrossToCrossStreetIncomplete() {
		grid = new Grid(straightStreetWEnotConn);
		try {
			grid.placeTile(new Coordinate(1, 0), straightStreetWEconn);
			grid.placeTile(new Coordinate(2, 0), elbowStreetNWconn);
			grid.placeTile(new Coordinate(2, 1), straightStreetNSconn);

			Marker marker = redPlayer.getMarker();
			marker.setPosition(CardinalPoint.NORTH);
			grid.getCellByCoordinates(new Coordinate(2, 0)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(2, 1)));
		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(4, redPlayer.getScore());
		assertEquals(MAX_MARKERS, redPlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkCircleStreetIncomplete() {
		grid = new Grid(elbowStreetNEconn);
		try {
			grid.placeTile(new Coordinate(1, 0), elbowStreetNWconn);
			grid.placeTile(new Coordinate(1, 1), elbowStreetSWconn);

			Marker marker = bluePlayer.getMarker();
			marker.setPosition(CardinalPoint.NORTH);
			grid.getCellByCoordinates(new Coordinate(1, 0)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, bluePlayer.getScore());
		assertEquals(MAX_MARKERS - 1, bluePlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(1, 0)));
		assertEquals(0, bluePlayer.getScore());
		assertEquals(MAX_MARKERS - 1, bluePlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(3, bluePlayer.getScore());
		assertEquals(MAX_MARKERS, bluePlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkCircleCrossToSameCrossStreetIncomplete() {
		grid = new Grid(elbowStreetNEnotConn);
		try {
			grid.placeTile(new Coordinate(1, 0), elbowStreetNWconn);
			grid.placeTile(new Coordinate(1, 1), elbowStreetSWconn);

			Marker marker = redPlayer.getMarker();
			marker.setPosition(CardinalPoint.NORTH);
			grid.getCellByCoordinates(new Coordinate(0, 0)).getTile()
					.setMarker(marker);
			Marker marker2 = bluePlayer.getMarker();
			marker2.setPosition(CardinalPoint.WEST);
			grid.getCellByCoordinates(new Coordinate(1, 0)).getTile()
					.setMarker(marker2);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());
		assertEquals(0, bluePlayer.getScore());
		assertEquals(MAX_MARKERS - 1, bluePlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(0, 0)));
		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());
		assertEquals(0, bluePlayer.getScore());
		assertEquals(MAX_MARKERS - 1, bluePlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(1, redPlayer.getScore());
		assertEquals(MAX_MARKERS, redPlayer.getAvailableMarkerCount());
		assertEquals(3, bluePlayer.getScore());
		assertEquals(MAX_MARKERS, bluePlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkBoundToBoundCityIncomplete() {
		grid = new Grid(straightCityWEnotConn);
		try {
			grid.placeTile(new Coordinate(1, 0), straightCityWEconn);
			grid.placeTile(new Coordinate(2, 0), elbowCityNWconn);
			grid.placeTile(new Coordinate(2, 1), straightCityNSconn);

			Marker marker = yellowPlayer.getMarker();
			marker.setPosition(CardinalPoint.EAST);
			grid.getCellByCoordinates(new Coordinate(0, 0)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, yellowPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, yellowPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(2, 0)));
		assertEquals(0, yellowPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, yellowPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(4, yellowPlayer.getScore());
		assertEquals(MAX_MARKERS, yellowPlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkCircleCityIncomplete() {
		grid = new Grid(elbowCityNEconn);
		try {
			grid.placeTile(new Coordinate(1, 0), elbowCityNWconn);
			grid.placeTile(new Coordinate(1, 1), elbowCitySWconn);

			Marker marker = blackPlayer.getMarker();
			marker.setPosition(CardinalPoint.EAST);
			grid.getCellByCoordinates(new Coordinate(0, 0)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, blackPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, blackPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(1, 1)));
		assertEquals(0, blackPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, blackPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(3, blackPlayer.getScore());
		assertEquals(MAX_MARKERS, blackPlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkCircleBoundToSameBoundCityIncomplete() {
		grid = new Grid(elbowCityNEnotConn);
		try {
			grid.placeTile(new Coordinate(1, 0), elbowCityNWconn);
			grid.placeTile(new Coordinate(1, 1), elbowCitySWconn);

			Marker marker = redPlayer.getMarker();
			marker.setPosition(CardinalPoint.EAST);
			grid.getCellByCoordinates(new Coordinate(0, 0)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(1, 1)));
		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(3, redPlayer.getScore());
		assertEquals(MAX_MARKERS, redPlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkDonutCityIncomplete() {
		grid = new Grid(straightCityWEconn);
		try {
			grid.placeTile(new Coordinate(1, 0), elbowCityNWconn);
			grid.placeTile(new Coordinate(1, 1), straightCityNSconn);
			grid.placeTile(new Coordinate(1, 2), elbowCitySWconn);
			grid.placeTile(new Coordinate(0, 2), straightCityWEconn);
			grid.placeTile(new Coordinate(-1, 2), elbowCitySEconn);
			grid.placeTile(new Coordinate(-1, 1), straightCityNSconn);

			Marker marker = greenPlayer.getMarker();
			marker.setPosition(CardinalPoint.NORTH);
			grid.getCellByCoordinates(new Coordinate(-1, 1)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, greenPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, greenPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(0, 2)));
		assertEquals(0, greenPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, greenPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(7, greenPlayer.getScore());
		assertEquals(MAX_MARKERS, greenPlayer.getAvailableMarkerCount());
	}

	@Test
	public void checkExtremeCityIncomplete() {
		grid = new Grid(threeWayCityNWE);
		try {
			grid.placeTile(new Coordinate(1, 0), threeWayCityNWE);
			grid.placeTile(new Coordinate(2, 0), threeWayCityNSW);
			grid.placeTile(new Coordinate(2, -1), singleCityN);
			grid.placeTile(new Coordinate(2, 1), straightCityNSconn);
			grid.placeTile(new Coordinate(2, 2), singleCityS);
			grid.placeTile(new Coordinate(1, 1), threeWayCityNSW);
			grid.placeTile(new Coordinate(1, 2), singleCityS);
			grid.placeTile(new Coordinate(0, 1), threeWayCitySWE);
			grid.placeTile(new Coordinate(-1, 1), singleCityE);

			Marker marker = redPlayer.getMarker();
			marker.setPosition(CardinalPoint.SOUTH);
			grid.getCellByCoordinates(new Coordinate(2, 2)).getTile()
					.setMarker(marker);

		} catch (Exception e) {
			fail("Errore nel posizionamento delle tessere");
		}

		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());
		grid.checkClosedBuildings(grid
				.getCellByCoordinates(new Coordinate(1, 1)));
		assertEquals(0, redPlayer.getScore());
		assertEquals(MAX_MARKERS - 1, redPlayer.getAvailableMarkerCount());

		grid.checkIncompleteBuildings();
		assertEquals(10, redPlayer.getScore());
		assertEquals(MAX_MARKERS, redPlayer.getAvailableMarkerCount());
	}
}
