package com.note_awesome.views.core_editors;

import com.note_awesome.NoteAwesomeEnv;
import com.note_awesome.NoteAwesomeFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.TwoDimensional;
import org.reactfx.SuspendableNo;
import org.reactfx.util.Either;

import java.io.IOException;
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

    @FXML
    private TextArea noteTitleTxtArea;

    @FXML
    private VBox noteVBox;

    @FXML
    private Button pinNoteBtn;

    private final SuspendableNo updatingToolbar = new SuspendableNo();

    public NoteEditorFxController() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(NoteAwesomeEnv.VIEW_COMPONENT_LOAD_PATHS.get(NoteAwesomeEnv.ViewComponent.NOTE_EDITOR));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        // Add the area to the root VBox
        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        VBox.setVgrow(vsPane, javafx.scene.layout.Priority.ALWAYS);
        VBox.setMargin(vsPane, new Insets(0, 9, 0, 9));
        this.noteVBox.getChildren().add(1, vsPane);
        // assign event handlers
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

        area.setWrapText(true);
        this.noteTitleTxtArea.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 16));

        // Add listeners to area
        area.beingUpdatedProperty().addListener((obs, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold, italic, underline;

                IndexRange selection = area.getSelection();

                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().allMatch(style -> style.bold.orElse(Boolean.valueOf(false)));
                    italic = styles.styleStream().allMatch(style -> style.italic.orElse(Boolean.valueOf(false)));
                    underline = styles.styleStream().allMatch(style -> style.underline.orElse(Boolean.valueOf(false)));
                } else {
                    int paragraph = area.getCurrentParagraph();
                    int column = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(paragraph, column);

                    bold = style.bold.orElse(Boolean.valueOf(false));
                    italic = style.italic.orElse(Boolean.valueOf(false));
                    underline = style.underline.orElse(Boolean.valueOf(false));
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
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(Boolean.valueOf(false)))));
    }

    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(Boolean.valueOf(false)))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(Boolean.valueOf(false)))));
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        this.area.requestFocus();
    }
}
