package com.note_awesome.views.core_editors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.TwoDimensional;
import org.reactfx.util.Either;

import java.util.List;
import java.util.function.Function;

public class NoteEditorFxController {
    
    private final FoldableStyleArea area = new FoldableStyleArea();
    
    @FXML
    private VBox rootVBox;
    
    @FXML
    private Button boldBtn;
    
    @FXML
    private Button italicBtn;
    
    @FXML
    private Button underlineBtn;
    
    public NoteEditorFxController() {
    }
    
    @FXML
    private void initialize() {
        // Add the area to the root VBox
        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(area);
        VBox.setVgrow(vsPane, javafx.scene.layout.Priority.ALWAYS);
        rootVBox.getChildren().add(1, vsPane);
        
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
        
        
        // Add listeners to area
        area.beingUpdatedProperty().addListener((obs, old, beingUpdated) -> {
            if(!beingUpdated) {
                boolean bold, italic, underline;
                
                
                IndexRange selection = area.getSelection();
                
                if(selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().allMatch(style -> style.bold.orElse(false));
                    italic = styles.styleStream().allMatch(style -> style.italic.orElse(false));
                    underline = styles.styleStream().allMatch(style -> style.underline.orElse(false));
                } else {
                    int paragraph = area.getCurrentParagraph();
                    int column = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(paragraph, column);
                    
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                }
                
                int startPar = area.offsetToPosition(selection.getStart(), TwoDimensional.Bias.Forward).getMajor();
                int endPar = area.offsetToPosition(selection.getEnd(), TwoDimensional.Bias.Backward).getMajor();
                List< Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle>> pars = area.getParagraphs().subList(startPar, endPar);
            }
        });
        
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if(selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }
    
    private void toggleBold() {
        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }
    
    private void toggleItalic() {
        updateStyleInSelection(spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }
    
    private void toggleUnderline() {
        updateStyleInSelection(spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }
}
