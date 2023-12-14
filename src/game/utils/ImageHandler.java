package game.utils;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageHandler{ //written by ofek


	public static PImage backCard;

	public static PImage background;

	public static PImage foldButton;

	public static PImage nextButton;

	public static PImage flipButton;

	public static PImage tenButton;

	public static PImage hundredButton;

	public static PImage thousandButton;
	
	public static PImage slot;

	static ArrayList<PImage> cardList= new ArrayList<>();

	public static PImage getCardImage(int cardVal, int suit) {
		switch (cardVal) {
		case 2: return cardList.get(suit+4);
		case 3: return cardList.get(suit+8);
		case 4: return cardList.get(suit+12);
		case 5: return cardList.get(suit+16);
		case 6: return cardList.get(suit+20);
		case 7: return cardList.get(suit+24);
		case 8: return cardList.get(suit+28);
		case 9: return cardList.get(suit+32);
		case 10: return cardList.get(suit);
		case 11: return cardList.get(suit+40);
		case 12: return cardList.get(suit+48);
		case 13: return cardList.get(suit+44);
		case 14: return cardList.get(suit+36);
		default: return null;
		}


	}

	public static void init(String folder, PApplet pap) {
		backCard= pap.loadImage("Images/others/cardback.png");
		backCard.resize(100, 150);
		background= pap.loadImage("Images/others/matNew.jpg");
		background.resize(800, 650);

		foldButton= pap.loadImage("Images/buttons/FoldButton.png");
		foldButton.resize(100, 30);

		nextButton= pap.loadImage("Images/buttons/NextPlayerButton.png");
		nextButton.resize(100, 30);

		flipButton= pap.loadImage("Images/buttons/FlipCardsButton.png");
		flipButton.resize(100, 30);

		
		tenButton= pap.loadImage("Images/buttons/button_10.png");
		tenButton.resize(50, 30);

		hundredButton= pap.loadImage("Images/buttons/button_100.png");
		hundredButton.resize(50, 30);

		thousandButton= pap.loadImage("Images/buttons/button_1000.png");
		thousandButton.resize(50, 30);
		
		slot= pap.loadImage("Images/buttons/slot.png");
		
		ArrayList<Path> pathList=getPaths(folder);
		for(Path p: pathList) {
			PImage temp= pap.loadImage(p.toString());
			temp.resize(100, 150);
			cardList.add(temp);
		}
	}



	private static ArrayList<Path> getPaths(String folderPath) {
		ArrayList<Path> paths = new ArrayList<Path>();
		Path workDir = Paths.get(folderPath);
		if (!Files.notExists(workDir)) {
			try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(workDir)) {
				for (Path p : directoryStream) {
					paths.add(p);
				}
				return paths;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}



}


