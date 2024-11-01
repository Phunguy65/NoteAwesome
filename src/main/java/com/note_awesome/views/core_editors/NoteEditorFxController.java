package com.note_awesome.views.core_editors;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.models.NoteBackgroundColor;
import com.note_awesome.models.NoteBackgroundImage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.lang3.ArrayUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.*;
import org.reactfx.SuspendableNo;
import org.reactfx.util.Either;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class NoteEditorFxController extends VBox {

    private final FoldableStyleArea area = new FoldableStyleArea();


    @FXML
    private Button boldBtn;

    @FXML
    private Button italicBtn;

    @FXML
    private Button underlineBtn;

    public TextArea getNoteTitleTxtArea() {
        return noteTitleTxtArea;
    }

    @FXML
    private TextArea noteTitleTxtArea;

    @FXML
    private VBox noteVBox;

    public Button getPinNoteBtn() {
        return pinNoteBtn;
    }

    @FXML
    private Button pinNoteBtn;

    public Button getCloseEditorBtn() {
        return closeEditorBtn;
    }

    @FXML
    private Button closeEditorBtn;

    @FXML
    private Button coralRedBtn;

    @FXML
    private Button peachOrangeBtn;

    @FXML
    private Button sandBrownBtn;

    @FXML
    private Button mintGreenBtn;

    @FXML
    private Button grayGreenBtn;

    @FXML
    private Button smokeGrayBtn;

    @FXML
    private Button darkBlueBtn;

    public Button getCelebrationImgBtn() {
        return celebrationImgBtn;
    }

    public Button getVideoImgBtn() {
        return videoImgBtn;
    }

    public Button getTravelImgBtn() {
        return travelImgBtn;
    }

    public Button getPlacesImgBtn() {
        return placesImgBtn;
    }

    public Button getNotesImgBtn() {
        return notesImgBtn;
    }

    public Button getRecipeImgBtn() {
        return recipeImgBtn;
    }

    public Button getMusicImgBtn() {
        return musicImgBtn;
    }

    public Button getGroceryImgBtn() {
        return groceryImgBtn;
    }

    public Button getFoodImgBtn() {
        return foodImgBtn;
    }

    @FXML
    private Button purpleSunsetBtn;

    @FXML
    private Button noneBgColorBtn;

    @FXML
    private Button groceryImgBtn;

    @FXML
    private Button foodImgBtn;

    @FXML
    private Button musicImgBtn;

    @FXML
    private Button recipeImgBtn;

    @FXML
    private Button notesImgBtn;

    @FXML
    private Button placesImgBtn;

    @FXML
    private Button travelImgBtn;

    @FXML
    private Button videoImgBtn;

    @FXML
    private Button celebrationImgBtn;

    public Button getCoralRedBtn() {
        return coralRedBtn;
    }

    public Button getPeachOrangeBtn() {
        return peachOrangeBtn;
    }

    public Button getSandBrownBtn() {
        return sandBrownBtn;
    }

    public Button getChelGrayBtn() {
        return chelGrayBtn;
    }

    public Button getClayBrownBtn() {
        return clayBrownBtn;
    }

    public Button getPeachBtn() {
        return peachBtn;
    }

    public Button getDarkBlueBtn() {
        return darkBlueBtn;
    }

    public Button getSmokeGrayBtn() {
        return smokeGrayBtn;
    }

    public Button getGrayGreenBtn() {
        return grayGreenBtn;
    }

    public Button getMintGreenBtn() {
        return mintGreenBtn;
    }

    public Button getPurpleSunsetBtn() {
        return purpleSunsetBtn;
    }

    @FXML
    private Button peachBtn;

    @FXML
    private Button clayBrownBtn;

    @FXML
    private Button chelGrayBtn;

    private final SuspendableNo updatingToolbar = new SuspendableNo();

    public NoteEditorFxController() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(NoteAwesomeEnv.ViewComponent.NOTE_EDITOR.getURL());
            loader.setRoot(this);
            loader.setController(this);
            loader.setClassLoader(getClass().getClassLoader());
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {

        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        vsPane.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(vsPane, javafx.scene.layout.Priority.ALWAYS);
        this.noteVBox.getChildren().add(1, vsPane);
        area.setWrapText(true);
        area.setStyle("-fx-background-color: transparent;");
        area.setStyleCodecs(ParStyle.CODEC, Codec.styledSegmentCodec(Codec.eitherCodec(Codec.STRING_CODEC, LinkedImage.codec()), TextStyle.CODEC));
        this.noteTitleTxtArea.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 16));
        area.clear();

        boldBtn.setOnAction(e -> {
            toggleBold();
            area.requestFocus();
        });

        italicBtn.setOnAction(e -> {
            toggleItalic();
            area.requestFocus();
        });

        underlineBtn.setOnAction(e -> {
            toggleUnderline();
            area.requestFocus();
        });

        area.beingUpdatedProperty().addListener((obs, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold, italic, underline;

                IndexRange selection = area.getSelection();

                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().allMatch(style -> style.bold.orElse(Boolean.FALSE));
                    italic = styles.styleStream().allMatch(style -> style.italic.orElse(Boolean.FALSE));
                    underline = styles.styleStream().allMatch(style -> style.underline.orElse(Boolean.FALSE));
                } else {
                    int paragraph = area.getCurrentParagraph();
                    int column = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(paragraph, column);

                    bold = style.bold.orElse(Boolean.FALSE);
                    italic = style.italic.orElse(Boolean.FALSE);
                    underline = style.underline.orElse(Boolean.FALSE);
                }

                updatingToolbar.suspendWhile(() -> {
                    if (bold) {
                        if (!boldBtn.getStyleClass().contains("pressed")) {
                            boldBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        boldBtn.getStyleClass().remove("pressed");
                    }

                    if (italic) {
                        if (!italicBtn.getStyleClass().contains("pressed")) {
                            italicBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        italicBtn.getStyleClass().remove("pressed");
                    }

                    if (underline) {
                        if (!underlineBtn.getStyleClass().contains("pressed")) {
                            underlineBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineBtn.getStyleClass().remove("pressed");
                    }
                });

            }
        });

        this.coralRedBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.CORAL_RED);
        });

        this.peachOrangeBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.PEACH_ORANGE);
        });

        this.sandBrownBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.SAND_BROWN);
        });

        this.mintGreenBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.MINT_GREEN);
        });

        this.grayGreenBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.GRAY_GREEN);
        });

        this.smokeGrayBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.SMOKE_GRAY);
        });

        this.darkBlueBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.DARK_BLUE);
        });

        this.purpleSunsetBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.PURPLE_SUNSET);
        });

        this.peachBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.PEACH);
        });

        this.clayBrownBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.CLAY_BROWN);
        });

        this.chelGrayBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.CHEL_GRAY);
        });

        this.noneBgColorBtn.setOnAction(event -> {
            changeBackgroundColor(NoteBackgroundColor.NONE);
        });

        this.groceryImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.GROCERY_LIGHT_IMAGE);
        });

        this.foodImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.FOOD_LIGHT_IMAGE);
        });

        this.musicImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.MUSIC_LIGHT_IMAGE);
        });

        this.recipeImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.RECIPE_LIGHT_IMAGE);
        });

        this.notesImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.NOTES_LIGHT_IMAGE);
        });

        this.placesImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.PLACES_LIGHT_IMAGE);
        });

        this.travelImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.TRAVEL_LIGHT_IMAGE);
        });

        this.videoImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.VIDEO_LIGHT_IMAGE);
        });

        this.celebrationImgBtn.setOnAction(event -> {
            changeBackgroundImage(NoteBackgroundImage.CELEBRATION_LIGHT_IMAGE);
        });
    }


    private void changeBackgroundColor(NoteBackgroundColor color) {
        this.setStyle("-fx-background-color: " + color.getHexCode() + ";"
                + "-fx-border-color: #000000;"
                + "-fx-border-width: 0px;"
                + "-fx-border-radius: 50;");
    }

    private void changeBackgroundImage(NoteBackgroundImage img) {
        Image image = new Image(img.getImageUrl().toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        BackgroundPosition backgroundPosition = new BackgroundPosition(Side.RIGHT, .005, true, Side.BOTTOM, .005, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, backgroundPosition, backgroundSize);
        Background background = new Background(backgroundImage);
        this.noteVBox.setBackground(background);
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }


    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(Boolean.FALSE))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(Boolean.FALSE))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(Boolean.FALSE))));
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        this.area.requestFocus();
    }

    public GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> getArea() {
        return area;
    }

    // update area content from input stream
    private void load(InputStream inputStream) {
        if (area.getStyleCodecs().isPresent()) {
            var codecs = area.getStyleCodecs().get();
            var codec = ReadOnlyStyledDocument.codec(codecs._1, codecs._2, this.area.getSegOps());

            try (var dis = new DataInputStream(inputStream)) {
                var doc = codec.decode(dis);

                if (doc != null) {
                    area.replace(doc);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // extract area content to output stream
    private void save(OutputStream outputStream) {
        var doc = area.getDocument();

        area.getStyleCodecs().ifPresent(codecs -> {
            var codec = ReadOnlyStyledDocument.codec(codecs._1, codecs._2, this.area.getSegOps());
            try (var dataOs = new DataOutputStream(outputStream)) {
                codec.encode(dataOs, doc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void save(List<Byte> bytes) {
        try (ByteArrayOutputStream byteArrOutStream = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(byteArrOutStream);) {
            this.save(dos);
            var getBytes = byteArrOutStream.toByteArray();
            byteArrOutStream.close();
            for (byte getByte : getBytes) {
                bytes.add(getByte);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Byte> getByteContent() {
        List<Byte> bytes = new java.util.ArrayList<>();
        this.save(bytes);
        return bytes;
    }

    public void load(List<Byte> bytes) {
        try (ByteArrayInputStream byteArrInpStream = new ByteArrayInputStream(ArrayUtils.toPrimitive(bytes.toArray(new Byte[0])));
             DataInputStream dis = new DataInputStream(byteArrInpStream)) {
            this.load(dis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
