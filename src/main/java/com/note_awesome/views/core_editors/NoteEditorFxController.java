package com.note_awesome.views.core_editors;

import com.note_awesome.NoteAwesomeEnv;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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
        VBox.setVgrow(vsPane, javafx.scene.layout.Priority.ALWAYS);
        VBox.setMargin(vsPane, new Insets(0, 9, 0, 9));
        this.noteVBox.getChildren().add(1, vsPane);

        area.setWrapText(true);
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
